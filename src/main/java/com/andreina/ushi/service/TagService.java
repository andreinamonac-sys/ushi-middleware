package com.andreina.ushi.service;

import java.sql.Connection;
import java.util.List;

import com.andreina.ushi.exception.ServiceException;
import com.andreina.ushi.model.Tag;

public interface TagService {
	
	/**
	 * Crear un nuevo tag.
	 * @param tag
	 * @return
	 */
	public Long create(Tag tag)throws ServiceException;

	/**
	 * Actualizar un tag existente.
	 * @param tag
	 * @return
	 */
	public boolean update(Tag tag)throws ServiceException;

	/**
	 * Eliminar un tag por su id.
	 * @param id
	 * @return
	 */
	public boolean delete(Long id)throws ServiceException;

	/**
	 * Encontrar tag por su id.
	 * @param id
	 * @return
	 */
	public Tag findById(Long id)throws ServiceException;

	/**
	 * Encontrar tag por su numero.
	 * @param numero
	 * @return
	 */
	public Tag findByNumero(String numero)throws ServiceException;

	/**
	 * Encontrar tags asociados a un animal.
	 * @param animalId
	 * @return
	 */
	public List<Tag> findByAnimalId(Long animalId)throws ServiceException;

	/**
	 * Encontrar tags con incidencias.
	 * @return
	 */
	public List<Tag> findConIncidencias()throws ServiceException;

	/**
	 * Encontrar tags disponibles (sin incidencias).
	 * @return
	 */
	public List<Tag> findDisponible()throws ServiceException;
}
