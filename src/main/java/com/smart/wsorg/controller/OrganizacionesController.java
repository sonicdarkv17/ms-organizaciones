package com.smart.wsorg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smart.wsorg.dto.OrganizacionDTO;
import com.smart.wsorg.dto.ResponseMensajeBean;
import com.smart.wsorg.exceptions.BusinessException;
import com.smart.wsorg.model.ResponseCodeBean;
import com.smart.wsorg.model.SingleResponseBean;
import com.smart.wsorg.service.IOrganizacionesService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * @author IGG
 * 
 *         Esta clase se encarga de exponer los endpoints de acceso basado
 *         principios REST Existen ciertas consultas, bajas, altas y
 *         actualizaciones a una coleccion de recursos de Organizaciones
 */
@RestController
@RequestMapping("/organizaciones")
@Slf4j
public class OrganizacionesController {
	/**
	 * Interface encargada de la logica de negocio de las organizaciones
	 */
	@Autowired
	private IOrganizacionesService organizacionService;

	/**
	 * Consulta de Organizaciones, no se agrega codigo de encriptacion
	 * 
	 * @param authorization Token de Autorizacion
	 * @return List<OrganizacionDTO> Listado de Organizaciones
	 */
	@ApiOperation(value = "Consulta de Organizaciones", notes = "Devuelve Listado de organizaciones", response = ResponseEntity.class, httpMethod = "GET")
	@GetMapping
	public ResponseEntity<SingleResponseBean<List<OrganizacionDTO>>> consultaOrganizaciones(
			@ApiParam(value = "Token de Autorizacion Bearer +Token") @RequestHeader(value = "Authorization") String authorization) {

		log.info("Entra a controller[consultaOrganizacion]");
		SingleResponseBean<List<OrganizacionDTO>> response = new SingleResponseBean<>();

		try {
			List<OrganizacionDTO> listaOrg = organizacionService.getOrganizaciones();
			response.done(listaOrg);
		} catch (BusinessException e) {
			log.error("Ha ocurrido un error [consultaOrganizacion]", e);
			response.error(e.getResponseCode());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * 
	 * @param authorization  Token de Autorizacion
	 * @param idOrganizacion
	 * @return OrganizacionDTO Entity Organizacion
	 */
	@ApiOperation(value = "Consulta Organizacion", notes = "Devuelve Entity de Organizacion", response = ResponseEntity.class, httpMethod = "GET")
	@GetMapping("/{idOrganizacion}")
	public ResponseEntity<SingleResponseBean<OrganizacionDTO>> consultaOrganizacion(
			@ApiParam(value = "Token de Autorizacion Bearer +Token") @RequestHeader(value = "Authorization") String authorization,
			@ApiParam(value = "ID Organizacion") @PathVariable("idOrganizacion") Integer idOrganizacion,
			@RequestHeader(value = "codigoEncriptacion") String codigoEncriptacion) {

		log.info("Entra a controller[consultaOrganizacion]");
		SingleResponseBean<OrganizacionDTO> response = new SingleResponseBean<>();

		try {
			OrganizacionDTO org = organizacionService.getOrganizacionById(idOrganizacion, codigoEncriptacion);
			response.done(org);
		} catch (BusinessException e) {
			log.error("Ha ocurrido un error [consultaOrganizacion]", e);
			response.error(e.getResponseCode());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * 
	 * @param org           Entity Organizacion
	 * @param authorization Token de Autorizacion
	 * @return OrganizacionDTO Organizacion dada de alta
	 */
	@ApiOperation(value = "Alta de Organizacion", response = ResponseEntity.class, httpMethod = "POST")
	@PostMapping
	public ResponseEntity<SingleResponseBean<OrganizacionDTO>> altaOrganizacion(
			@ApiParam(value = "Organizacion a dar de alta") @RequestBody OrganizacionDTO org,
			@ApiParam(value = "Token de Autorizacion Bearer +Token") @RequestHeader("Authorization") String authorization) {

		SingleResponseBean<OrganizacionDTO> response = new SingleResponseBean<>();

		try {
			org.setIdOrganizacion(null);
			OrganizacionDTO organizacion = organizacionService.insertaOrganizacion(org);

			response.done(organizacion);
		} catch (BusinessException e) {
			log.info("Ha ocurrido un error", e);
			response.error(e.getResponseCode());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	/**
	 * 
	 * @param org           Organizacion a actualizar
	 * @param authorization Token de Autorizacion
	 * @return
	 */
	@ApiOperation(value = "Actualizacion de Organizacion", response = ResponseEntity.class, httpMethod = "PUT")
	@PutMapping
	public ResponseEntity<SingleResponseBean<OrganizacionDTO>> actualizaOrganizacion(
			@ApiParam(value = "Organizacion a dar de alta") @RequestBody OrganizacionDTO org,
			@ApiParam(value = "Token de Autorizacion Bearer +Token") @RequestHeader("Authorization") String authorization,
			@RequestHeader(value = "codigoEncriptacion") String codigoEncriptacion) {

		SingleResponseBean<OrganizacionDTO> response = new SingleResponseBean<>();

		try {
			OrganizacionDTO organizacion = organizacionService.actualizaOrganizacion(org, codigoEncriptacion);

			response.done(organizacion);
		} catch (BusinessException e) {
			log.info("Ha ocurrido un error", e);
			response.error(e.getResponseCode());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	/**
	 * 
	 * @param idOrganizacion Id de organizacion a eliminar
	 * @param authorization  Token de Autorizacion
	 * @return Mensaje de delete
	 */
	@ApiOperation(value = "Elimina  Organizacion", response = ResponseEntity.class, httpMethod = "DELETE")
	@DeleteMapping(value = "/{idOrganizacion}")
	public ResponseEntity<SingleResponseBean<ResponseMensajeBean>> eliminarBeneficiario(
			@ApiParam(value = "Id de organizacion a borrar") @PathVariable("idOrganizacion") Integer idOrganizacion,
			@ApiParam(value = "Token de Autorizacion Bearer +Token") @RequestHeader("Authorization") String authorization,
			@RequestHeader(value = "codigoEncriptacion") String codigoEncriptacion) {
		SingleResponseBean<ResponseMensajeBean> response = new SingleResponseBean<>();

		try {
			if (organizacionService.eliminaOrganizacion(idOrganizacion, codigoEncriptacion)) {
				response.done(new ResponseMensajeBean("Organizacion eliminada correctamente."));
			} else {
				response.error(new ResponseCodeBean("200", "Ha ocurrido un error al eliminar la organizacion", "ERROR",
						"Ha ocurrido un error al eliminar la organizacion"));
			}

		} catch (BusinessException e) {
			log.info("Ha ocurrido un error", e);
			response.error(e.getResponseCode());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
