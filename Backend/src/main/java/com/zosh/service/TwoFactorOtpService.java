package com.zosh.service;

import com.zosh.model.TwoFactorOTP;
import com.zosh.model.Appuser;

public interface TwoFactorOtpService {

    TwoFactorOTP createTwoFactorOtp(Appuser appuser, String otp, String jwt);

    TwoFactorOTP findByUser(Long userId);

    TwoFactorOTP findById(String id);

    boolean verifyTwoFactorOtp(TwoFactorOTP twoFactorOtp, String otp);

    void deleteTwoFactorOtp(TwoFactorOTP twoFactorOTP);

}
