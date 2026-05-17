package com.andreina.ushi.dao;

import java.sql.Connection;

import com.andreina.ushi.model.TipoEvento;
import com.andreina.ushi.utils.JDBCUtils;

public class TipoEventoDAOTest {

	private static void findByIdTest(Connection c)throws Exception {
		TipoEventoDAO dao = new TipoEventoDAO();
		TipoEvento te = dao.findById(c,1L);
		if (te != null) {
			System.out.println(te.getId() + " - " + te.getNombre());
		}
	}

	public static void main(String[] args)throws Exception {
		Connection c = JDBCUtils.getConnection();
		try {
		findByIdTest(c);
		} finally {
			JDBCUtils.close(c, true);
		}
	}
}
