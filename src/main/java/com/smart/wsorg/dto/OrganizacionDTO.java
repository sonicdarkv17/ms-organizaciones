package com.smart.wsorg.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class OrganizacionDTO {

	private Integer idOrganizacion;
	private String nombre;
	private String direccion;
	private String telefono;
	@JsonIgnore
	private String idExterno;

}
