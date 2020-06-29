package com.smart.wsorg.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ResponseMensajeBean implements Serializable {

	/**
	 * atributo serialVersionUID
	 */
	private static final long serialVersionUID = -7584792742571003370L;

	/** Codigo de tipo de registro */
	private String mensaje;
}
