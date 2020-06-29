package com.smart.wsorg.conf.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.smart.wsorg.vo.JwtTokenUtil;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAuthorizationTokenFilter extends OncePerRequestFilter {

	private final UserDetailsService userDetailsService;
	private final JwtTokenUtil jwtTokenUtil;
	private final String tokenHeader;

	public JwtAuthorizationTokenFilter(UserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil,
			@Value("${jwt.header}") String tokenHeader) {
		this.userDetailsService = userDetailsService;
		this.jwtTokenUtil = jwtTokenUtil;
		this.tokenHeader = tokenHeader;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		log.debug("processing authentication for '{}'", request.getRequestURL());

		final String requestHeader = request.getHeader(this.tokenHeader);

		String username = null;
		String authToken = null;
		if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
			authToken = requestHeader.substring(7);
			try {
				username = jwtTokenUtil.getUsernameFromToken(authToken);
			} catch (IllegalArgumentException e) {
				log.info("an error occured during getting username from token", e);
			} catch (ExpiredJwtException e) {
				log.info("the token is expired and not valid anymore", e);
			} catch (Exception exG) {
				log.info("Error  en authenticación");
			}
		}

		log.debug("checking authentication for user '{}'", username);
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			if (jwtTokenUtil.validateToken(authToken, userDetails)) {
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}

		chain.doFilter(request, response);
	}
}
