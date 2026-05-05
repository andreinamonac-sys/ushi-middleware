package com.andreina.ushi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.andreina.ushi.dao.criteria.ParametroCriteria;
import com.andreina.ushi.model.ParametroDTO;
import com.andreina.ushi.utils.DAOUtils;
import com.andreina.ushi.utils.SQLUtils;

public class ParametroDAO {

	private static final String BASE_QUERY = 
			"SELECT p.id, p.fecha_hora, p.valor_parametro, p.tag_id, p.animal_id, "
			+ "p.tipo_parametro_id, tp.nombre AS tipo_parametro_nombre "
			+ "FROM parametro p "
			+ "INNER JOIN tipo_parametro tp ON tp.id = p.tipo_parametro_id";

	public ParametroDAO() {
		
	}

	/**
	 * Crear un nuevo parametro.
	 * @param parametro
	 * @return
	 */
	public ParametroDTO create(ParametroDTO parametro) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = DAOUtils.getConnection();

			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append("INSERT INTO parametro (fecha_hora, valor_parametro, tag_id, animal_id, tipo_parametro_id) ");
			sqlBuilder.append("VALUES (?, ?, ?, ?, ?)");

			ps = c.prepareStatement(sqlBuilder.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
			int i = 1;
			ps.setTimestamp(i++, parametro.getFechaHora() == null ? null : new Timestamp(parametro.getFechaHora().getTime()));
			ps.setDouble(i++, parametro.getValorParametro());
			ps.setLong(i++, parametro.getTagId());
			ps.setLong(i++, parametro.getAnimalId());
			ps.setLong(i++, parametro.getTipoParametroId());

			ps.executeUpdate();

			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				parametro.setId(rs.getLong(1));
			}
			return parametro;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	/**
	 * Encontrar parametros por criterios de busqueda.
	 * @param criteria
	 * @return
	 */
	public List<ParametroDTO> findByCriteria(ParametroCriteria criteria, int from, int pageSize) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = DAOUtils.getConnection();

			StringBuilder sqlBuilder = new StringBuilder(BASE_QUERY);
			List<String> condiciones = new ArrayList<>();
			List<Object> parametros = new ArrayList<>();

			SQLUtils.addClause(criteria.getId(),
					condiciones, " p.id = ? ", parametros, criteria.getId());

			SQLUtils.addClause(criteria.getTagId(),
					condiciones, " p.tag_id = ? ", parametros, criteria.getTagId());

			SQLUtils.addClause(criteria.getAnimalId(),
					condiciones, " p.animal_id = ? ", parametros, criteria.getAnimalId());

			SQLUtils.addClause(criteria.getTipoParametroId(),
					condiciones, " p.tipo_parametro_id = ? ", parametros, criteria.getTipoParametroId());

			SQLUtils.addClause(criteria.getFechaDesde(),
					condiciones, " p.fecha_hora >= ? ", parametros,
					criteria.getFechaDesde() == null ? null : new Timestamp(criteria.getFechaDesde().getTime()));

			SQLUtils.addClause(criteria.getFechaHasta(),
					condiciones, " p.fecha_hora <= ? ", parametros,
					criteria.getFechaHasta() == null ? null : new Timestamp(criteria.getFechaHasta().getTime()));

			SQLUtils.addClause(criteria.getValorMin(),
					condiciones, " p.valor_parametro >= ? ", parametros, criteria.getValorMin());

			SQLUtils.addClause(criteria.getValorMax(),
					condiciones, " p.valor_parametro <= ? ", parametros, criteria.getValorMax());

			if (condiciones.size() > 0) {
				sqlBuilder.append(" WHERE ");
				sqlBuilder.append(String.join(" AND ", condiciones));
			}

			String sql = sqlBuilder.toString();
			System.out.println("SQL: " + sql);

			ps = c.prepareStatement(sql);
			int i = 1;
			for (Object parametro : parametros) {
				ps.setObject(i++, parametro);
			}

			rs = ps.executeQuery();

			List<ParametroDTO> lista = new ArrayList<>();
			while (rs.next()) {
				lista.add(loadNext(rs));
			}
			return lista;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	/**
	 * Cargar un parametro desde el ResultSet actual.
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	private ParametroDTO loadNext(ResultSet rs) throws Exception {
		int i = 1;
		ParametroDTO parametro = new ParametroDTO();

		parametro.setId(rs.getLong(i++));
		parametro.setFechaHora(rs.getTimestamp(i++));
		parametro.setValorParametro(rs.getDouble(i++));
		parametro.setTagId(rs.getLong(i++));
		parametro.setAnimalId(rs.getLong(i++));
		parametro.setTipoParametroId(rs.getLong(i++));
		parametro.setTipoParametroNombre(rs.getString(i++));

		return parametro;
	}
}
