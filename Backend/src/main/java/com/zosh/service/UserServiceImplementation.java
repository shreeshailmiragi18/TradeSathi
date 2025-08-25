package com.zosh.service;

import com.zosh.config.JwtProvider;
import com.zosh.domain.VerificationType;
import com.zosh.exception.UserException;
import com.zosh.model.TwoFactorAuth;
import com.zosh.model.Appuser;
import com.zosh.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Appuser findUserProfileByJwt(String jwt) throws UserException {
		String email = JwtProvider.getEmailFromJwtToken(jwt);

		Appuser appuser = userRepository.findByEmail(email);

		if (appuser == null) {
			throw new UserException("appuser not exist with email " + email);
		}
		return appuser;
	}

	@Override
	public Appuser findUserByEmail(String username) throws UserException {

		Appuser appuser = userRepository.findByEmail(username);

		if (appuser != null) {

			return appuser;
		}

		throw new UserException("appuser not exist with username " + username);
	}

	@Override
	public Appuser findUserById(Long userId) throws UserException {
		Optional<Appuser> opt = userRepository.findById(userId);

		if (opt.isEmpty()) {
			throw new UserException("appuser not found with id " + userId);
		}
		return opt.get();
	}

	@Override
	public Appuser verifyUser(Appuser appuser) throws UserException {
		appuser.setVerified(true);
		return userRepository.save(appuser);
	}

	@Override
	public Appuser enabledTwoFactorAuthentication(
			VerificationType verificationType, String sendTo, Appuser appuser) throws UserException {
		TwoFactorAuth twoFactorAuth = new TwoFactorAuth();
		twoFactorAuth.setEnabled(true);
		twoFactorAuth.setSendTo(verificationType);

		appuser.setTwoFactorAuth(twoFactorAuth);
		return userRepository.save(appuser);
	}

	@Override
	public Appuser updatePassword(Appuser appuser, String newPassword) {
		appuser.setPassword(passwordEncoder.encode(newPassword));
		return userRepository.save(appuser);
	}

	@Override
	public void sendUpdatePasswordOtp(String email, String otp) {

	}

}
