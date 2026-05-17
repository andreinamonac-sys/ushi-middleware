package com.andreina.ushi.service;

import java.sql.Connection;
import java.util.List;

import com.andreina.ushi.dao.criteria.ParametroCriteria;
import com.andreina.ushi.model.ParametroDTO;

public interface ParametroService {
	
	/**
	 * Crear un nuevo parametro.
	 * @param parametro
	 * @return
	 */
	public Long create(ParametroDTO parametro) throws Exception;

	/**
	 * Encontrar parametros por criterios de busqueda.
	 * @param criteria
	 * @return
	 */
	public List<ParametroDTO> findByCriteria(ParametroCriteria criteria,
			int from, int pageSize) throws Exception;
}
