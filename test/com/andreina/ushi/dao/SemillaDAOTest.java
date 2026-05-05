package com.andreina.ushi.dao;

import java.util.List;

import com.andreina.ushi.model.Semilla;

public class SemillaDAOTest {

	private static void findTopByItpTest() {
		SemillaDAO dao = new SemillaDAO();
		List<Semilla> semillas = dao.findTopByItp(5);
		if (semillas != null) {
			for (Semilla semilla : semillas) {
				System.out.println(semilla.getId() + " - " + semilla.getItp());
			}
		}
	}

	public static void main(String[] args) {
		findTopByItpTest();
	}
}
