package com.andreina.ushi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.andreina.ushi.dao.criteria.AnimalCriteria;
import com.andreina.ushi.model.AnimalDTO;
import com.andreina.ushi.model.Results;
import com.andreina.ushi.utils.DAOUtils;
import com.andreina.ushi.utils.JDBCUtils;
import com.andreina.ushi.utils.SQLUtils;

public class AnimalDAO {

	private static Logger logger = 
			LogManager.getLogger(AnimalDAO.class.getName());

	private static final String BASE_QUERY = 
			"SELECT a.id, a.num_registro,a.sexo_id, a.fecha_nacimiento,a.fecha_alta,"
					+ "a.fecha_baja,a.granja_id, "
					+ "s.descripcion AS sexo_descripcion, "
					+ "a.id_padre_externo, "
					+ "a.id_madre_externa, "
					+ "a.id_madre_interna, "
					+ "madre_interna.num_registro AS num_registro_madre_interna, "
					+ "g.nif AS granja_nif "
					+ "FROM animal a "
					+ "INNER JOIN sexo s ON s.id = a.sexo_id "
					+ "LEFT JOIN animal madre_interna ON madre_interna.id = a.id_madre_interna "
					+ "INNER JOIN granja g ON g.id = a.granja_id ";
					
	private static final String ORDER_BY = " ORDER BY a.id DESC";
	
	public AnimalDAO() {		
	}

