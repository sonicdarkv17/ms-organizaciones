package com.smart.wsorg.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
public class ResponseCodeBean implements Serializable {

	private static final long serialVersionUID = -8872167999855136047L;
	/**
	 * Codigo de respuesta unico
	 */
	@ApiModelProperty(value = "C\u00f3digo de respuesta \u00fanico. CODIGO_MENSAJE.NNN", allowableValues = "SSSS.NNN", required = true)
	private String code;
	/**
	 * Mensaje de respuesta descrito
	 */
	@ApiModelProperty(value = "Mensaje de respuesta descrito.", required = true)
	private String message;
	/**
	 * Nivel de error
	 */
	@ApiModelProperty(value = "Nivel de error", allowableValues = "ERROR, INFO, WARN", required = true)
	private String level;
	/**
	 * Descripcion detallada del error
	 */
	@ApiModelProperty(value = "Descripci\u00f3n detallada del error.", required = true)
	private String description;

	/**
	 * Constructor de la clase
	 */
	public ResponseCodeBean() {
		super();
	}

}
