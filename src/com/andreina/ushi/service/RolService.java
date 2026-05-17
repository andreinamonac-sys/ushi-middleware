package com.andreina.ushi.service;

import java.sql.Connection;
import java.util.List;

import com.andreina.ushi.model.Rol;

public interface RolService {

	/**
	 * Listar todos los roles.
	 * @return
	 */
	public List<Rol> findAll() throws Exception;
}
