package com.andreina.ushi.dao;

import java.util.List;

import com.andreina.ushi.model.GranjaDTO;

public class GranjaDAOTest {

	private static void findByIdTest() {
		GranjaDAO dao = new GranjaDAO();
		GranjaDTO granja = dao.findById(1L);
		if (granja != null) {
			System.out.println(granja.getId() + " - " + granja.getNif());
		}
	}

	private static void findByNifTest() {
		GranjaDAO dao = new GranjaDAO();
		GranjaDTO granja = dao.findByNif("B12345678");
		if (granja != null) {
			System.out.println(granja.getId() + " - " + granja.getNif());
		}
	}

	private static void findByEncargadoIdTest() {
		GranjaDAO dao = new GranjaDAO();
		List<GranjaDTO> granjas = dao.findByEncargadoId(4L);
		if (granjas != null) {
			for (GranjaDTO granja : granjas) {
				System.out.println(granja.getId() + " - " + granja.getNif());
			}
		}
	}

	public static void main(String[] args) {
		findByIdTest();
//		findByNifTest();
//		findByEncargadoIdTest();
	}
}
