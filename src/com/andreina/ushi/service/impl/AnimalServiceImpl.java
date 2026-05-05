package com.andreina.ushi.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.andreina.ushi.dao.AnimalDAO;
import com.andreina.ushi.dao.criteria.AnimalCriteria;
import com.andreina.ushi.model.AnimalDTO;
import com.andreina.ushi.model.Results;
import com.andreina.ushi.service.AnimalService;

public class AnimalServiceImpl implements AnimalService {

	private static Logger logger = 
			LogManager.getLogger(AnimalServiceImpl.class.getName());

	
	private AnimalDAO animalDAO = null;

	public AnimalServiceImpl() {
		animalDAO = new AnimalDAO();
	}

	@Override
	public AnimalDTO create(AnimalDTO animal) {
		return animalDAO.create(animal);
	}

	@Override
	public AnimalDTO update(AnimalDTO animal) {
		return animalDAO.update(animal);
	}

	@Override
	public boolean delete(Long id) {
		return animalDAO.delete(id);
	}

	@Override
	public AnimalDTO findById(Long id) {
		return animalDAO.findById(id);
	}

	@Override
	public AnimalDTO findByNumRegistro(String numRegistro) {
		return animalDAO.findByNumRegistro(numRegistro);
	}

	@Override
	public Results<AnimalDTO> findByCriteria(AnimalCriteria criteria, int from, int pageSize) {
		return animalDAO.findByCriteria(criteria, from, pageSize);
	}
}
