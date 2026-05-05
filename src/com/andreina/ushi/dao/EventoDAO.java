package com.andreina.ushi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.andreina.ushi.dao.criteria.EventoCriteria;
import com.andreina.ushi.model.EventoDTO;
import com.andreina.ushi.utils.DAOUtils;
import com.andreina.ushi.utils.SQLUtils;

public class EventoDAO {
	
	private static final String BASE_QUERY = 
			"SELECT e.id, e.fecha_hora, e.descripcion, e.tipo_evento_id, "
			+ "te.nombre AS tipo_evento_nombre, "
			+ "e.animal_id, "
			+ "a.num_registro AS animal_num_registro, "
			+ "e.semilla_id, "
			+ "e.valor_diagnostico, "
			+ "e.dosis_id, "
			+ "d.num_dosis, "
			+ "d.plazo_siguiente, "
			+ "d.tratamiento_id, "
			+ "t.nombre AS tratamiento_nombre, "
			+ "e.cria_id, "
			+ "e.veterinario_id, "
			+ "v.nombre AS veterinario_nombre, "
			+ "v.apellido1 AS veterinario_apellido1, "
			+ "v.apellido2 AS veterinario_apellido2 "
			+ "FROM evento e "
			+ "INNER JOIN tipo_evento te ON te.id = e.tipo_evento_id "
			+ "INNER JOIN animal a ON a.id = e.animal_id "
			+ "LEFT JOIN dosis d ON d.id = e.dosis_id "
			+ "LEFT JOIN tratamiento t ON t.id = d.tratamiento_id "
			+ "LEFT JOIN animal cria ON cria.id = e.cria_id "
			+ "LEFT JOIN usuario v ON v.id = e.veterinario_id";

	public EventoDAO() { 		
		
	}

	/**
	 * Crear un nuevo evento.
	 * @param evento
	 * @return
	 */
	public EventoDTO create(EventoDTO evento) {

		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			// obtener la conexion
			c = DAOUtils.getConnection();

			// preparar la consulta			
			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append("INSERT INTO evento (id, fecha_hora, descripcion, tipo_evento_id, animal_id,");
			sqlBuilder.append(" semilla_id, valor_diagnostico, dosis_id, cria_id, veterinario_id) ");
			sqlBuilder.append("\nVALUES (?, ?, ?, ?, ?, ?, ?,?, ?, ?)");

			// crear el PreparedStatement
			ps = c.prepareStatement(sqlBuilder.toString(), PreparedStatement.RETURN_GENERATED_KEYS); 

			// asignar los valores a los parametros
			int i = 1; // contador de parametros
			ps.setString(i++, evento.getNombreTipoEvento());
			

			// ejecutar la consulta
			ps.executeUpdate(); // para operaciones de insercion, actualizacion y borrado se pone update porque lo que se hace es MODIFICAR la BD

			// obtener la clave generada
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				evento.setId(rs.getLong(1));
			} 
			return evento;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	/**
	 * Anular un evento por su id.
	 * @param id
	 * @return
	 */

