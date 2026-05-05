package com.andreina.ushi.service.impl;

import java.util.List;

import com.andreina.ushi.dao.GranjaDAO;
import com.andreina.ushi.model.GranjaDTO;
import com.andreina.ushi.service.GranjaService;

public class GranjaServiceImpl implements GranjaService {

	private GranjaDAO granjaDAO = null;

	public GranjaServiceImpl() {
		granjaDAO = new GranjaDAO();
	}

	@Override
	public GranjaDTO create(GranjaDTO granja) {
		return granjaDAO.create(granja);
	}

	@Override
	public GranjaDTO update(GranjaDTO granja) {
		return granjaDAO.update(granja);
	}

	@Override
	public boolean delete(Long id) {
		return granjaDAO.delete(id);
	}

	@Override
	public GranjaDTO findById(Long id) {
		return granjaDAO.findById(id);
	}

	@Override
	public GranjaDTO findByNif(String nif) {
		return granjaDAO.findByNif(nif);
	}

	@Override
	public List<GranjaDTO> findByEncargadoId(Long usuarioId) {
		return granjaDAO.findByEncargadoId(usuarioId);
	}

	@Override
	public List<GranjaDTO> findAll() {
		return granjaDAO.findAll();
	}
}
