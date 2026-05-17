package com.andreina.ushi.service;

import java.util.List;

import com.andreina.ushi.dao.criteria.EventoCriteria;
import com.andreina.ushi.model.EventoDTO;
import com.andreina.ushi.model.Results;

public interface EventoService {
	
	/**
	 * Crear un nuevo evento.
	 * @param evento
	 * @return
	 */
	public EventoDTO create( EventoDTO evento) throws Exception;

	/**
	 * Anular un evento por su id.
	 * @param id
	 * @return
	 */
	public boolean anularEvento(Long id) throws Exception;

	/**
	 * Encontrar eventos por criterios de busqueda.
	 * @param criteria
	 * @return
	 */
	public Results<EventoDTO> findByCriteria(EventoCriteria criteria, 
			int from, int pageSize) throws Exception;
	/**
	 * Encontrar eventos por tipo de evento.
	 * @param tipoEventoId
	 * @return
	 */
	public List<EventoDTO> findByTipoEventoId(Long tipoEventoId) throws Exception;

	/**
	 * Encontrar eventos por animal.
	 * @param animalId
	 * @return
	 */
	public List<EventoDTO> findByAnimalId( Long animalId) throws Exception;

	/**
	 * Encontrar evento por id.
	 * @param id
	 * @return
	 */
	public EventoDTO findById(Long id) throws Exception;
}
