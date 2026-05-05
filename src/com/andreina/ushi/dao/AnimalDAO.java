package com.andreina.ushi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.andreina.ushi.dao.criteria.AnimalCriteria;
import com.andreina.ushi.model.AnimalDTO;
import com.andreina.ushi.model.Results;
import com.andreina.ushi.utils.DAOUtils;
import com.andreina.ushi.utils.SQLUtils;

public class AnimalDAO {
	
	private static Logger logger = 
			LogManager.getLogger(AnimalDAO.class.getName());
	
	private static final String BASE_QUERY = 
		    "SELECT a.id, a.num_registro, a.granja_id, a.sexo_id, "
		    + "s.descripcion AS sexo_descripcion, "
		    + "a.id_padre_externo, "
		    + "a.id_madre_externa, "
		    + "a.id_madre_interna, "
		    + "madre_interna.num_registro AS num_registro_madre_interna, "
		    + "g.nif AS granja_nif "
		    + "FROM animal a "
		    + "INNER JOIN sexo s ON s.id = a.sexo_id "
		    + "LEFT JOIN animal madre_interna ON madre_interna.id = a.id_madre_interna "
		    + "INNER JOIN granja g ON g.id = a.granja_id";
	
	public AnimalDAO() {		
	}
	
	/**
	 * Crear un nuevo animal. 
	 * @param animal
	 * @return
	 */
	public AnimalDTO create(AnimalDTO animal) {
		
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			// obtener la conexion
			c = DAOUtils.getConnection();

			// preparar la consulta			
			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append("INSERT INTO evento (id, num_registro, id_padre_externo, id_madre_externa, granja_id,");
			sqlBuilder.append(" id_madre_interna, sexo_id) ");
			sqlBuilder.append("\nVALUES (?, ?, ?, ?, ?, ?, ?)");

			// crear el PreparedStatement
			ps = c.prepareStatement(sqlBuilder.toString(), PreparedStatement.RETURN_GENERATED_KEYS); 

			// asignar los valores a los parametros
			int i = 1; // contador de parametros
			ps.setString(i++, animal.getNumRegistro());
			

			// ejecutar la consulta
			ps.executeUpdate(); // para operaciones de insercion, actualizacion y borrado se pone update porque lo que se hace es MODIFICAR la BD

			// obtener la clave generada
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				animal.setId(rs.getLong(1));
			} 
			return animal;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	
	/**
	 * Actualizar un animal existente. El id del animal a actualizar debe estar presente en el DTO.
	 * @param animal
	 * @return
	 */
	public AnimalDTO update(AnimalDTO animal) {
		Connection c = null;
		PreparedStatement ps = null;
		try {
			c = DAOUtils.getConnection();
			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append("UPDATE evento SET id = ?, num_registro = ?, id_padre_externo = ?, ");
			sqlBuilder.append("id_madre_externa = ?, granja_id = ?, id_madre_interna = ?, sexo_id = ?, ");
			sqlBuilder.append("WHERE id = ?");
			ps = c.prepareStatement(sqlBuilder.toString());
			int i = 1;
			
			ps.setString(i++, animal.getNumRegistro());
			
			
			ps.executeUpdate();			
			return animal;		
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(null, ps, c);
		}
		return null;
	}
	
	/**
	 * Eliminar un animal por su id.
	 * @param id
	 */
	public boolean delete(Long id) {
		Connection c = null;
		PreparedStatement ps = null;
		
		try {
			c = DAOUtils.getConnection();
			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append("DELETE FROM animal WHERE id = ?");
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
	 * Encontrar animal por su id.
	 * @param id
	 * @return
	 */
	public AnimalDTO findById(Long id) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {			
			// obtener la conexion
			c = DAOUtils.getConnection();

			// preparar la consulta
			StringBuilder sqlBuilder = new StringBuilder(BASE_QUERY);
			sqlBuilder.append(" WHERE a.id = ? ");

			// crear el PreparedStatement y ejecutar la consulta.
			ps = c.prepareStatement(sqlBuilder.toString());
			DAOUtils.setParameters(ps, id);				
			rs = ps.executeQuery();

			AnimalDTO animal = null; 	
			
			if (rs.next()) {
				
				animal= loadNext(rs);
				
			}
			
			return animal;				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	/**
	 * Encontrar animal por su num_registro.
	 * @param numRegistro
	 * @return
	 */
	public AnimalDTO findByNumRegistro(String numRegistro) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {			
			// obtener la conexion
			c = DAOUtils.getConnection();

			// preparar la consulta
			StringBuilder sqlBuilder = new StringBuilder(BASE_QUERY);
			sqlBuilder.append(" WHERE a.num_registro = ? ");

			// crear el PreparedStatement y ejecutar la consulta.
			ps = c.prepareStatement(sqlBuilder.toString());
			DAOUtils.setParameters(ps, numRegistro);				
			rs = ps.executeQuery();

			AnimalDTO animal = null; 	
			
			if (rs.next()) {
				
				animal = loadNext(rs);
				
			}
			
			return animal;				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}
	
	/**
	 * Encontrar animales por sus atributos. Puede que alguno de los parametros sea null.
	 * @param criteria
	 * @return
	 */
	public Results<AnimalDTO> findByCriteria(AnimalCriteria criteria, int from, int pageSize) {
		
		if (logger.isInfoEnabled()) {
			logger.info("Criteria: {}", criteria);
		}
		
		Results<AnimalDTO> results = new Results<AnimalDTO>();
		
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
			        condiciones, " a.id = ? ", parametros, criteria.getId());

			SQLUtils.addClause(criteria.getNumRegistro(),
			        condiciones, " UPPER(a.num_registro) LIKE UPPER(?) ",
			        parametros, "%" + criteria.getNumRegistro() + "%");

			SQLUtils.addClause(criteria.getGranjaId(),
			        condiciones, " a.granja_id = ? ", parametros, criteria.getGranjaId());
			
			SQLUtils.addClause(criteria.getSexoId(),
			        condiciones, " a.sexo_id = ? ", parametros, criteria.getSexoId());
			

			if (condiciones.size() > 0) {
				sqlBuilder.append(" WHERE ");
				sqlBuilder.append( String.join(" AND ", condiciones) );
			}
			
			String sql = sqlBuilder.toString();
			
			if (logger.isInfoEnabled()) {
				logger.info("Criteria SQL: {}", sql);
			}

			ps = c.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			for (Object parametro: parametros) {
				ps.setObject(i++, parametro);
			}

			rs = ps.executeQuery();

			List<AnimalDTO> animales = new ArrayList<AnimalDTO>();
			 
			if(from>=1) {
				int count=0;
				rs.absolute(from);
				do {
					animales.add(loadNext(rs));
					++count;
				}while (count<pageSize && rs.next());							
			}
			
			int totalResults =SQLUtils.getTotalRows(rs);
			
			results.setPageResults(animales);
			results.setTotal(totalResults);
			
			return results;

		} catch (Exception e) {
			logger.error("Criteria: {}, Message: {}", criteria.toString(), e.getMessage(), e);
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	/**
	 * Cargar un animal desde el ResultSet actual.
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	private AnimalDTO loadNext(ResultSet rs) throws Exception {
	    int i = 1;
	   AnimalDTO animal = new AnimalDTO();

	    animal.setId(rs.getLong(i++));
	    animal.setNumRegistro(rs.getString(i++));
	    animal.setGranjaId(rs.getLong(i++));
	    animal.setSexoId(rs.getLong(i++));
	    
	

	    return animal;
	}
	
}


