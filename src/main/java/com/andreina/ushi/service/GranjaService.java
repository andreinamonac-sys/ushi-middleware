package com.andreina.ushi.service;

import java.util.List;

import com.andreina.ushi.dao.criteria.GranjaCriteria;
import com.andreina.ushi.exception.ServiceException;
import com.andreina.ushi.model.Granja;
import com.andreina.ushi.model.GranjaDTO;

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
	 * Encontrar granja por su nif.
	 * @param nif
	 * @return
	 */
	public GranjaDTO findByNif(String nif) throws ServiceException;

	/**
	 * Encontrar granjas por encargado.
	 * @param usuarioId
	 * @return
	 */
	public List<GranjaDTO> findByEncargadoId(Long usuarioId) throws ServiceException;

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
