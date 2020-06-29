package com.smart.wsorg.conf.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.smart.wsorg.dto.UsuarioDTO;
import com.smart.wsorg.exceptions.BusinessException;
import com.smart.wsorg.service.IUsuariosService;
import com.smart.wsorg.utilerias.Utilerias;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {

	/**
	 * Interface encargada de la logica de negocio que se encarga de validar el
	 * acceso del usuario a la aplicacion
	 */
	@Autowired
	private IUsuariosService usuariosService;

	private static final String USUARIO_ERROR = "Usuario y/o constraseña incorrectos";

	@Override
	public Authentication authenticate(Authentication authentication) {
		String usuario = authentication.getName();
		String password = authentication.getCredentials().toString();

		UsuarioDTO usuarioEncontrado;
		try {
			usuarioEncontrado = usuariosService.getUsuarioByName(usuario);
			if (!Utilerias.encripta(password).equals(usuarioEncontrado.getPassword())) {
				throw new BadCredentialsException(USUARIO_ERROR);
			}
		} catch (BusinessException e) {
			throw new BadCredentialsException(USUARIO_ERROR);
		}
		Objects.requireNonNull(usuarioEncontrado, USUARIO_ERROR);

		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		return new UsernamePasswordAuthenticationToken(usuario, password, authorities);
	}
}