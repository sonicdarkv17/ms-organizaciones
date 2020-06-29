package com.smart.wsorg.exceptions;

import com.smart.wsorg.model.ResponseCodeBean;

public class BusinessException extends Exception {

	/**
	 * Numero serial
	 */
	private static final long serialVersionUID = -6654256023230766060L;

	/**
	 * Excepcion original
	 */
	private final Exception originalException;

	/**
	 * Codigo de respuesta
	 */
	private final ResponseCodeBean responseCode;

	/**
	 * Constructor de la clase
	 * 
	 * @param responseCode mensaje de la exepcion
	 * @param params       parametros para el mensaje
	 */
	public BusinessException(final ResponseCodeBean responseCode, final Object... params) {
		super(formatMsg(responseCode.getMessage(), params));
		responseCode.setMessage(formatMsg(responseCode.getMessage(), params));
		this.responseCode = responseCode;
		this.originalException = null;
	}

	/**
	 * Constructor de la clase
	 * 
	 * @param e            excepcion original
	 * @param responseCode mensaje de la excepcion
	 * @param params       parametros para el mensaje
	 */
	public BusinessException(final Exception e, final ResponseCodeBean responseCode, final Object... params) {
		super(formatMsg(responseCode.getMessage(), params));
		this.responseCode = responseCode;
		this.originalException = e;
	}

	/**
	 * Obtener valor.
	 * 
	 * @return El valor de originalException.
	 */
	public Exception getOriginalException() {
		return originalException;
	}

	/**
	 * @return the responseCode
	 */
	public ResponseCodeBean getResponseCode() {
		return responseCode;
	}

	/**
	 * Metodo para formatear el mensaje
	 * @param message
	 * @param params
	 * @return
	 */
	public static String formatMsg(final String message, final Object... params) {
		String mensaje = message;
		if (message != null) {
			for (final Object param : params) {
				mensaje = mensaje.replaceFirst("\\{\\}", "" + param);
			}
		}
		return mensaje;
	}
}
