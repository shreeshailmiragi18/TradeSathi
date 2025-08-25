package com.zosh.service;

import com.zosh.domain.VerificationType;
import com.zosh.exception.UserException;
import com.zosh.model.Appuser;

public interface UserService {

	public Appuser findUserProfileByJwt(String jwt) throws UserException;

	public Appuser findUserByEmail(String email) throws UserException;

	public Appuser findUserById(Long userId) throws UserException;

	public Appuser verifyUser(Appuser appuser) throws UserException;

	public Appuser enabledTwoFactorAuthentication(VerificationType verificationType,
			String sendTo, Appuser appuser) throws UserException;

	// public List<Appuser> getPenddingRestaurantOwner();

	Appuser updatePassword(Appuser appuser, String newPassword);

	void sendUpdatePasswordOtp(String email, String otp);

	// void sendPasswordResetEmail(Appuser appuser);
}
