package com.andreina.ushi.service;

import java.util.List;

import com.andreina.ushi.model.Semilla;

public interface SemillaService {
	
	/**
	 * Crear una nueva semilla.
	 * @param semilla
	 * @return
	 */
	public Semilla create(Semilla semilla);

	/**
	 * Actualizar una semilla existente.
	 * @param semilla
	 * @return
	 */
	public Semilla update(Semilla semilla);

	/**
	 * Eliminar una semilla por su id.
	 * @param id
	 * @return
	 */
	public boolean delete(Long id);

	/**
	 * Listar semillas con merito neto mayor que un minimo.
	 * @param minimo
	 * @return
	 */
	public List<Semilla> findByMeritoNetoGreaterThan(Integer minimo);

	/**
	 * Listar semillas con mayor ITP limitado por un numero maximo de resultados.
	 * @param limite
	 * @return
	 */
	public List<Semilla> findTopByItp(Integer limite);
}
