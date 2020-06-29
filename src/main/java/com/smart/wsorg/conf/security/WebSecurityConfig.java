package com.smart.wsorg.conf.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.smart.wsorg.security.service.JwtUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	JwtAuthorizationTokenFilter authenticationTokenFilter;

	@Value("${jwt.header}")
	private String tokenHeader;

	@Value("${jwt.route.authentication.path}")
	private String authenticationPath;

	@Value("${spring.mvc.servlet.path}")
	private String pathUrl;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoderBean());
	}

	@Bean
	public PasswordEncoder passwordEncoderBean() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers(pathUrl + "/auth/**").permitAll().antMatchers(pathUrl + "/refresh/**").permitAll()
				.antMatchers(pathUrl + "/swagger-resources/**").permitAll().antMatchers(pathUrl + "/swagger-ui.html")
				.permitAll().antMatchers(pathUrl + "/webjars/**").permitAll().antMatchers(HttpMethod.OPTIONS, "/**")
				.permitAll().anyRequest().authenticated();

		httpSecurity.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

		httpSecurity.headers().frameOptions().sameOrigin().cacheControl();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.POST, authenticationPath).antMatchers(pathUrl + "/v2/api-docs/**")
				.antMatchers(pathUrl + "/swagger.json").antMatchers(pathUrl + "/swagger-ui.html")
				.antMatchers(pathUrl + "/swagger-resources/**").antMatchers(pathUrl + "/webjars/**");
	}

}
