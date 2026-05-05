package com.andreina.ushi.service.impl;

import java.util.List;

import com.andreina.ushi.dao.SemillaDAO;
import com.andreina.ushi.model.Semilla;
import com.andreina.ushi.service.SemillaService;

public class SemillaServiceImpl implements SemillaService {

	private SemillaDAO semillaDAO = null;

	public SemillaServiceImpl() {
		semillaDAO = new SemillaDAO();
	}

	@Override
	public Semilla create(Semilla semilla) {
		return semillaDAO.create(semilla);
	}

	@Override
	public Semilla update(Semilla semilla) {
		return semillaDAO.update(semilla);
	}

	@Override
	public boolean delete(Long id) {
		return semillaDAO.delete(id);
	}

	@Override
	public List<Semilla> findByMeritoNetoGreaterThan(Integer minimo) {
		return semillaDAO.findByMeritoNetoGreaterThan(minimo);
	}

	@Override
	public List<Semilla> findTopByItp(Integer limite) {
		return semillaDAO.findTopByItp(limite);
	}
}
