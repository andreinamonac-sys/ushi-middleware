package com.andreina.ushi.service;

import java.util.List;

import com.andreina.ushi.dao.criteria.EventoCriteria;
import com.andreina.ushi.model.EventoDTO;

public interface EventoService {
	
	/**
	 * Crear un nuevo evento.
	 * @param evento
	 * @return
	 */
	public EventoDTO create(EventoDTO evento);

	/**
	 * Anular un evento por su id.
	 * @param id
	 * @return
	 */
	public boolean anularEvento(Long id);

	/**
	 * Encontrar eventos por criterios de busqueda.
	 * @param criteria
	 * @return
	 */
	public List<EventoDTO> findByCriteria(EventoCriteria criteria, int from, int pageSize);

	/**
	 * Encontrar eventos por tipo de evento.
	 * @param tipoEventoId
	 * @return
	 */
	public List<EventoDTO> findByTipo(Long tipoEventoId);

	/**
	 * Encontrar eventos por animal.
	 * @param animalId
	 * @return
	 */
	public List<EventoDTO> findByAnimalId(Long animalId);

	/**
	 * Encontrar evento por id.
	 * @param id
	 * @return
	 */
	public EventoDTO findById(Long id);
}
