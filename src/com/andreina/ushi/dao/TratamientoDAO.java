package com.andreina.ushi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.andreina.ushi.model.DosisDTO;
import com.andreina.ushi.model.Tratamiento;
import com.andreina.ushi.utils.DAOUtils;

public class TratamientoDAO {

	private static final String BASE_QUERY = 
			"SELECT t.id, t.nombre "
			+ "FROM tratamiento t";

	public TratamientoDAO() {
		
	}

	/**
	 * Crear un nuevo tratamiento.
	 * @param tratamiento
	 * @return
	 */
	public Tratamiento create(Tratamiento tratamiento) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = DAOUtils.getConnection();

			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append("INSERT INTO tratamiento (nombre) VALUES (?)");

			ps = c.prepareStatement(sqlBuilder.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, tratamiento.getNombre());
			ps.executeUpdate();

			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				tratamiento.setId(rs.getLong(1));
			}
			return tratamiento;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	/**
	 * Actualizar un tratamiento existente.
	 * @param tratamiento
	 * @return
	 */
	public Tratamiento update(Tratamiento tratamiento) {
		Connection c = null;
		PreparedStatement ps = null;

		try {
			c = DAOUtils.getConnection();

			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append("UPDATE tratamiento SET nombre = ? WHERE id = ?");

			ps = c.prepareStatement(sqlBuilder.toString());
			ps.setString(1, tratamiento.getNombre());
			ps.setLong(2, tratamiento.getId());
			ps.executeUpdate();
			return tratamiento;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(null, ps, c);
		}
		return null;
	}

	/**
	 * Eliminar un tratamiento por su id.
	 * @param id
	 * @return
	 */
	public boolean delete(Long id) {
		Connection c = null;
		PreparedStatement ps = null;

		try {
			c = DAOUtils.getConnection();

			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append("DELETE FROM tratamiento WHERE id = ?");

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
	 * Listar tratamientos ordenados alfabeticamente por nombre.
	 * @return
	 */
	public List<Tratamiento> findByNombre() {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = DAOUtils.getConnection();

			StringBuilder sqlBuilder = new StringBuilder(BASE_QUERY);
			sqlBuilder.append(" ORDER BY t.nombre ASC ");

			ps = c.prepareStatement(sqlBuilder.toString());
			rs = ps.executeQuery();

			List<Tratamiento> tratamientos = new ArrayList<>();
			while (rs.next()) {
				tratamientos.add(loadNext(rs));
			}
			return tratamientos;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	/**
	 * Listar dosis por id de tratamiento.
	 * @param tratamientoId
	 * @return
	 */
	public List<DosisDTO> findDosisByTratamientoId(Long tratamientoId) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = DAOUtils.getConnection();

			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append("SELECT d.id, d.plazo_siguiente, d.num_dosis, d.tratamiento_id ");
			sqlBuilder.append("FROM dosis d WHERE d.tratamiento_id = ? ");

			ps = c.prepareStatement(sqlBuilder.toString());
			DAOUtils.setParameters(ps, tratamientoId);
			rs = ps.executeQuery();

			List<DosisDTO> dosis = new ArrayList<>();
			while (rs.next()) {
				dosis.add(loadNextDosis(rs));
			}
			return dosis;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	/**
	 * Crear una dosis para un tratamiento.
	 * @param dosis
	 * @return
	 */
	public DosisDTO addDosis(DosisDTO dosis) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = DAOUtils.getConnection();

			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append("INSERT INTO dosis (plazo_siguiente, num_dosis, tratamiento_id) ");
			sqlBuilder.append("VALUES (?, ?, ?)");

			ps = c.prepareStatement(sqlBuilder.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
			int i = 1;
			ps.setInt(i++, dosis.getPlazoSiguiente());
			ps.setInt(i++, dosis.getNumDosis());
			ps.setLong(i++, dosis.getTratamientoId());
			ps.executeUpdate();

			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				dosis.setId(rs.getLong(1));
			}
			return dosis;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	/**
	 * Actualizar una dosis existente.
	 * @param dosis
	 * @return
	 */
	public DosisDTO updateDosis(DosisDTO dosis) {
		Connection c = null;
		PreparedStatement ps = null;

		try {
			c = DAOUtils.getConnection();

			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append("UPDATE dosis SET plazo_siguiente = ?, num_dosis = ?, tratamiento_id = ? WHERE id = ?");

			ps = c.prepareStatement(sqlBuilder.toString());
			int i = 1;
			ps.setInt(i++, dosis.getPlazoSiguiente());
			ps.setInt(i++, dosis.getNumDosis());
			ps.setLong(i++, dosis.getTratamientoId());
			ps.setLong(i++, dosis.getId());
			ps.executeUpdate();
			return dosis;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(null, ps, c);
		}
		return null;
	}

	/**
	 * Eliminar una dosis por su id.
	 * @param id
	 * @return
	 */
	public boolean deleteDosis(Long id) {
		Connection c = null;
		PreparedStatement ps = null;

		try {
			c = DAOUtils.getConnection();

			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append("DELETE FROM dosis WHERE id = ?");

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
	 * Cargar un tratamiento desde el ResultSet actual.
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	private Tratamiento loadNext(ResultSet rs) throws Exception {
		int i = 1;
		Tratamiento tratamiento = new Tratamiento();
		tratamiento.setId(rs.getLong(i++));
		tratamiento.setNombre(rs.getString(i++));
		return tratamiento;
	}

	/**
	 * Cargar una dosis desde el ResultSet actual.
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	private DosisDTO loadNextDosis(ResultSet rs) throws Exception {
		int i = 1;
		DosisDTO dosis = new DosisDTO();
		dosis.setId(rs.getLong(i++));
		dosis.setPlazoSiguiente(rs.getInt(i++));
		dosis.setNumDosis(rs.getInt(i++));
		dosis.setTratamientoId(rs.getLong(i++));
		return dosis;
	}
}
