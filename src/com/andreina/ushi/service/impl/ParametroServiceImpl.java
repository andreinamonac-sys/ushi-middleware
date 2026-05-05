package com.andreina.ushi.service.impl;

import java.util.List;

import com.andreina.ushi.dao.ParametroDAO;
import com.andreina.ushi.dao.criteria.ParametroCriteria;
import com.andreina.ushi.model.ParametroDTO;
import com.andreina.ushi.service.ParametroService;

public class ParametroServiceImpl implements ParametroService {

	private ParametroDAO parametroDAO = null;

	public ParametroServiceImpl() {
		parametroDAO = new ParametroDAO();
	}

	@Override
	public ParametroDTO create(ParametroDTO parametro) {
		return parametroDAO.create(parametro);
	}

	@Override
	public List<ParametroDTO> findByCriteria(ParametroCriteria criteria, int from, int pageSize) {
		return parametroDAO.findByCriteria(criteria,from,pageSize);
	}
}