	/**
	 * Crear un nuevo animal. 
	 * @param animal
	 * @throws Exception
	 * @return El animal creado con su id generado.
	 */
	public AnimalDTO create(Connection c, AnimalDTO animal) throws Exception {
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    try {
	        String sql = " INSERT INTO animal (num_registro, sexo_id, "
	                   + "fecha_nacimiento, fecha_alta, fecha_baja, granja_id, "
	                   + "id_padre_externo, id_madre_externa, id_madre_interna) "
	                   + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) " + ORDER_BY;
	        
	        logger.debug("Executing SQL: {}", sql);
	        ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	        DAOUtils.setParameters(ps,animal.getNumRegistro(), 
	                              animal.getSexoId(), animal.getFechaNacimiento(),
	                              animal.getFechaAlta(), animal.getFechaBaja(),
	                              animal.getGranjaId(), animal.getPadreExternoId(),
	                              animal.getMadreExternaId(), animal.getMadreInternaId());
	        int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                   animal.setId(rs.getLong(1));
                   logger.info("Animal creado con id: {}", animal.getId());
                }
            }
	        
	    } catch (Exception e) {
	    	logger.error("Error creando animal con numRegistro: {} - Message: {}", 
	                animal.getNumRegistro(), e.getMessage(), e);
	        throw e;
	    } finally {
	        JDBCUtils.close(rs, ps);
	    }
	    return animal;
	}
	/**
	 * Actualizar un animal existente. El id del animal a actualizar debe estar presente en el DTO.
	 * @param animal
	 * @throws Exception
	 * @return Animal actualizado y filas afectadas.
	 */
	public boolean update(Connection c, AnimalDTO animal) throws Exception {

	    PreparedStatement ps = null;
	    try {
	        java.sql.Date fechaNac = new java.sql.Date(animal.getFechaNacimiento().getTime());
	        java.sql.Timestamp fechaAlta = new java.sql.Timestamp(animal.getFechaAlta().getTime());
	        java.sql.Timestamp fechaBaja = animal.getFechaBaja() != null
	                ? new java.sql.Timestamp(animal.getFechaBaja().getTime()) : null;

	        String sql = "UPDATE animal SET num_registro = ?, sexo_id = ?, fecha_nacimiento = ?, "
	                   + "fecha_alta = ?, fecha_baja = ?, granja_id = ?, "
	                   + "id_padre_externo = ?, id_madre_externa = ?, id_madre_interna = ? "
	                   + "WHERE id = ?"+ ORDER_BY;

	        logger.info("Actualizando animal con id: {}", animal.getId());
	        logger.debug("Executing SQL: {}", sql);

	        ps = c.prepareStatement(sql);
	        DAOUtils.setParameters(ps,
	                animal.getNumRegistro(), animal.getSexoId(),
	                fechaNac, fechaAlta, fechaBaja, animal.getGranjaId(),
	                animal.getPadreExternoId(), animal.getMadreExternaId(),
	                animal.getMadreInternaId(), animal.getId());

	        int updatedRows = ps.executeUpdate();
	        logger.info("Animal con id: {} actualizado, filas afectadas: {}", animal.getId(), updatedRows);
	        return updatedRows == 1;

	    } catch (Exception e) {
	        logger.error("Animal con id: {}, Message: {}", animal.getId(), e.getMessage(), e);
	        throw e;
	    } finally {
	        JDBCUtils.close(null, ps);
	    }
	}

	/**
	 * Eliminar un animal por su id.
	 * @param id
	 * @throws Exception
	 * @return true si se eliminó un animal, false si no se encontró el id
	 */
	public boolean delete(Connection c, Long id) throws Exception {

		PreparedStatement ps = null;
		try {
			String sql = "DELETE FROM animal WHERE id = ?";
			logger.info("Eliminando animal con id: {}", id);
			logger.debug("Executing SQL: {}", sql );
			ps = c.prepareStatement(sql );
			int i = 1;
			ps.setLong(i++, id);
			int deleted = ps.executeUpdate();	
			logger.info("Animal con id: {} eliminado, filas afectadas: {}", id, deleted);
			return deleted==1;
		} catch (Exception e) {
			logger.error("Error eliminando animal con id: {}, Message: {}", id, e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(null, ps);
		}
	}

	/**
	 * Encontrar animal por su id.
	 * @param id
	 * @throws Exception
	 * @return El animal encontrado.
	 */
	public AnimalDTO findById(Connection c, Long id) throws Exception {

		PreparedStatement ps = null;
		ResultSet rs = null;
		AnimalDTO animal = null;
		try {			
			String sql = BASE_QUERY + " WHERE a.id = ? ";
			logger.info("Buscando animal con id: {}", id);
			logger.debug("Executing SQL: {}", sql);
			ps = c.prepareStatement(sql);
			DAOUtils.setParameters(ps, id);				
			rs = ps.executeQuery();
			
			if (rs.next()) {
				animal= loadNext(rs);
				 logger.info("Animal encontrado: {}", animal);
			}
			return animal;				
		} catch (Exception e) {
			logger.error("Error encontrando animal con id: {}, Message: {}",id, e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(rs, ps);
		}
	}

	/**
	 * Encontrar animal por su num_registro.
	 * @param numRegistro
	 * @throws Exception
	 * @return El animal encontrado.
	 */
	public AnimalDTO findByNumRegistro(Connection c, String numRegistro) throws Exception {

		PreparedStatement ps = null;
		ResultSet rs = null;
		AnimalDTO animal = null; 

		try {			
			String sql = BASE_QUERY + " WHERE a.num_registro = ? ";
			logger.info("Buscando animal con numRegistro: {}", numRegistro);
			logger.debug("Executing SQL: {}", sql);
			
			ps = c.prepareStatement(sql);
			DAOUtils.setParameters(ps, numRegistro);				
			rs = ps.executeQuery();
			if (rs.next()) {
				animal = loadNext(rs);
				logger.info("Animal encontrado: {}", animal);
			}
			return animal;			
		} catch (Exception e) {
			logger.error("Error encontrando animal con numRegistro: {}, Message: {}", numRegistro, e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(rs, ps);
		}
	}

	/**
	 * Encontrar animales por sus atributos. Puede que alguno de los parametros sea null.
	 * @param criteria
	 * @throws Exception
	 * @return Resultados paginados de animales que cumplen los criterios de búsqueda.
	 */
	public Results<AnimalDTO> findByCriteria(Connection c, AnimalCriteria criteria, 
											int from, int pageSize) throws Exception {

	    Results<AnimalDTO> results = new Results<>();
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    logger.info("Criteria: {}", criteria);

	    try {
	        StringBuilder sqlBuilder = new StringBuilder(BASE_QUERY);
	        List<String> condiciones = new ArrayList<>();
	        List<Object> parametros = new ArrayList<>();

	        SQLUtils.addClause(criteria.getId(),
	                condiciones, " a.id = ? ", parametros, criteria.getId());
	        SQLUtils.addClause(criteria.getNumRegistro(),
	                condiciones, " UPPER(a.num_registro) LIKE UPPER(?) ",
	                parametros, "%" + criteria.getNumRegistro() + "%");
	        SQLUtils.addClause(criteria.getSexoDescripcion(),
	                condiciones, " UPPER(s.descripcion) LIKE UPPER(?) ",
	                parametros, "%" + criteria.getSexoDescripcion() + "%");
	        SQLUtils.addClause(criteria.getFechaNacimiento(),
	                condiciones, " a.fecha_nacimiento = ? ", parametros, criteria.getFechaNacimiento());
	        SQLUtils.addClause(criteria.getFechaAlta(),
	                condiciones, " a.fecha_alta = ? ", parametros, criteria.getFechaAlta());
	        SQLUtils.addClause(criteria.getFechaBaja(),
	                condiciones, " a.fecha_baja = ? ", parametros, criteria.getFechaBaja());
	        SQLUtils.addClause(criteria.getGranjaNif(),
	                condiciones, " UPPER(g.nif) LIKE UPPER(?) ",
	                parametros, "%" + criteria.getGranjaNif() + "%");
	        SQLUtils.addClause(criteria.getNumRegistroMadreInterna(),
	                condiciones, " UPPER(madre_interna.num_registro) LIKE UPPER(?) ",
	                parametros, "%" + criteria.getNumRegistroMadreInterna() + "%");

	        if (!condiciones.isEmpty()) {
	            sqlBuilder.append(" WHERE ");
	            sqlBuilder.append(String.join(" AND ", condiciones));
	        }

	        sqlBuilder.append(ORDER_BY);
	        String sql = sqlBuilder.toString();
	        logger.debug("Executing SQL: {}", sql);
	        logger.debug("Parameters: {}", parametros);
	        ps = c.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
	                ResultSet.CONCUR_READ_ONLY);
	        DAOUtils.setParameters(ps, parametros);
	        rs = ps.executeQuery();
	        List<AnimalDTO> animales = new ArrayList<>();

	        if (from >= 1 && rs.absolute(from)) {
	            int count = 0;
	            do {
	                animales.add(loadNext(rs));
	                ++count;
	            } while (count < pageSize && rs.next());
	        }
	        
	        int totalResults = SQLUtils.getTotalRows(rs);
	        results.setPageResults(animales);
	        results.setTotal(totalResults);
	        return results;
	    } catch (Exception e) {
	        logger.error("Criteria: {}, Message: {}", criteria, e.getMessage(), e);
	        throw e;
	    } finally {
	        JDBCUtils.close(rs, ps);
	    }
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


