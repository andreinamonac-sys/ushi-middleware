package com.andreina.ushi.dao;

import java.sql.Connection;
import java.util.List;

import com.andreina.ushi.model.DosisDTO;
import com.andreina.ushi.model.Tratamiento;
import com.andreina.ushi.utils.JDBCUtils;

public class TratamientoDAOTest {

	private static void findByNombreTest(Connection c) throws Exception {
		TratamientoDAO dao = new TratamientoDAO();
		List<Tratamiento> tratamientos = dao.findByNombre(c);
		if (tratamientos != null) {
			for (Tratamiento tratamiento : tratamientos) {
				System.out.println(tratamiento.getId() + " - " + tratamiento.getNombre());
			}
		}
	}

	private static void findDosisByTratamientoIdTest(Connection c) throws Exception {
		TratamientoDAO dao = new TratamientoDAO();
		List<DosisDTO> dosis = dao.findDosisByTratamientoId(c,1L);
		if (dosis != null) {
			for (DosisDTO d : dosis) {
				System.out.println(d.getId() + " - " + d.getNumDosis());
			}
		}
	}

	public static void main(String[] args)throws Exception {
		Connection c = JDBCUtils.getConnection();
		try {
		findByNombreTest(c);
//		findDosisByTratamientoIdTest();
		} finally {
			JDBCUtils.close(c, true);
		}
	}
}
