package com.smart.wsorg.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.smart.wsorg.dto.OrganizacionDTO;
import com.smart.wsorg.exceptions.BusinessException;
import com.smart.wsorg.model.Organizacion;
import com.smart.wsorg.model.ResponseCodeBean;
import com.smart.wsorg.repository.IOrganizacionesRepository;
import com.smart.wsorg.utilerias.Utilerias;

@Service
public class OrganizacionesService implements IOrganizacionesService {

	@Autowired
	private IOrganizacionesRepository organizacionesRepository;

	@Value("${secure.idExterno.prefijo}")
	private String prefijo;

	/**
	 * see you
	 * {@link com.smart.wsorg.service.IOrganizacionesService#getOrganizaciones}
	 */
	@Override
	public List<OrganizacionDTO> getOrganizaciones() throws BusinessException {
		List<Organizacion> lista = organizacionesRepository.findAll();
		return entityToModel(lista);
	}

	/**
	 * Metodo que mapea de entidad a modelo
	 * 
	 * @param entities Listado de entidades
	 * @return List<OrganizacionDTO> listado de modelos
	 */
	private List<OrganizacionDTO> entityToModel(List<Organizacion> entities) {
		ModelMapper modelMapper = new ModelMapper();
		return entities.stream().map(entity -> modelMapper.map(entity, OrganizacionDTO.class))
				.collect(Collectors.toList());
	}

	/**
	 * see you
	 * {@link com.smart.wsorg.service.IOrganizacionesService#insertaOrganizacion}
	 */
	@Override
	public OrganizacionDTO insertaOrganizacion(OrganizacionDTO org) throws BusinessException {
		String nombreStr = org.getNombre().substring(0, 4);
		String telefono = org.getTelefono().substring(org.getTelefono().length() - 4);
		org.setIdExterno(nombreStr + telefono + prefijo);

		ModelMapper mapper = new ModelMapper();
		Organizacion orgBd = organizacionesRepository.save(mapper.map(org, Organizacion.class));
		orgBd.setIdExterno(Utilerias.encripta(orgBd.getIdExterno() + orgBd.getIdOrganizacion()));
		orgBd = organizacionesRepository.save(orgBd);
		return mapper.map(orgBd, OrganizacionDTO.class);
	}

	/**
	 * see you
	 * {@link com.smart.wsorg.service.IOrganizacionesService#eliminaOrganizacion}
	 */
	@Override
	public boolean eliminaOrganizacion(Integer idOrganizacion, String codigoEncriptacion) throws BusinessException {
		Optional<Organizacion> orgBdopt = organizacionesRepository.findById(idOrganizacion);
		if (orgBdopt.isPresent()) {
			Organizacion orgBD = orgBdopt.get();
			validaCodigoEncriptacion(orgBD, codigoEncriptacion);
			organizacionesRepository.deleteById(idOrganizacion);
		}

		return true;
	}

	/**
	 * see you
	 * {@link com.smart.wsorg.service.IOrganizacionesService#actualizaOrganizacion}
	 */
	@Override
	public OrganizacionDTO actualizaOrganizacion(OrganizacionDTO org, String codigoEncriptacion)
			throws BusinessException {
		String nombreStr = org.getNombre().substring(0, 4);
		String telefono = org.getTelefono().substring(org.getTelefono().length() - 4);
		org.setIdExterno(nombreStr + telefono + prefijo);
		Optional<Organizacion> orgBdopt = organizacionesRepository.findById(org.getIdOrganizacion());
		if (orgBdopt.isPresent()) {
			Organizacion orgBD = orgBdopt.get();
			validaCodigoEncriptacion(orgBD, codigoEncriptacion);
			orgBD.setDireccion(org.getDireccion());
			orgBD.setIdExterno(Utilerias.encripta(org.getIdExterno() + org.getIdOrganizacion()));
			orgBD.setNombre(org.getNombre());
			orgBD.setTelefono(org.getTelefono());
			orgBD = organizacionesRepository.save(orgBD);
			return new ModelMapper().map(orgBD, OrganizacionDTO.class);
		} else {
			throw new BusinessException(new ResponseCodeBean("200", "No Se encuentra la organizacion en el sistema",
					"ERROR",
					"Se ha presentado un error de acceso a datos, favor de revisar con el administrador del sistema."));
		}
	}

	/**
	 * see you
	 * {@link com.smart.wsorg.service.IOrganizacionesService#getOrganizacionById}
	 */
	@Override
	public OrganizacionDTO getOrganizacionById(Integer idOrganizacion, String codigoEncriptacion)
			throws BusinessException {
		Optional<Organizacion> orgBD = organizacionesRepository.findById(idOrganizacion);
		if (orgBD.isPresent()) {
			validaCodigoEncriptacion(orgBD.get(), codigoEncriptacion);
			return new ModelMapper().map(orgBD.get(), OrganizacionDTO.class);
		}
		throw new BusinessException(new ResponseCodeBean("200", "No Se encuentra la organizacion en el sistema",
				"ERROR",
				"Se ha presentado un error de acceso a datos, favor de revisar con el administrador del sistema."));
	}

	/**
	 * 
	 * @param org
	 * @return
	 */
	public boolean validaCodigoEncriptacion(Organizacion org, String codigoEncritacion) throws BusinessException {
		codigoEncritacion = Utilerias.encripta(codigoEncritacion);
		String encDb = org.getIdExterno();
		if (!codigoEncritacion.equals(encDb)) {
			throw new BusinessException(new ResponseCodeBean("CEINC", "El codigo de encripcion es incorrecto", "CEINC",
					"El codigo de encripcion es incorrecto"));
		}
		return true;
	}

}
