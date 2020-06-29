package com.smart.wsorg.conf.security;

/**
 * 
 * @author IGG
 * 
 *         Excepcion definida para la autenticacion
 */
public class AuthenticationException extends RuntimeException {

	private static final long serialVersionUID = -4528637351555963432L;

	public AuthenticationException(String message) {
		super(message);
	}

	public AuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}

}
