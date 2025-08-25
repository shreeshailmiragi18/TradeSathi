package com.zosh.controller;

import com.zosh.domain.VerificationType;
import com.zosh.exception.UserException;
import com.zosh.model.ForgotPasswordToken;
import com.zosh.model.Appuser;
import com.zosh.model.VerificationCode;
import com.zosh.request.ResetPasswordRequest;
import com.zosh.request.UpdatePasswordRequest;
import com.zosh.response.ApiResponse;
import com.zosh.response.AuthResponse;
import com.zosh.service.EmailService;
import com.zosh.service.ForgotPasswordService;
import com.zosh.service.UserService;
import com.zosh.service.VerificationService;
import com.zosh.utils.OtpUtils;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    private UserService appuserService;

    @Autowired
    private VerificationService verificationService;

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/api/appusers/profile")
    public ResponseEntity<Appuser> getUserProfileHandler(
            @RequestHeader("Authorization") String jwt) throws UserException {

        Appuser appuser = appuserService.findUserProfileByJwt(jwt);
        appuser.setPassword(null);

        return new ResponseEntity<>(appuser, HttpStatus.ACCEPTED);
    }

    @GetMapping("/api/appusers/{appuserId}")
    public ResponseEntity<Appuser> findUserById(
            @PathVariable Long appuserId,
            @RequestHeader("Authorization") String jwt) throws UserException {

        Appuser appuser = appuserService.findUserById(appuserId);
        appuser.setPassword(null);

        return new ResponseEntity<>(appuser, HttpStatus.ACCEPTED);
    }

    @GetMapping("/api/appusers/email/{email}")
    public ResponseEntity<Appuser> findUserByEmail(
            @PathVariable String email,
            @RequestHeader("Authorization") String jwt) throws UserException {

        Appuser appuser = appuserService.findUserByEmail(email);

        return new ResponseEntity<>(appuser, HttpStatus.ACCEPTED);
    }

    @PatchMapping("/api/appusers/enable-two-factor/verify-otp/{otp}")
    public ResponseEntity<Appuser> enabledTwoFactorAuthentication(
            @RequestHeader("Authorization") String jwt,
            @PathVariable String otp) throws Exception {

        Appuser appuser = appuserService.findUserProfileByJwt(jwt);

        VerificationCode verificationCode = verificationService.findUsersVerification(appuser);

        String sendTo = verificationCode.getVerificationType().equals(VerificationType.EMAIL)
                ? verificationCode.getEmail()
                : verificationCode.getMobile();

        boolean isVerified = verificationService.VerifyOtp(otp, verificationCode);

        if (isVerified) {
            Appuser updatedUser = appuserService.enabledTwoFactorAuthentication(verificationCode.getVerificationType(),
                    sendTo, appuser);
            verificationService.deleteVerification(verificationCode);
            return ResponseEntity.ok(updatedUser);
        }
        throw new Exception("wrong otp");

    }

    @PatchMapping("/auth/appusers/reset-password/verify-otp")
    public ResponseEntity<ApiResponse> resetPassword(
            @RequestParam String id,
            @RequestBody ResetPasswordRequest req) throws Exception {
        ForgotPasswordToken forgotPasswordToken = forgotPasswordService.findById(id);

        boolean isVerified = forgotPasswordService.verifyToken(forgotPasswordToken, req.getOtp());

        if (isVerified) {

            appuserService.updatePassword(forgotPasswordToken.getAppuser(), req.getPassword());
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setMessage("password updated successfully");
            return ResponseEntity.ok(apiResponse);
        }
        throw new Exception("wrong otp");

    }

    @PostMapping("/auth/appusers/reset-password/send-otp")
    public ResponseEntity<AuthResponse> sendUpdatePasswordOTP(
            @RequestBody UpdatePasswordRequest req)
            throws Exception {

        Appuser appuser = appuserService.findUserByEmail(req.getSendTo());
        String otp = OtpUtils.generateOTP();
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();

        ForgotPasswordToken token = forgotPasswordService.findByUser(appuser.getId());

        if (token == null) {
            token = forgotPasswordService.createToken(
                    appuser, id, otp, req.getVerificationType(), req.getSendTo());
        }

        if (req.getVerificationType().equals(VerificationType.EMAIL)) {
            emailService.sendVerificationOtpEmail(
                    appuser.getEmail(),
                    token.getOtp());
        }

        AuthResponse res = new AuthResponse();
        res.setSession(token.getId());
        res.setMessage("Password Reset OTP sent successfully.");

        return ResponseEntity.ok(res);

    }

    @PatchMapping("/api/appusers/verification/verify-otp/{otp}")
    public ResponseEntity<Appuser> verifyOTP(
            @RequestHeader("Authorization") String jwt,
            @PathVariable String otp) throws Exception {

        Appuser appuser = appuserService.findUserProfileByJwt(jwt);

        VerificationCode verificationCode = verificationService.findUsersVerification(appuser);

        boolean isVerified = verificationService.VerifyOtp(otp, verificationCode);

        if (isVerified) {
            verificationService.deleteVerification(verificationCode);
            Appuser verifiedUser = appuserService.verifyUser(appuser);
            return ResponseEntity.ok(verifiedUser);
        }
        throw new Exception("wrong otp");

    }

    @PostMapping("/api/appusers/verification/{verificationType}/send-otp")
    public ResponseEntity<String> sendVerificationOTP(
            @PathVariable VerificationType verificationType,
            @RequestHeader("Authorization") String jwt)
            throws Exception {

        Appuser appuser = appuserService.findUserProfileByJwt(jwt);

        VerificationCode verificationCode = verificationService.findUsersVerification(appuser);

        if (verificationCode == null) {
            verificationCode = verificationService.sendVerificationOTP(appuser, verificationType);
        }

        if (verificationType.equals(VerificationType.EMAIL)) {
            emailService.sendVerificationOtpEmail(appuser.getEmail(), verificationCode.getOtp());
        }

        return ResponseEntity.ok("Verification OTP sent successfully.");

    }

}
