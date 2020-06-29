package com.smart.wsorg.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.smart.wsorg.dto.OrganizacionDTO;
import com.smart.wsorg.exceptions.BusinessException;

@Service
public interface IOrganizacionesService {

	/**
	 * Metodo encargado de listas todas las organizaciones
	 * 
	 * @return List<OrganizacionDTO> Listado de organizaciones
	 * @throws BusinessException Excepcion de negocio
	 */
	List<OrganizacionDTO> getOrganizaciones() throws BusinessException;

	/**
	 * Metodo encargado de insertar una organizacion
	 * 
	 * @param org Entity de organizacion
	 * @return OrganizacionDTO organizacion insertada
	 * @throws BusinessException Excepcion de negocio
	 */
	OrganizacionDTO insertaOrganizacion(OrganizacionDTO org) throws BusinessException;

	/**
	 * 
	 * @param idOrganizacion id de la organizacion a eliminar
	 * @return true/false
	 * @throws BusinessException Excepcion de negocio
	 */
	boolean eliminaOrganizacion(Integer idOrganizacion) throws BusinessException;

	/**
	 * 
	 * @param org Entity de organizacion
	 * @return OrganizacionDTO organizacion actualizada
	 * @throws BusinessException Excepcion de negocio
	 */
	OrganizacionDTO actualizaOrganizacion(OrganizacionDTO org) throws BusinessException;

	/**
	 * 
	 * @param idOrganizacion id de la organizacion a buscar	
	 * @return OrganizacionDTO organizacion actualizada
	 * @throws BusinessException Excepcion de negocio
	 */
	OrganizacionDTO getOrganizacionById(Integer idOrganizacion) throws BusinessException;
	
	
	/**
	 * Metodo que valida la cadena de encriptacion
	 * @param cadena String a validar 
	 * @return true/false
	 * @throws BusinessException Excepcion de negocio
	 */
	boolean validaCadenaEncriptacion(String cadena) throws BusinessException;

}
