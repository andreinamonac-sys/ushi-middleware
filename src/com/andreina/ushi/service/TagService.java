package com.andreina.ushi.service;

import java.sql.Connection;
import java.util.List;

import com.andreina.ushi.model.Tag;

public interface TagService {
	
	/**
	 * Crear un nuevo tag.
	 * @param tag
	 * @return
	 */
	public Long create(Tag tag)throws Exception;

	/**
	 * Actualizar un tag existente.
	 * @param tag
	 * @return
	 */
	public boolean update(Tag tag)throws Exception;

	/**
	 * Eliminar un tag por su id.
	 * @param id
	 * @return
	 */
	public boolean delete(Long id)throws Exception;

	/**
	 * Encontrar tag por su id.
	 * @param id
	 * @return
	 */
	public Tag findById(Long id)throws Exception;

	/**
	 * Encontrar tag por su numero.
	 * @param numero
	 * @return
	 */
	public Tag findByNumero(String numero)throws Exception;

	/**
	 * Encontrar tags asociados a un animal.
	 * @param animalId
	 * @return
	 */
	public List<Tag> findByAnimalId(Long animalId)throws Exception;

	/**
	 * Encontrar tags con incidencias.
	 * @return
	 */
	public List<Tag> findConIncidencias()throws Exception;

	/**
	 * Encontrar tags disponibles (sin incidencias).
	 * @return
	 */
	public List<Tag> findDisponible()throws Exception;
}
