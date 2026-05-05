package com.andreina.ushi.dao;

import java.util.List;

import com.andreina.ushi.dao.criteria.ParametroCriteria;
import com.andreina.ushi.model.ParametroDTO;

public class ParametroDAOTest {

	private static void findByCriteriaTest() {
		ParametroDAO dao = new ParametroDAO();
		ParametroCriteria criteria = new ParametroCriteria();
		criteria.setAnimalId(1L);
		List<ParametroDTO> parametros = dao.findByCriteria(criteria);
		if (parametros != null) {
			for (ParametroDTO parametro : parametros) {
				System.out.println(parametro.getId() + " - " + parametro.getValorParametro());
			}
		}
	}

	public static void main(String[] args) {
		findByCriteriaTest();
	}
}
