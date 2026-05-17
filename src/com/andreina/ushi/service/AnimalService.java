package com.andreina.ushi.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.andreina.ushi.dao.criteria.AnimalCriteria;
import com.andreina.ushi.model.AnimalDTO;
import com.andreina.ushi.model.Results;

public interface AnimalService {
	
	/**
	 * Crear un nuevo animal.
	 * @param animal
	 * @return
	 */
	public AnimalDTO create( AnimalDTO animal) throws Exception;
		

	/**
	 * Actualizar un animal existente.
	 * @param animal
	 * @return
	 */
	public boolean update(AnimalDTO animal) throws Exception;

	/**
	 * Eliminar un animal por su id.
	 * @param id
	 * @return
	 */
	public boolean delete(Long id) throws Exception;

	/**
	 * Encontrar animal por su id.
	 * @param id
	 * @return
	 */
	public AnimalDTO findById(Long id) throws Exception;

	/**
	 * Encontrar animal por su num_registro.
	 * @param numRegistro
	 * @return
	 */
	public AnimalDTO findByNumRegistro(String numRegistro) throws Exception;

	/**
	 * Encontrar animales por criterios de busqueda.
	 * @param criteria
	 * @return
	 */
	public Results<AnimalDTO> findByCriteria(AnimalCriteria criteria,int from, int pageSize)
			throws Exception;
}
