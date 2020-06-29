package com.smart.wsorg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * 
 * @author Ivan Garcia Esta es la clase principal que inicia la aplicacion de
 *         Spring Boot. En ella se puede agregar el escaneo de distintos
 *         paquetes para que sean cargados en el contexto de Spring
 */
@SpringBootApplication
@ComponentScan("com.smart.wsorg")
public class WsOrgApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(WsOrgApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(WsOrgApplication.class);
	}

}
