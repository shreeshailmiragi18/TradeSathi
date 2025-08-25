package com.zosh.service;

import com.zosh.domain.VerificationType;
import com.zosh.model.Appuser;
import com.zosh.model.VerificationCode;
import com.zosh.repository.VerificationRepository;
import com.zosh.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.Random;

@Service
public class VerificationServiceImpl implements VerificationService {

    @Autowired
    private VerificationRepository verificationRepository;

    @Override
    public VerificationCode sendVerificationOTP(Appuser appuser, VerificationType verificationType) {

        VerificationCode verificationCode = new VerificationCode();

        verificationCode.setOtp(OtpUtils.generateOTP());
        verificationCode.setAppuser(appuser);
        verificationCode.setVerificationType(verificationType);

        return verificationRepository.save(verificationCode);
    }

    @Override
    public VerificationCode findVerificationById(Long id) throws Exception {
        Optional<VerificationCode> verificationCodeOption = verificationRepository.findById(id);
        if (verificationCodeOption.isEmpty()) {
            throw new Exception("verification not found");
        }
        return verificationCodeOption.get();
    }

    @Override
    public VerificationCode findUsersVerification(Appuser appuser) throws Exception {
        return verificationRepository.findByAppuserId(appuser.getId());
    }

    @Override
    public Boolean VerifyOtp(String opt, VerificationCode verificationCode) {
        return opt.equals(verificationCode.getOtp());
    }

    @Override
    public void deleteVerification(VerificationCode verificationCode) {
        verificationRepository.delete(verificationCode);
    }

}
