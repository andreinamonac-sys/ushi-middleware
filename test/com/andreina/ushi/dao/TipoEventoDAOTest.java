package com.andreina.ushi.dao;

import com.andreina.ushi.model.TipoEvento;

public class TipoEventoDAOTest {

	private static void findByIdTest() {
		TipoEventoDAO dao = new TipoEventoDAO();
		TipoEvento te = dao.findById(1L);
		if (te != null) {
			System.out.println(te.getId() + " - " + te.getNombre());
		}
	}

	public static void main(String[] args) {
		findByIdTest();
	}
}
