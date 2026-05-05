package com.andreina.ushi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.andreina.ushi.model.TipoEvento;
import com.andreina.ushi.utils.DAOUtils;

public class TipoEventoDAO {

	private static final String BASE_QUERY = 
			"SELECT te.id, te.nombre "
			+ "FROM tipo_evento te";

	public TipoEventoDAO() {
		
	}

	/**
	 * Crear un nuevo tipo de evento.
	 * @param tipoEvento
	 * @return
	 */
	public TipoEvento create(TipoEvento tipoEvento) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = DAOUtils.getConnection();

			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append("INSERT INTO tipo_evento (nombre) VALUES (?)");

			ps = c.prepareStatement(sqlBuilder.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, tipoEvento.getNombre());
			ps.executeUpdate();

			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				tipoEvento.setId(rs.getLong(1));
			}
			return tipoEvento;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	/**
	 * Actualizar un tipo de evento existente.
	 * @param tipoEvento
	 * @return
	 */
	public TipoEvento update(TipoEvento tipoEvento) {
		Connection c = null;
		PreparedStatement ps = null;

		try {
			c = DAOUtils.getConnection();

			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append("UPDATE tipo_evento SET nombre = ? WHERE id = ?");

			ps = c.prepareStatement(sqlBuilder.toString());
			ps.setString(1, tipoEvento.getNombre());
			ps.setLong(2, tipoEvento.getId());
			ps.executeUpdate();
			return tipoEvento;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(null, ps, c);
		}
		return null;
	}

	/**
	 * Eliminar un tipo de evento por su id.
	 * @param id
	 * @return
	 */
	public boolean delete(Long id) {
		Connection c = null;
		PreparedStatement ps = null;

		try {
			c = DAOUtils.getConnection();

			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append("DELETE FROM tipo_evento WHERE id = ?");

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

	/**
	 * Encontrar tipo de evento por nombre.
	 * @param nombre
	 * @return
	 */
	public TipoEvento findByNombre(String nombre) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = DAOUtils.getConnection();

			StringBuilder sqlBuilder = new StringBuilder(BASE_QUERY);
			sqlBuilder.append(" WHERE te.nombre = ? ");

			ps = c.prepareStatement(sqlBuilder.toString());
			DAOUtils.setParameters(ps, nombre);
			rs = ps.executeQuery();

			TipoEvento tipoEvento = null;
			if (rs.next()) {
				tipoEvento = loadNext(rs);
			}
			return tipoEvento;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	/**
	 * Encontrar tipo de evento por id.
	 * @param id
	 * @return
	 */
	public TipoEvento findById(Long id) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = DAOUtils.getConnection();

			StringBuilder sqlBuilder = new StringBuilder(BASE_QUERY);
			sqlBuilder.append(" WHERE te.id = ? ");

			ps = c.prepareStatement(sqlBuilder.toString());
			DAOUtils.setParameters(ps, id);
			rs = ps.executeQuery();

			TipoEvento tipoEvento = null;
			if (rs.next()) {
				tipoEvento = loadNext(rs);
			}
			return tipoEvento;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	/**
	 * Cargar un tipo de evento desde el ResultSet actual.
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	private TipoEvento loadNext(ResultSet rs) throws Exception {
		int i = 1;
		TipoEvento tipoEvento = new TipoEvento();
		tipoEvento.setId(rs.getLong(i++));
		tipoEvento.setNombre(rs.getString(i++));
		return tipoEvento;
	}
}
