package com.zosh.service;

import com.zosh.model.Appuser;
import com.zosh.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomeUserServiceImplementation implements UserDetailsService {

	private UserRepository userRepository;

	public CustomeUserServiceImplementation(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Appuser appuser = userRepository.findByEmail(username);

		if (appuser == null) {

			throw new UsernameNotFoundException("appuser not found with email  - " + username);
		}

		List<GrantedAuthority> authorities = new ArrayList<>();

		return new org.springframework.security.core.userdetails.User(
				appuser.getEmail(), appuser.getPassword(), authorities);
	}

}
