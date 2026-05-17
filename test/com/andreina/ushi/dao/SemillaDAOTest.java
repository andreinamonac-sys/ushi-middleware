package com.andreina.ushi.dao;

import java.sql.Connection;
import java.util.List;

import com.andreina.ushi.model.Semilla;
import com.andreina.ushi.utils.JDBCUtils;

public class SemillaDAOTest {

	private static void findTopByItpTest(Connection c) throws Exception {
		SemillaDAO dao = new SemillaDAO();
		List<Semilla> semillas = dao.findTopByItp(c, 5);
		if (semillas != null) {
			for (Semilla semilla : semillas) {
				System.out.println(semilla.getId() + " - " + semilla.getItp());
			}
		}
	}

	public static void main(String[] args)throws Exception {
		Connection c = JDBCUtils.getConnection();
		try {
		findTopByItpTest(c);
		}finally {
			JDBCUtils.close(c, true);
		}	
	}
}