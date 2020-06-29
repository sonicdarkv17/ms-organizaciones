package com.smart.wsorg.service;

import org.springframework.stereotype.Service;

import com.smart.wsorg.dto.UsuarioDTO;
import com.smart.wsorg.exceptions.BusinessException;

@Service
public interface IUsuariosService {

	/**
	 * Metodo que obtiene el usuario a base de datos para el acceso a la aplicacion
	 * @param nombre nombre de usuario
	 * @return UsuarioDTO objeto con la informacion del usuario
	 * @throws BusinessException excepcion de negocio
	 */
	public UsuarioDTO getUsuarioByName(String nombre) throws BusinessException;

}
