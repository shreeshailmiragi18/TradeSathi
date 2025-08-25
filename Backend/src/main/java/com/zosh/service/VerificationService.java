package com.zosh.service;

import com.zosh.domain.VerificationType;
import com.zosh.model.Appuser;
import com.zosh.model.VerificationCode;

public interface VerificationService {
    VerificationCode sendVerificationOTP(Appuser appuser, VerificationType verificationType);

    VerificationCode findVerificationById(Long id) throws Exception;

    VerificationCode findUsersVerification(Appuser appuser) throws Exception;

    Boolean VerifyOtp(String opt, VerificationCode verificationCode);

    void deleteVerification(VerificationCode verificationCode);
}
