package com.andreina.ushi.dao;

import java.util.List;

import com.andreina.ushi.model.DosisDTO;
import com.andreina.ushi.model.Tratamiento;

public class TratamientoDAOTest {

	private static void findByNombreTest() {
		TratamientoDAO dao = new TratamientoDAO();
		List<Tratamiento> tratamientos = dao.findByNombre();
		if (tratamientos != null) {
			for (Tratamiento tratamiento : tratamientos) {
				System.out.println(tratamiento.getId() + " - " + tratamiento.getNombre());
			}
		}
	}

	private static void findDosisByTratamientoIdTest() {
		TratamientoDAO dao = new TratamientoDAO();
		List<DosisDTO> dosis = dao.findDosisByTratamientoId(1L);
		if (dosis != null) {
			for (DosisDTO d : dosis) {
				System.out.println(d.getId() + " - " + d.getNumDosis());
			}
		}
	}

	public static void main(String[] args) {
		findByNombreTest();
//		findDosisByTratamientoIdTest();
	}
}
