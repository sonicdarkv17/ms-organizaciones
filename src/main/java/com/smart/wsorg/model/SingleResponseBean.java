package com.smart.wsorg.model;

import java.io.Serializable;

import com.smart.wsorg.exceptions.BusinessException;

import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SingleResponseBean<R> implements Serializable {

	private static final long serialVersionUID = -4884348326347435334L;
	@ApiModelProperty(value = "Objeto de la respuesta", required = true)
	private transient R responseData;
	
	@ApiModelProperty(value = "Determina la respuesta es correcta o no", notes = "En caso de \u00e9xito : OK, En caso de errores: TEXTO ERROR", required = true)
	private String responseEstatus;

	/**
	 * Constructor de la clase
	 */
	public SingleResponseBean() {
		super();
	}

	/**
	 * Metodo que determina que la respuesta es exitosa y su mensaje, puede estar
	 * 
	 * @param response     Objeto de respuesta, es obligatorio almenos un true o
	 *                     string
	 * @param responseCode mensaje de exito de la respuesta. Ej. "Operacion
	 *                     realizada exitosamente."
	 * @param params       parametros para el mensaje.
	 */
	public void done(final R response, final Object... params) {
		this.responseData = response;
		this.responseEstatus = "OK";
	}

	/**
	 * Metodo que determina que la respuesta es incorrecta. El mensaje se determina
	 * en el metodo getMessage() de Exception o su implementacion
	 * 
	 * @param responseCode codigo de respuesta de la operacion
	 * @param exception    excepcion que provoco el error
	 * @param params       parametros a colocar en el mensaje de error
	 */
	public void error(final Exception exception, final Object... params) {
		this.responseEstatus = "ERROR";
		this.responseData = null;
		if (exception instanceof BusinessException) {
			log.warn("Error --> Detalle {} ", exception.getMessage());
		} else {
			log.error("Error --> Detalle {} -- Error {} ", exception.getMessage(), exception);
		}

	}

	/**
	 * Metodo que determina que la respuesta es incorrecta. El mensaje se determina
	 * en el metodo getMessage() de Exception o su implementacion
	 * 
	 * @param responseCode codigo de respuesta de la operacion
	 * @param params       parametros a colocar en el mensaje de error
	 */
	public void error(final ResponseCodeBean responseCode, final Object... params) {

		this.responseEstatus = "ERROR:" + responseCode.getMessage();
		this.responseData = null;

	}

	/**
	 * Obtener valor.
	 * 
	 * @return El valor de response.
	 */
	public R getResponseData() {
		return responseData;
	}

	/**
	 * Obtener valor.
	 * 
	 * @return El valor de response.
	 */
	public String getResponseEstatus() {
		return responseEstatus;
	}

}
