package com.andreina.ushi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.andreina.ushi.dao.criteria.ParametroCriteria;
import com.andreina.ushi.model.ParametroDTO;
import com.andreina.ushi.utils.DAOUtils;
import com.andreina.ushi.utils.JDBCUtils;
import com.andreina.ushi.utils.SQLUtils;

public class ParametroDAO {

    private static final Logger logger = LogManager.getLogger(ParametroDAO.class.getName());

    private static final String BASE_QUERY =
            "SELECT p.id, p.fecha_hora, p.valor_parametro, p.tag_id, p.animal_id, "
            + "p.tipo_parametro_id, tp.nombre AS tipo_parametro_nombre "
            + "FROM parametro p "
            + "INNER JOIN tipo_parametro tp ON tp.id = p.tipo_parametro_id";

    private static final String ORDER_BY  = " ORDER BY p.fecha_hora DESC, p.id DESC";
    private static final String PAGINATION = " LIMIT ? OFFSET ?";

    /**
     * Crea un nuevo parametro.
     *
     * @param c         conexion activa con la base de datos.
     * @param parametro datos del parametro a crear.
     * @return parametro creado con el id generado.
     * @throws Exception si ocurre un error al crear el parametro.
     */
    public ParametroDTO create(Connection c, ParametroDTO parametro) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "INSERT INTO parametro (fecha_hora, valor_parametro, "
	            		+ "tag_id, animal_id, tipo_parametro_id) "
	                    + "VALUES (?, ?, ?, ?, ?)";
            logger.debug("Executing SQL: {}", sql);
            ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            DAOUtils.setParameters(ps,DAOUtils.toTimestamp(parametro.getFechaHora()),
                    parametro.getValorParametro(),parametro.getTagId(),
                    parametro.getAnimalId(),parametro.getTipoParametroId());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No se insertó ningún registro en la tabla parametro.");
            }
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                parametro.setId(rs.getLong(1));
                logger.info("Parametro creado con id: {}", parametro.getId());
            }
            return parametro;

        } catch (Exception e) {
            logger.error("Error creando parametro para animal_id: {}, Message: {}",
                    parametro.getAnimalId(), e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(rs, ps);
        }
    }

   
    /**
     * Busca parametros que cumplen los criterios indicados.
     *
     * @param c        conexion activa con la base de datos.
     * @param criteria criterios de busqueda.
     * @param from     posicion inicial de la pagina, empezando en 1.
     * @param pageSize numero maximo de resultados a devolver.
     * @return lista de parametros encontrados.
     * @throws Exception si ocurre un error al buscar parametros.
     */
    public List<ParametroDTO> findByCriteria(Connection c, ParametroCriteria criteria,
                                              int from, int pageSize) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            StringBuilder sqlBuilder = new StringBuilder(BASE_QUERY);
            List<String> condiciones = new ArrayList<>();
            List<Object> parametros  = new ArrayList<>();
            SQLUtils.addClause(criteria.getId(),condiciones, " p.id = ? ",
            		parametros, criteria.getId());
            SQLUtils.addClause(criteria.getTagId(),condiciones, " p.tag_id = ? ",              
            		parametros, criteria.getTagId());
            SQLUtils.addClause(criteria.getAnimalId(),condiciones, " p.animal_id = ? ",           
            		parametros, criteria.getAnimalId());
            SQLUtils.addClause(criteria.getTipoParametroId(),condiciones, " p.tipo_parametro_id = ? ",  
            		parametros, criteria.getTipoParametroId());
            SQLUtils.addClause(criteria.getFechaDesde(),condiciones, " p.fecha_hora >= ? ",         
            		parametros, DAOUtils.toTimestamp(criteria.getFechaDesde()));
            SQLUtils.addClause(criteria.getFechaHasta(), condiciones, " p.fecha_hora <= ? ",         
            		parametros, DAOUtils.toTimestamp(criteria.getFechaHasta()));
            SQLUtils.addClause(criteria.getValorMin(),condiciones, " p.valor_parametro >= ? ",    
            		parametros, criteria.getValorMin());
            SQLUtils.addClause(criteria.getValorMax(), condiciones, " p.valor_parametro <= ? ",    
            		parametros, criteria.getValorMax());
            if (!condiciones.isEmpty()) {
                sqlBuilder.append(" WHERE ").append(String.join(" AND ", condiciones));
            }
            sqlBuilder.append(ORDER_BY);
            sqlBuilder.append(PAGINATION);
            parametros.add(pageSize);
            parametros.add(from > 0 ? from - 1 : 0);

            String sql = sqlBuilder.toString();
            logger.debug("Executing SQL: {}", sql);
            logger.debug("Parameters: {}", parametros);

            ps = c.prepareStatement(sql);
            DAOUtils.setParameters(ps, parametros);
            rs = ps.executeQuery();

            List<ParametroDTO> lista = new ArrayList<>();
            while (rs.next()) {
                lista.add(loadNext(rs));
            }
            return lista;

        } catch (Exception e) {
            logger.error("Criteria: {}, Message: {}", criteria, e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(rs, ps);
        }
    }

    private ParametroDTO loadNext(ResultSet rs) throws Exception {
        int i = 1;
        ParametroDTO parametro = new ParametroDTO();
        parametro.setId(rs.getLong(i++));
        parametro.setFechaHora(rs.getTimestamp(i++));
        parametro.setValorParametro(rs.getDouble(i++));
        parametro.setTagId(DAOUtils.getLong(rs, i++));
        parametro.setAnimalId(DAOUtils.getLong(rs, i++));
        parametro.setTipoParametroId(rs.getLong(i++));
        parametro.setTipoParametroNombre(rs.getString(i++));
        return parametro;
    }
}