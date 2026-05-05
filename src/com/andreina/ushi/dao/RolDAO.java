package com.andreina.ushi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.andreina.ushi.model.Rol;
import com.andreina.ushi.utils.DAOUtils;

public class RolDAO {

	private static final String BASE_QUERY = 
			"SELECT r.id, r.nombre "
			+ "FROM rol r";

	public RolDAO() {
		
	}

	/**
	 * Listar todos los roles.
	 * @return
	 */
	public List<Rol> findAll() {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = DAOUtils.getConnection();
			StringBuilder sqlBuilder = new StringBuilder(BASE_QUERY);
			sqlBuilder.append(" ORDER BY r.id ASC ");

			ps = c.prepareStatement(sqlBuilder.toString());
			rs = ps.executeQuery();

			List<Rol> roles = new ArrayList<>();
			while (rs.next()) {
				Rol rol = new Rol();
				rol.setId(rs.getLong("id"));
				rol.setNombre(rs.getString("nombre"));
				roles.add(rol);
			}
			return roles;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	/**
	 * Crear un nuevo rol.
	 * @param rol
	 * @return
	 */
	public Rol create(Rol rol) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = DAOUtils.getConnection();

			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append("INSERT INTO rol (nombre) VALUES (?)");

			ps = c.prepareStatement(sqlBuilder.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, rol.getNombre());
			ps.executeUpdate();

			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				rol.setId(rs.getLong(1));
			}
			return rol;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	/**
	 * Actualizar un rol existente.
	 * @param rol
	 * @return
	 */
	public Rol update(Rol rol) {
		Connection c = null;
		PreparedStatement ps = null;

		try {
			c = DAOUtils.getConnection();

			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append("UPDATE rol SET nombre = ? WHERE id = ?");

			ps = c.prepareStatement(sqlBuilder.toString());
			ps.setString(1, rol.getNombre());
			ps.setLong(2, rol.getId());
			ps.executeUpdate();
			return rol;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(null, ps, c);
		}
		return null;
	}

	/**
	 * Eliminar un rol por su id.
	 * @param id
	 * @return
	 */
	public boolean delete(Long id) {
		Connection c = null;
		PreparedStatement ps = null;

		try {
			c = DAOUtils.getConnection();

			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append("DELETE FROM rol WHERE id = ?");

			ps = c.prepareStatement(sqlBuilder.toString());
			ps.setLong(1, id);
			ps.executeUpdate();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(null, ps, c);
		}
		return false;
	}
}
