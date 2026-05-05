package com.andreina.ushi.dao;

import com.andreina.ushi.model.UsuarioDTO;

public class UsuarioDAOTest {

	private static void findByIdTest() {
		UsuarioDAO dao = new UsuarioDAO();
		UsuarioDTO usuario = dao.findById(1L);
		if (usuario != null) {
			System.out.println(usuario.getId() + " - " + usuario.getEmail());
		}
	}

	private static void findByEmailTest() {
		UsuarioDAO dao = new UsuarioDAO();
		UsuarioDTO usuario = dao.findByEmail("juan.garcia@email.com");
		if (usuario != null) {
			System.out.println(usuario.getId() + " - " + usuario.getEmail());
		}
	}

	public static void main(String[] args) {
		findByIdTest();
//		findByEmailTest();
	}
}
