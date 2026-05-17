package com.andreina.ushi.dao;

import java.sql.Connection;

import com.andreina.ushi.model.Rol;
import com.andreina.ushi.utils.JDBCUtils;

public class RolDAOTest {

	private static void createDeleteTest(Connection c)throws Exception {
		RolDAO dao = new RolDAO();
		Rol rol = new Rol();
		rol.setNombre("Rol Test");
		Rol creado = dao.create(c,rol);
		if (creado != null) {
			System.out.println(creado.getId() + " - " + creado.getNombre());
			dao.delete(c, creado.getId());
		}
	}

	public static void main(String[] args) throws Exception {
		Connection c = JDBCUtils.getConnection();
		try {
			createDeleteTest(c);
		} finally {
			JDBCUtils.close(c, true);
		}
	
	}
}
