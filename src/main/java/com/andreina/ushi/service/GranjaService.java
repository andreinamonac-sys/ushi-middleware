package com.andreina.ushi.service;

import java.util.List;

import com.andreina.ushi.dao.criteria.GranjaCriteria;
import com.andreina.ushi.exception.ServiceException;
import com.andreina.ushi.model.Granja;
import com.andreina.ushi.model.GranjaDTO;
import com.andreina.ushi.model.UsuarioDTO;

public interface GranjaService {
	
	/**
	 * Crear una nueva granja.
	 * @param granja
	 * @return
	 */
	public Granja create(Granja granja) throws ServiceException;

	/**
	 * Actualizar una granja existente.
	 * @param granja
	 * @return
	 */
	public GranjaDTO update(GranjaDTO granja) throws ServiceException;

	/**
	 * Eliminar una granja por su id.
	 * @param id
	 * @return
	 */
	public boolean delete(Long id) throws ServiceException;

	/**
	 * Encontrar granja por su id.
	 * @param id
	 * @return
	 */
	public GranjaDTO findById(Long id) throws ServiceException;

	/**
	 * Encontrar granja por su código REGA.
	 * @param nif
	 * @return
	 */
	public GranjaDTO findByRega(String rega) throws ServiceException;

	/**
	 * Encontrar granjas por encargado.
	 * @param encargadoId ID del encargado
	 * @return Listado de granjas
	 */
	public List<GranjaDTO> findByEncargadoId(Long encargadoId) throws ServiceException;
	
	/**
	 * Comprueba si un usuario tiene acceso a una granja.
	 * @param usuario
	 * @param granjaId
	 * @return True si tiene acceso y false en caso contrario.
	 * @throws ServiceException
	 */
	public boolean tieneAccesoAGranja(UsuarioDTO usuario, Long granjaId) throws ServiceException;

	/**
	 * Listar todas las granjas.
	 * @return
	 */
	public List<GranjaDTO> findAll() throws ServiceException;

	/**
	 * Buscar granjas por criterios.
	 * @param criteria filtros de busqueda.
	 * @return granjas encontradas.
	 */
	public List<GranjaDTO> findByCriteria(GranjaCriteria criteria) throws ServiceException;
}
