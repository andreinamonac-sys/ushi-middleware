package com.andreina.ushi.service.impl;

import java.util.List;

import com.andreina.ushi.dao.EventoDAO;
import com.andreina.ushi.dao.criteria.EventoCriteria;
import com.andreina.ushi.model.EventoDTO;
import com.andreina.ushi.service.EventoService;

public class EventoServiceImpl implements EventoService {

	private EventoDAO eventoDAO = null;

	public EventoServiceImpl() {
		eventoDAO = new EventoDAO();
	}

	@Override
	public EventoDTO create(EventoDTO evento) {
		return eventoDAO.create(evento);
	}

	@Override
	public boolean anularEvento(Long id) {
		return eventoDAO.anularEvento(id);
	}

	@Override
	public List<EventoDTO> findByCriteria(EventoCriteria criteria, int from, int pageSize) {
		return eventoDAO.findByCriteria(criteria, from, pageSize);
	}

	@Override
	public List<EventoDTO> findByTipo(Long tipoEventoId) {
		return eventoDAO.findByTipo(tipoEventoId);
	}

	@Override
	public List<EventoDTO> findByAnimalId(Long animalId) {
		return eventoDAO.findByAnimalId(animalId);
	}

	@Override
	public EventoDTO findById(Long id) {
		return eventoDAO.findById(id);
	}
}
