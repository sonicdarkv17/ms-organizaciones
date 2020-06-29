package com.smart.wsorg.security.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.smart.wsorg.dto.Authority;
import com.smart.wsorg.dto.AuthorityName;
import com.smart.wsorg.dto.UserVO;
import com.smart.wsorg.dto.UsuarioDTO;
import com.smart.wsorg.exceptions.BusinessException;
import com.smart.wsorg.service.IUsuariosService;
import com.smart.wsorg.vo.JwtUser;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private IUsuariosService usuariosService;

	@Override
	public JwtUser loadUserByUsername(String username) {

		UsuarioDTO usuarioEncontrado = null;
		try {
			usuarioEncontrado = usuariosService.getUsuarioByName(username);
		} catch (BusinessException e) {
			log.error("Error al buscar usuario ", e);
		}

		UserVO user = new UserVO();
		user.setUsername(usuarioEncontrado.getUsuario());
		user.setPassword(usuarioEncontrado.getPassword());

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, 2016);
		calendar.set(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);

		Authority rol = new Authority();
		rol.setId(1L);
		rol.setName(AuthorityName.ROLE_ADMIN);

		Authority rol2 = new Authority();
		rol2.setId(2L);
		rol2.setName(AuthorityName.ROLE_USER);

		List<Authority> roles = new ArrayList<>();
		roles.add(rol);
		roles.add(rol2);

		return new JwtUser(user.getUsername(), user.getPassword(), mapToGrantedAuthorities(roles), true,
				calendar.getTime(), user);
	}

	private static List<GrantedAuthority> mapToGrantedAuthorities(List<Authority> authorities) {
		return authorities.stream().map(authority -> new SimpleGrantedAuthority(authority.getName().name()))
				.collect(Collectors.toList());
	}
}
