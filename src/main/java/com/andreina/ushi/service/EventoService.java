package com.andreina.ushi.service;

import java.util.List;

import com.andreina.ushi.dao.criteria.EventoCriteria;
import com.andreina.ushi.exception.ServiceException;
import com.andreina.ushi.model.Evento;
import com.andreina.ushi.model.EventoDTO;
import com.andreina.ushi.model.Results;

public interface EventoService {
	
	/**
	 * Crea un nuevo evento.
	 * @param evento
	 * @return
	 */
	public Evento create( Evento evento) throws ServiceException;

	/**
	 * Actualiza un evento.
	 * @param evento
	 * @return
	 * @throws ServiceException
	 */
	public boolean update(EventoDTO evento) throws ServiceException;

	/**
	 * Anular un evento por su id.
	 * @param id
	 * @return
	 */
	public boolean anularEvento(Long id) throws ServiceException;

	/**
	 * Encontrar eventos por criterios de busqueda.
	 * @param criteria
	 * @return
	 */
	public Results<EventoDTO> findByCriteria(EventoCriteria criteria, 
			int from, int pageSize) throws ServiceException;
	/**
	 * Encontrar eventos por tipo de evento.
	 * @param tipoEventoId
	 * @return
	 */
	public List<EventoDTO> findByTipoEventoId(Long tipoEventoId) throws ServiceException;

	/**
	 * Encontrar eventos por animal.
	 * @param animalId
	 * @return
	 */
	public List<EventoDTO> findByAnimalId( Long animalId) throws ServiceException;

	/**
	 * Encontrar evento por id.
	 * @param id
	 * @return
	 */
	public EventoDTO findById(Long id) throws ServiceException;
}