	public boolean anularEvento(Long id) {
		Connection c = null;
		PreparedStatement ps = null;
		
		try {
			c = DAOUtils.getConnection();
			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append("UPDATE evento SET anulado = 1 WHERE id = ?");
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
	 * Encontrar evento por criterios de busqueda	
	 * @param criteria
	 * @return
	 */

	public List<EventoDTO> findByCriteria( EventoCriteria criteria, int from, int pageSize ) { 		

		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {		

			c = DAOUtils.getConnection();

			StringBuilder sqlBuilder = new StringBuilder(BASE_QUERY);
			
			
			List<String> condiciones = new ArrayList<>();
			List<Object> parametros = new ArrayList<>();

			// Obtener los valores de los criterios
			SQLUtils.addClause(criteria.getId(),
			        condiciones, " e.id = ? ", parametros, criteria.getId());

			SQLUtils.addClause(criteria.getDescripcion(),
			        condiciones, " UPPER(e.descripcion) LIKE UPPER(?) ",
			        parametros, "%" + criteria.getDescripcion() + "%");

			SQLUtils.addClause(criteria.getFechaDesde(),
			        condiciones, " e.fecha_hora >= ? ",
			        parametros,
			        criteria.getFechaDesde() == null ? null :
			            new java.sql.Timestamp(criteria.getFechaDesde().getTime()));

			SQLUtils.addClause(criteria.getFechaHasta(),
			        condiciones, " e.fecha_hora <= ? ",
			        parametros,
			        criteria.getFechaHasta() == null ? null :
			            new java.sql.Timestamp(criteria.getFechaHasta().getTime()));

			if (condiciones.size() > 0) {
				sqlBuilder.append(" WHERE ");
				sqlBuilder.append( String.join(" AND ", condiciones) );
			}
			String sql = sqlBuilder.toString();
			System.out.println("SQL: " + sql);

			ps = c.prepareStatement(sql);

			int i = 1;
			for (Object parametro: parametros) {
				ps.setObject(i++, parametro);
			}

			rs = ps.executeQuery();

			List<EventoDTO> eventos = new ArrayList<>();
			while (rs.next()) {							
				eventos.add(loadNext(rs));
				
			}

			return eventos;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	/**
	 * Encontrar eventos por su tipo de evento.
	 * @param tipoEventoId
	 * @return
	 */
	public List<EventoDTO> findByTipo(Long tipoEventoId) {

		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {			
			// obtener la conexion
			c = DAOUtils.getConnection();

			// preparar la consulta
			StringBuilder sqlBuilder = new StringBuilder(BASE_QUERY);
			sqlBuilder.append(" WHERE e.tipo_evento_id = ? ");

			// crear el PreparedStatement y ejecutar la consulta.
			ps = c.prepareStatement(sqlBuilder.toString());
			DAOUtils.setParameters(ps, tipoEventoId);				
			rs = ps.executeQuery();

			List<EventoDTO> eventos = new ArrayList<>();	
			
			while (rs.next()) {
				
				eventos.add(loadNext(rs));
				
			}
			
			return eventos;				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	/**
	 * Encontrar eventos por su animal_id.
	 * @param animalId
	 * @return
	 */
	public List<EventoDTO> findByAnimalId(Long animalId) {

		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {			
			// obtener la conexion
			c = DAOUtils.getConnection();

			// preparar la consulta
			StringBuilder sqlBuilder = new StringBuilder(BASE_QUERY);
			sqlBuilder.append(" WHERE e.animal_id = ? ");

			// crear el PreparedStatement y ejecutar la consulta.
			ps = c.prepareStatement(sqlBuilder.toString());
			DAOUtils.setParameters(ps, animalId);				
			rs = ps.executeQuery();

			List<EventoDTO> eventos = new ArrayList<>();	
			
			while (rs.next()) {
				
				eventos.add(loadNext(rs));
				
			}
			
			return eventos;				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	/**
	 * Encontrar evento por su id. 
	 * @param id
	 * @return
	 */
	public EventoDTO findById(Long id) {

		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {			
			// obtener la conexion
			c = DAOUtils.getConnection();

			// preparar la consulta
			StringBuilder sqlBuilder = new StringBuilder(BASE_QUERY);
			sqlBuilder.append(" WHERE e.id = ? ");

			// crear el PreparedStatement y ejecutar la consulta.
			ps = c.prepareStatement(sqlBuilder.toString());
			DAOUtils.setParameters(ps, id);				
			rs = ps.executeQuery();

			EventoDTO evento = null; 	
			
			if (rs.next()) {
				
				evento= loadNext(rs);
				
			}
			
			return evento;				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	/**
	 * Actualizar un evento existente.
	 * @param evento
	 * @return
	 */
	public EventoDTO update(EventoDTO evento) {
		Connection c = null;
		PreparedStatement ps = null;
		try {
			c = DAOUtils.getConnection();
			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append("UPDATE evento SET id = ?, fecha_hora = ?, descripcion = ?, ");
			sqlBuilder.append("tipo_evento_id = ?, animal_id = ?, semilla_id = ?, valor_diagnostico = ?, dosis_id = ? ");
			sqlBuilder.append("WHERE id = ?");
			ps = c.prepareStatement(sqlBuilder.toString());
			int i = 1;
			
			ps.setString(i++, evento.getNombreTipoEvento());
			
			
			ps.executeUpdate();			
			return evento;		
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(null, ps, c);
		}
		return null;
	}

	/**
	 * Eliminar un evento por su id.
	 * @param id
	 * @return
	 */

	public boolean delete(Long id) {
		return anularEvento(id);
	}
	
	/**
	 * Cargar un evento desde el ResultSet actual.
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	private EventoDTO loadNext(ResultSet rs) throws Exception {
    int i = 1;
    EventoDTO evento = new EventoDTO();

    evento.setId(rs.getLong(i++));
    evento.setFechaDesde(rs.getTimestamp(i++));
    evento.setDescripcion(rs.getString(i++));
    evento.setTipoEventoId(rs.getLong(i++));
    evento.setNombreTipoEvento(rs.getString(i++));
    evento.setAnimalId(rs.getLong(i++));
    rs.getString(i++); // animal_num_registro (String in DB)
    evento.setSemillaId(rs.getObject(i++, Long.class));
    evento.setValorDiagnostico(rs.getString(i++));
    evento.setDosisId(rs.getObject(i++, Long.class));
    evento.setNumDosis(rs.getObject(i++, Long.class));
    rs.getObject(i++, Long.class); // plazo_siguiente
    rs.getObject(i++, Long.class); // tratamiento_id
    rs.getString(i++); // tratamiento_nombre
    evento.setCriaId(rs.getObject(i++, Long.class));
    evento.setVeterinarioId(rs.getObject(i++, Long.class));
    rs.getString(i++); // veterinario_nombre
    rs.getString(i++); // veterinario_apellido1
    rs.getString(i++); // veterinario_apellido2

    return evento;
}


}
