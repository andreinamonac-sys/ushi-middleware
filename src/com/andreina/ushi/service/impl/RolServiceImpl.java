package com.andreina.ushi.service.impl;

import java.util.List;

import com.andreina.ushi.dao.RolDAO;
import com.andreina.ushi.model.Rol;
import com.andreina.ushi.service.RolService;

public class RolServiceImpl implements RolService {

	private RolDAO rolDAO = null;

	public RolServiceImpl() {
		rolDAO = new RolDAO();
	}

	@Override
	public List<Rol> findAll() {
		return rolDAO.findAll();
	}
}
