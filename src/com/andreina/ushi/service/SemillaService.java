package com.andreina.ushi.service;

import java.sql.Connection;
import java.util.List;

import com.andreina.ushi.model.Semilla;

public interface SemillaService {
	
	/**
	 * Crear una nueva semilla.
	 * @param semilla
	 * @return
	 */
	public Long create(Semilla semilla) throws Exception;

	/**
	 * Actualizar una semilla existente.
	 * @param semilla
	 * @return
	 */
	public boolean update(Semilla semilla)throws Exception;

	/**
	 * Eliminar una semilla por su id.
	 * @param id
	 * @return
	 */
	public boolean delete(Long id)throws Exception;

	/**
	 * Listar semillas con merito neto mayor que un minimo.
	 * @param minimo
	 * @return
	 */
	public List<Semilla> findByMeritoNetoGreaterThan(Integer minimo)throws Exception;

	/**
	 * Listar semillas con mayor ITP limitado por un numero maximo de resultados.
	 * @param limite
	 * @return
	 */
	public List<Semilla> findTopByItp(Integer limite)throws Exception;
}
