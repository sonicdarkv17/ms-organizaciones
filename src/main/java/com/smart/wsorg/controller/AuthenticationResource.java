package com.smart.wsorg.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.smart.wsorg.security.service.AuthenticateService;
import com.smart.wsorg.vo.JwtAuthenticationResponse;
import com.smart.wsorg.vo.JwtTokenUtil;
import com.smart.wsorg.vo.JwtUser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@RestController
public class AuthenticationResource {

	@Value("${jwt.header}")
	private String tokenHeader;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	@Qualifier("jwtUserDetailsService")
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticateService authService;

	@PostMapping("${jwt.route.authentication.path}")
	public ResponseEntity<JwtAuthenticationResponse> createAuthenticationToken(String username, String password) {
		return ResponseEntity.ok(this.authService.loginAndCreateToken(username, password));
	}

	@GetMapping("${jwt.route.authentication.refresh}")
	public JwtAuthenticationResponse refreshAndGetAuthenticationToken(HttpServletRequest request) {
		String authToken = request.getHeader(tokenHeader);
		final String token = authToken.substring(7);
		String username = jwtTokenUtil.getUsernameFromToken(token);
		JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);

		if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
			String refreshedToken = jwtTokenUtil.refreshToken(token);
			return new JwtAuthenticationResponse(refreshedToken);
		} else {
			return new JwtAuthenticationResponse();
		}
	}

	/**
	 * Intercepta todas las excepciones y los convierte en respuestas json
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler
	@ResponseBody
	public ResponseEntity<ExceptionRepresentation> handle(Exception exception) {
		ExceptionRepresentation body = new ExceptionRepresentation(exception.getLocalizedMessage());
		return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
	}

	@AllArgsConstructor
	@Getter
	@Setter
	class ExceptionRepresentation {
		private String message;
	}
}
