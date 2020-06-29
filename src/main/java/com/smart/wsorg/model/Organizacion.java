package com.smart.wsorg.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author Ivan Garcia
 *
 */

@Entity
@Table(name = "Organizacion")
@Data
public class Organizacion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5153579052482195455L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idOrganizacion")
	private Integer idOrganizacion;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "direccion")
	private String direccion;

	@Column(name = "telefono")
	private String telefono;

	@Column(name = "idExterno")
	private String idExterno;

}
