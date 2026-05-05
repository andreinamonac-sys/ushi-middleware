package com.andreina.ushi.dao;

import com.andreina.ushi.dao.criteria.AnimalCriteria;
import com.andreina.ushi.model.AnimalDTO;
import com.andreina.ushi.model.Results;

public class AnimalDAOTest {

	private static void findByIdTest() {
		AnimalDAO dao = new AnimalDAO();
		AnimalDTO animal = dao.findById(1L);
		if (animal != null) {
			System.out.println(animal.getId() + " - " + animal.getNumRegistro());
		}
	}

	private static void findByNumRegistroTest() {
		AnimalDAO dao = new AnimalDAO();
		AnimalDTO animal = dao.findByNumRegistro("AN-001-2020");
		if (animal != null) {
			System.out.println(animal.getId() + " - " + animal.getNumRegistro());
		}
	}

	private static void findByCriteriaTest() {
		AnimalDAO dao = new AnimalDAO();
		AnimalCriteria criteria = new AnimalCriteria();
		// criteria.setGranjaId(1L);
		Results<AnimalDTO> results = dao.findByCriteria(criteria, 1, Integer.MAX_VALUE);
		if (results != null) {
			for (AnimalDTO animal : results.getPageResults()) {
				System.out.println(animal.getId() + " - " + animal.getNumRegistro());
			}
		}
	}

	public static void main(String[] args) {
		findByIdTest();
 		findByNumRegistroTest();
		findByCriteriaTest();
	}
}
