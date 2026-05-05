package com.andreina.ushi.service;

import java.util.List;

import com.andreina.ushi.model.GranjaDTO;

public interface GranjaService {
	
	/**
	 * Crear una nueva granja.
	 * @param granja
	 * @return
	 */
	public GranjaDTO create(GranjaDTO granja);

	/**
	 * Actualizar una granja existente.
	 * @param granja
	 * @return
	 */
	public GranjaDTO update(GranjaDTO granja);

	/**
	 * Eliminar una granja por su id.
	 * @param id
	 * @return
	 */
	public boolean delete(Long id);

	/**
	 * Encontrar granja por su id.
	 * @param id
	 * @return
	 */
	public GranjaDTO findById(Long id);

	/**
	 * Encontrar granja por su nif.
	 * @param nif
	 * @return
	 */
	public GranjaDTO findByNif(String nif);

	/**
	 * Encontrar granjas por encargado.
	 * @param usuarioId
	 * @return
	 */
	public List<GranjaDTO> findByEncargadoId(Long usuarioId);

	/**
	 * Listar todas las granjas.
	 * @return
	 */
	public List<GranjaDTO> findAll();
}
