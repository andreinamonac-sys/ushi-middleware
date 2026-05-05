package com.andreina.ushi.dao;

import com.andreina.ushi.model.Rol;

public class RolDAOTest {

	private static void createDeleteTest() {
		RolDAO dao = new RolDAO();
		Rol rol = new Rol();
		rol.setNombre("Rol Test");
		Rol creado = dao.create(rol);
		if (creado != null) {
			System.out.println(creado.getId() + " - " + creado.getNombre());
			dao.delete(creado.getId());
		}
	}

	public static void main(String[] args) {
		createDeleteTest();
	}
}
