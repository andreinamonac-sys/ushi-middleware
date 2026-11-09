package com.andreina.ushi.service;

import com.andreina.ushi.dao.criteria.AnimalCriteria;
import com.andreina.ushi.exception.ServiceException;
import com.andreina.ushi.model.Animal;
import com.andreina.ushi.model.AnimalDTO;
import com.andreina.ushi.model.Results;

public interface AnimalService {
	
	/**
	 * Crear un nuevo animal.
	 * @param animal
	 * @return
	 */
	public Animal create( Animal animal) throws ServiceException;
		

	/**
	 * Actualizar un animal existente.
	 * @param animal
	 * @return
	 */
	public boolean update(AnimalDTO animal) throws ServiceException;

	/**
	 * Eliminar un animal por su id.
	 * @param id
	 * @return
	 */
	public boolean delete(Long id) throws ServiceException;

	/**
	 * Encontrar animal por su id.
	 * @param id
	 * @return
	 */
	public AnimalDTO findById(Long id) throws ServiceException;

	/**
	 * Encontrar animal por su num_registro.
	 * @param numRegistro
	 * @return
	 */
	public AnimalDTO findByNumRegistro(String numRegistro) throws ServiceException;

	/**
	 * Encontrar animales por criterios de busqueda.
	 * @param criteria
	 * @return
	 */
	public Results<AnimalDTO> findByCriteria(AnimalCriteria criteria,int from, int pageSize)
			throws ServiceException;
}
