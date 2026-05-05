package com.andreina.ushi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.andreina.ushi.model.Semilla;
import com.andreina.ushi.utils.DAOUtils;

public class SemillaDAO {

	private static final String BASE_QUERY = 
			"SELECT s.id, s.itp, s.merito_neto "
			+ "FROM semilla s";

	public SemillaDAO() {
		
	}

	/**
	 * Crear una nueva semilla.
	 * @param semilla
	 * @return
	 */
	public Semilla create(Semilla semilla) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = DAOUtils.getConnection();

			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append("INSERT INTO semilla (itp, merito_neto) VALUES (?, ?)");

			ps = c.prepareStatement(sqlBuilder.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
			int i = 1;
			ps.setInt(i++, semilla.getItp());
			ps.setInt(i++, semilla.getMeritoNeto());
			ps.executeUpdate();

			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				semilla.setId(rs.getLong(1));
			}
			return semilla;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	/**
	 * Actualizar una semilla existente.
	 * @param semilla
	 * @return
	 */
	public Semilla update(Semilla semilla) {
		Connection c = null;
		PreparedStatement ps = null;

		try {
			c = DAOUtils.getConnection();

			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append("UPDATE semilla SET itp = ?, merito_neto = ? WHERE id = ?");

			ps = c.prepareStatement(sqlBuilder.toString());
			int i = 1;
			ps.setInt(i++, semilla.getItp());
			ps.setInt(i++, semilla.getMeritoNeto());
			ps.setLong(i++, semilla.getId());
			ps.executeUpdate();
			return semilla;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(null, ps, c);
		}
		return null;
	}

	/**
	 * Eliminar una semilla por su id.
	 * @param id
	 * @return
	 */
	public boolean delete(Long id) {
		Connection c = null;
		PreparedStatement ps = null;

		try {
			c = DAOUtils.getConnection();

			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append("DELETE FROM semilla WHERE id = ?");

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
	 * Listar semillas con merito neto mayor que un minimo.
	 * @param minimo
	 * @return
	 */
	public List<Semilla> findByMeritoNetoGreaterThan(Integer minimo) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = DAOUtils.getConnection();

			StringBuilder sqlBuilder = new StringBuilder(BASE_QUERY);
			sqlBuilder.append(" WHERE s.merito_neto > ? ");
			sqlBuilder.append("ORDER BY s.merito_neto ASC ");

			ps = c.prepareStatement(sqlBuilder.toString());
			DAOUtils.setParameters(ps, minimo);
			rs = ps.executeQuery();

			List<Semilla> semillas = new ArrayList<>();
			while (rs.next()) {
				semillas.add(loadNext(rs));
			}
			return semillas;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	/**
	 * Listar semillas con mayor ITP limitado por un numero maximo de resultados.
	 * @param limite
	 * @return
	 */
	public List<Semilla> findTopByItp(Integer limite) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = DAOUtils.getConnection();

			StringBuilder sqlBuilder = new StringBuilder(BASE_QUERY);
			sqlBuilder.append(" ORDER BY s.itp DESC ");
			sqlBuilder.append("LIMIT ? ");

			ps = c.prepareStatement(sqlBuilder.toString());
			DAOUtils.setParameters(ps, limite);
			rs = ps.executeQuery();

			List<Semilla> semillas = new ArrayList<>();
			while (rs.next()) {
				semillas.add(loadNext(rs));
			}
			return semillas;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	/**
	 * Cargar una semilla desde el ResultSet actual.
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	private Semilla loadNext(ResultSet rs) throws Exception {
		int i = 1;
		Semilla semilla = new Semilla();
		semilla.setId(rs.getLong(i++));
		semilla.setItp(rs.getInt(i++));
		semilla.setMeritoNeto(rs.getInt(i++));
		return semilla;
	}
}
