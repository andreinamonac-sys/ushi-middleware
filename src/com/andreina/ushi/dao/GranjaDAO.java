package com.andreina.ushi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.andreina.ushi.model.GranjaDTO;
import com.andreina.ushi.utils.DAOUtils;

public class GranjaDAO {

	private static final String BASE_QUERY = 
			"SELECT g.id, g.nif, g.calle, g.CP, g.usuario_id, "
			+ "u.nombre AS usuario_nombre "
			+ "FROM granja g "
			+ "LEFT JOIN usuario u ON u.id = g.usuario_id";

	public GranjaDAO() {
		
	}

	/**
	 * Crear una nueva granja.
	 * @param granja
	 * @return
	 */
	public GranjaDTO create(GranjaDTO granja) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = DAOUtils.getConnection();
			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append("INSERT INTO granja (nif, calle, CP, usuario_id) ");
			sqlBuilder.append("VALUES (?, ?, ?, ?)");
			ps = c.prepareStatement(sqlBuilder.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
			int i = 1;
			ps.setString(i++, granja.getNif());
			ps.setString(i++, granja.getCalle());
			ps.setLong(i++, granja.getCp());
			ps.setLong(i++, granja.getUsuarioId());

			ps.executeUpdate();

			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				granja.setId(rs.getLong(1));
			}
			return granja;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	/**
	 * Actualizar una granja existente.
	 * @param granja
	 * @return
	 */
	public GranjaDTO update(GranjaDTO granja) {
		Connection c = null;
		PreparedStatement ps = null;
		try {
			c = DAOUtils.getConnection();
			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append("UPDATE granja SET nif = ?, calle = ?, CP = ?, usuario_id = ? ");
			sqlBuilder.append("WHERE id = ?");
			ps = c.prepareStatement(sqlBuilder.toString());
			int i = 1;
			ps.setString(i++, granja.getNif());
			ps.setString(i++, granja.getCalle());
			ps.setLong(i++, granja.getCp());
			ps.setLong(i++, granja.getUsuarioId());
			ps.setLong(i++, granja.getId());

			ps.executeUpdate();
			return granja;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(null, ps, c);
		}
		return null;
	}

	/**
	 * Eliminar una granja por su id (borrado logico). Solo permite granjas sin animales.
	 * @param id
	 */
	public boolean delete(Long id) {
		Connection c = null;
		PreparedStatement ps = null;

		try {
			c = DAOUtils.getConnection();
			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append("UPDATE granja g SET g.activo = 0 ");
			sqlBuilder.append("WHERE g.id = ? AND NOT EXISTS (SELECT 1 FROM animal a WHERE a.granja_id = g.id)");
			ps = c.prepareStatement(sqlBuilder.toString());
			int i = 1;
			ps.setLong(i++, id);
			int deleted = ps.executeUpdate();			
			return deleted>0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(null, ps, c);
		}
		return false;
	}

	/**
	 * Encontrar granja por su id.
	 * @param id
	 * @return
	 */
	public GranjaDTO findById(Long id) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = DAOUtils.getConnection();

			StringBuilder sqlBuilder = new StringBuilder(BASE_QUERY);
			sqlBuilder.append(" WHERE g.id = ? ");

			ps = c.prepareStatement(sqlBuilder.toString());
			DAOUtils.setParameters(ps, id);
			rs = ps.executeQuery();

			GranjaDTO granja = null;
			if (rs.next()) {
				granja = loadNext(rs);
			}
			return granja;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	/**
	 * Encontrar granja por su nif.
	 * @param nif
	 * @return
	 */
	public GranjaDTO findByNif(String nif) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = DAOUtils.getConnection();

			StringBuilder sqlBuilder = new StringBuilder(BASE_QUERY);
			sqlBuilder.append(" WHERE g.nif = ? ");

			ps = c.prepareStatement(sqlBuilder.toString());
			DAOUtils.setParameters(ps, nif);
			rs = ps.executeQuery();

			GranjaDTO granja = null;
			if (rs.next()) {
				granja = loadNext(rs);
			}
			return granja;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	/**
	 * Encontrar granjas por su encargado (usuario_id).
	 * @param usuarioId
	 * @return
	 */
	public List<GranjaDTO> findByEncargadoId(Long usuarioId) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = DAOUtils.getConnection();

			StringBuilder sqlBuilder = new StringBuilder(BASE_QUERY);
			sqlBuilder.append(" WHERE g.usuario_id = ? ");

			ps = c.prepareStatement(sqlBuilder.toString());
			DAOUtils.setParameters(ps, usuarioId);
			rs = ps.executeQuery();

			List<GranjaDTO> granjas = new ArrayList<>();
			while (rs.next()) {
				granjas.add(loadNext(rs));
			}
			return granjas;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	/**
	 * Listar todas las granjas.
	 * @return
	 */
	public List<GranjaDTO> findAll() {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = DAOUtils.getConnection();
			StringBuilder sqlBuilder = new StringBuilder(BASE_QUERY);
			sqlBuilder.append(" ORDER BY g.id ASC ");

			ps = c.prepareStatement(sqlBuilder.toString());
			rs = ps.executeQuery();

			List<GranjaDTO> granjas = new ArrayList<>();
			while (rs.next()) {
				granjas.add(loadNext(rs));
			}
			return granjas;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	/**
	 * Cargar una granja desde el ResultSet actual.
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	private GranjaDTO loadNext(ResultSet rs) throws Exception {
		int i = 1;
		GranjaDTO granja = new GranjaDTO();

		granja.setId(rs.getLong(i++));
		granja.setNif(rs.getString(i++));
		granja.setCalle(rs.getString(i++));
		granja.setCp(rs.getLong(i++));
		granja.setUsuarioId(rs.getLong(i++));
		granja.setUsuarioNombre(rs.getString(i++));

		return granja;
	}
}
