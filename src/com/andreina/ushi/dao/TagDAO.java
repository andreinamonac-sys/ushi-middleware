package com.andreina.ushi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.andreina.ushi.model.Tag;
import com.andreina.ushi.utils.DAOUtils;

public class TagDAO {

	private static final String BASE_QUERY = 
			"SELECT t.id, t.numero, t.ultima_actualizacion, t.version_softfware, t.tipo, t.incidencias "
			+ "FROM tag t";

	public TagDAO() {
		
	}

	/**
	 * Crear un nuevo tag.
	 * @param tag
	 * @return
	 */
	public Tag create(Tag tag) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = DAOUtils.getConnection();

			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append("INSERT INTO tag (numero, ultima_actualizacion, version_softfware, tipo, incidencias) ");
			sqlBuilder.append("VALUES (?, ?, ?, ?, ?)");

			ps = c.prepareStatement(sqlBuilder.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
			int i = 1;
			ps.setString(i++, tag.getNumero());
			ps.setTimestamp(i++, tag.getUltimaActualizacion() == null ? null : new Timestamp(tag.getUltimaActualizacion().getTime()));
			ps.setString(i++, tag.getVersionSoftware());
			ps.setString(i++, tag.getTipo());
			ps.setString(i++, tag.getIncidencias());

			ps.executeUpdate();

			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				tag.setId(rs.getLong(1));
			}
			return tag;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	/**
	 * Actualizar un tag existente.
	 * @param tag
	 * @return
	 */
	public Tag update(Tag tag) {
		Connection c = null;
		PreparedStatement ps = null;

		try {
			c = DAOUtils.getConnection();

			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append("UPDATE tag SET numero = ?, ultima_actualizacion = ?, version_softfware = ?, ");
			sqlBuilder.append("tipo = ?, incidencias = ? WHERE id = ?");

			ps = c.prepareStatement(sqlBuilder.toString());
			int i = 1;
			ps.setString(i++, tag.getNumero());
			ps.setTimestamp(i++, tag.getUltimaActualizacion() == null ? null : new Timestamp(tag.getUltimaActualizacion().getTime()));
			ps.setString(i++, tag.getVersionSoftware());
			ps.setString(i++, tag.getTipo());
			ps.setString(i++, tag.getIncidencias());
			ps.setLong(i++, tag.getId());

			ps.executeUpdate();
			return tag;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(null, ps, c);
		}
		return null;
	}

	/**
	 * Eliminar un tag por su id.
	 * @param id
	 * @return
	 */
	public boolean delete(Long id) {
		Connection c = null;
		PreparedStatement ps = null;

		try {
			c = DAOUtils.getConnection();

			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append("DELETE FROM tag WHERE id = ?");

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
	 * Encontrar tag por su id.
	 * @param id
	 * @return
	 */
	public Tag findById(Long id) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = DAOUtils.getConnection();

			StringBuilder sqlBuilder = new StringBuilder(BASE_QUERY);
			sqlBuilder.append(" WHERE t.id = ? ");

			ps = c.prepareStatement(sqlBuilder.toString());
			DAOUtils.setParameters(ps, id);
			rs = ps.executeQuery();

			Tag tag = null;
			if (rs.next()) {
				tag = loadNext(rs);
			}
			return tag;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	/**
	 * Encontrar tag por su numero.
	 * @param numero
	 * @return
	 */
	public Tag findByNumero(String numero) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = DAOUtils.getConnection();

			StringBuilder sqlBuilder = new StringBuilder(BASE_QUERY);
			sqlBuilder.append(" WHERE t.numero = ? ");

			ps = c.prepareStatement(sqlBuilder.toString());
			DAOUtils.setParameters(ps, numero);
			rs = ps.executeQuery();

			Tag tag = null;
			if (rs.next()) {
				tag = loadNext(rs);
			}
			return tag;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	/**
	 * Encontrar tags asociados a un animal.
	 * @param animalId
	 * @return
	 */
	public List<Tag> findByAnimalId(Long animalId) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = DAOUtils.getConnection();

			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append("SELECT DISTINCT t.id, t.numero, t.ultima_actualizacion, t.version_softfware, t.tipo, t.incidencias ");
			sqlBuilder.append("FROM tag t ");
			sqlBuilder.append("INNER JOIN parametro p ON p.tag_id = t.id ");
			sqlBuilder.append("WHERE p.animal_id = ? ");

			ps = c.prepareStatement(sqlBuilder.toString());
			DAOUtils.setParameters(ps, animalId);
			rs = ps.executeQuery();

			List<Tag> tags = new ArrayList<>();
			while (rs.next()) {
				tags.add(loadNext(rs));
			}
			return tags;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	/**
	 * Encontrar tags con incidencias.
	 * @return
	 */
	public List<Tag> findConIncidencias() {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = DAOUtils.getConnection();

			StringBuilder sqlBuilder = new StringBuilder(BASE_QUERY);
			sqlBuilder.append(" WHERE t.incidencias IS NOT NULL ");
			sqlBuilder.append("AND t.incidencias <> '' ");
			sqlBuilder.append("AND UPPER(t.incidencias) <> 'NINGUNA' ");

			ps = c.prepareStatement(sqlBuilder.toString());
			rs = ps.executeQuery();

			List<Tag> tags = new ArrayList<>();
			while (rs.next()) {
				tags.add(loadNext(rs));
			}
			return tags;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	/**
	 * Encontrar tags disponibles (sin incidencias).
	 * @return
	 */
	public List<Tag> findDisponible() {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = DAOUtils.getConnection();

			StringBuilder sqlBuilder = new StringBuilder(BASE_QUERY);
			sqlBuilder.append(" WHERE t.incidencias IS NULL ");
			sqlBuilder.append("OR t.incidencias = '' ");
			sqlBuilder.append("OR UPPER(t.incidencias) = 'NINGUNA' ");

			ps = c.prepareStatement(sqlBuilder.toString());
			rs = ps.executeQuery();

			List<Tag> tags = new ArrayList<>();
			while (rs.next()) {
				tags.add(loadNext(rs));
			}
			return tags;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	/**
	 * Cargar un tag desde el ResultSet actual.
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	private Tag loadNext(ResultSet rs) throws Exception {
		int i = 1;
		Tag tag = new Tag();

		tag.setId(rs.getLong(i++));
		tag.setNumero(rs.getString(i++));
		tag.setUltimaActualizacion(rs.getTimestamp(i++));
		tag.setVersionSoftware(rs.getString(i++));
		tag.setTipo(rs.getString(i++));
		tag.setIncidencias(rs.getString(i++));

		return tag;
	}
}
