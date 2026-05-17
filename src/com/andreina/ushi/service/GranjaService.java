package com.andreina.ushi.service;

import java.sql.Connection;
import java.util.List;

import com.andreina.ushi.model.GranjaDTO;

public interface GranjaService {
	
	/**
	 * Crear una nueva granja.
	 * @param granja
	 * @return
	 */
	public Long create(GranjaDTO granja) throws Exception;

	/**
	 * Actualizar una granja existente.
	 * @param granja
	 * @return
	 */
	public GranjaDTO update(GranjaDTO granja) throws Exception;

	/**
	 * Eliminar una granja por su id.
	 * @param id
	 * @return
	 */
	public boolean delete(Long id) throws Exception;

	/**
	 * Encontrar granja por su id.
	 * @param id
	 * @return
	 */
	public GranjaDTO findById(Long id) throws Exception;

	/**
	 * Encontrar granja por su nif.
	 * @param nif
	 * @return
	 */
	public GranjaDTO findByNif(String nif) throws Exception;

	/**
	 * Encontrar granjas por encargado.
	 * @param usuarioId
	 * @return
	 */
	public List<GranjaDTO> findByEncargadoId(Long usuarioId) throws Exception;

	/**
	 * Listar todas las granjas.
	 * @return
	 */
	public List<GranjaDTO> findAll() throws Exception;
}
