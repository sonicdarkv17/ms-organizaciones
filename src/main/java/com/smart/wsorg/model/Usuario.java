package com.smart.wsorg.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author Ivan Garcia
 *
 */

@Entity
@Table(name = "Usuarios")
@Data
public class Usuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5153579052482195455L;

	@Id
	@Column(name = "idUsuario")
	private Integer idUsuario;
	
	@Column(name = "usuario")
	private String usuario;

	@Column(name = "password")
	private String password;

}
