package com.smart.wsorg.security.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.smart.wsorg.conf.security.AuthenticationException;
import com.smart.wsorg.conf.security.CustomAuthenticationManager;
import com.smart.wsorg.vo.JwtAuthenticationResponse;
import com.smart.wsorg.vo.JwtTokenUtil;
import com.smart.wsorg.vo.JwtUser;

@Service
public class AuthenticateService {

	@Autowired
	@Qualifier("jwtUserDetailsService")
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private CustomAuthenticationManager authenticationManager;

	public JwtAuthenticationResponse loginAndCreateToken(String userName, String password) {
		authenticate(userName, password);

		JwtUser userDetails = (JwtUser) userDetailsService.loadUserByUsername(userName);
		String token = jwtTokenUtil.generateToken(userDetails);

		return new JwtAuthenticationResponse(token);
	}

	private void authenticate(String username, String password) {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new AuthenticationException("Usuario deshabilitado", e);
		} catch (BadCredentialsException e) {
			throw new AuthenticationException("Usuario y/o constraseña incorrectos", e);
		}
	}

}
