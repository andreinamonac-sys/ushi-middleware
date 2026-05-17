package com.andreina.ushi.dao;

import java.sql.Connection;

import com.andreina.ushi.model.UsuarioDTO;
import com.andreina.ushi.utils.JDBCUtils;

public class UsuarioDAOTest {

	private static void findByIdTest(Connection c) throws Exception {
		UsuarioDAO dao = new UsuarioDAO();
		UsuarioDTO usuario = dao.findById(c, 1L);
		if (usuario != null) {
			System.out.println(usuario.getId() + " - " + usuario.getEmail());
		}
	}

	private static void findByEmailTest(Connection c)throws Exception {
		UsuarioDAO dao = new UsuarioDAO();
		UsuarioDTO usuario = dao.findByEmail(c, "juan.garcia@email.com");
		if (usuario != null) {
			System.out.println(usuario.getId() + " - " + usuario.getEmail());
		}
	}

	public static void main(String[] args) throws Exception {
		Connection c = JDBCUtils.getConnection();
		try {
		findByIdTest(c);
//		findByEmailTest();
		} finally {
			JDBCUtils.close(c, true);
		}
	}
}
