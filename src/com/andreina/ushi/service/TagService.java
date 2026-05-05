package com.andreina.ushi.service;

import java.util.List;

import com.andreina.ushi.model.Tag;

public interface TagService {
	
	/**
	 * Crear un nuevo tag.
	 * @param tag
	 * @return
	 */
	public Tag create(Tag tag);

	/**
	 * Actualizar un tag existente.
	 * @param tag
	 * @return
	 */
	public Tag update(Tag tag);

	/**
	 * Eliminar un tag por su id.
	 * @param id
	 * @return
	 */
	public boolean delete(Long id);

	/**
	 * Encontrar tag por su id.
	 * @param id
	 * @return
	 */
	public Tag findById(Long id);

	/**
	 * Encontrar tag por su numero.
	 * @param numero
	 * @return
	 */
	public Tag findByNumero(String numero);

	/**
	 * Encontrar tags asociados a un animal.
	 * @param animalId
	 * @return
	 */
	public List<Tag> findByAnimalId(Long animalId);

	/**
	 * Encontrar tags con incidencias.
	 * @return
	 */
	public List<Tag> findConIncidencias();

	/**
	 * Encontrar tags disponibles (sin incidencias).
	 * @return
	 */
	public List<Tag> findDisponible();
}
