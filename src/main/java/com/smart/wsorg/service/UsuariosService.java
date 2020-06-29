package com.smart.wsorg.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.wsorg.dto.UsuarioDTO;
import com.smart.wsorg.exceptions.BusinessException;
import com.smart.wsorg.model.ResponseCodeBean;
import com.smart.wsorg.model.Usuario;
import com.smart.wsorg.repository.IUsuariosRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UsuariosService implements IUsuariosService {

	@Autowired
	private IUsuariosRepository usuariosRepository;

	/**
	 * see you
	 * {@link com.smart.wsorg.service.IUsuariosService#getUsuarioByName}
	 */
	@Override
	public UsuarioDTO getUsuarioByName(String nombre) throws BusinessException {
		log.info("UsuariosService[getUsuarioByName] ");
		Usuario usuario = usuariosRepository.findByUsuario(nombre);
		if (null != usuario) {
			return new ModelMapper().map(usuario, UsuarioDTO.class);
		} else {
			throw new BusinessException(new ResponseCodeBean("ER9999", "No existe usuario con este username", "ERROR",
					"No existe usuario con este username"), "ER9999");
		}

	}

}
