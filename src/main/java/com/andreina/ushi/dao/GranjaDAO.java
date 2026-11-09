package com.andreina.ushi.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.andreina.ushi.dao.criteria.GranjaCriteria;
import com.andreina.ushi.model.Granja;
import com.andreina.ushi.model.GranjaDTO;
import com.andreina.ushi.utils.DAOUtils;
import com.andreina.ushi.utils.JDBCUtils;
import com.andreina.ushi.utils.SQLUtils;
public class GranjaDAO {

    private static final Logger logger = LogManager.getLogger(GranjaDAO.class.getName());

    private static final String BASE_QUERY =
            "SELECT g.id, g.codigo_rega, g.nif, g.calle, g.numero, g.CP, g.usuario_id, "
            + "u.nombre AS usuario_nombre "
            + "FROM granja g "
            + "LEFT JOIN usuario u ON u.id = g.usuario_id";
    private static final String ORDER_BY= " ORDER BY g.id DESC";

    public GranjaDAO() {
    }

    /**
	 * Crea un nuevo registro de granja en la base de datos.
	 * @param c Conexión a la base de datos.
	 * @param granja Objeto GranjaDTO con los datos de la granja a
	 * return GranjaDTO con el id generado.
	 * @throws Exception si ocurre un error al acceder o procesar datos.
	 */
    public Granja create(Connection c, Granja granja) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "INSERT INTO granja (codigo_rega, nif, calle, numero, CP, usuario_id) "
            		+ "VALUES (?, ?, ?, ?, ?, ?)";
            logger.debug("Executing SQL: {}", sql);
            ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            DAOUtils.setParameters(ps,granja.getCodigoRega(), granja.getNif(), granja.getCalle(), granja.getNumero(), granja.getCp(),
            		granja.getUsuarioId());
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    granja.setId(rs.getLong(1));
                    logger.info("Granja creada con id: {}", granja.getId());
                }
            }
            return granja;
        } catch (Exception e) {
            logger.error("Error creando granja con nif: {}, Message: {}", granja.getNif(), e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(rs, ps);
        }
    }

    /**
     * Actualiza un registro de granja existente en la base de datos.
     * @param c
     * @param granja
     * @return
     * @throws Exception
     */
    public GranjaDTO update(Connection c, GranjaDTO granja) throws Exception {
        PreparedStatement ps = null;
        try {
            String sql = "UPDATE granja SET cogigo_rega = ?, nif = ?, calle = ?, numero= ?, CP = ?, "
            		+ "usuario_id = ? WHERE id = ?";
            logger.info("Actualizando granja con id: {}", granja.getId());
            logger.debug("Executing SQL: {}", sql);
            ps = c.prepareStatement(sql);
            DAOUtils.setParameters(ps, granja.getCodigoRega(), granja.getNif(), granja.getCalle(),
					            		granja.getNumero(),granja.getCp(), 
					            		granja.getUsuarioId(), granja.getId());
            int updatedRows = ps.executeUpdate();
            logger.info("Granja con id: {} actualizada, filas afectadas: {}", granja.getId(), updatedRows);
            return updatedRows == 1 ? granja : null;
        } catch (Exception e) {
            logger.error("Error actualizando granja con id: {}, Message: {}", granja.getId(), e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(null, ps);
        }
    }

   /**
	 * Elimina un registro de granja de la base de datos.
	 * @param c
	 * @param id
	 * @return
	 * @throws Exception
	 */
    public boolean delete(Connection c, Long id) throws Exception {
        PreparedStatement ps = null;
        try {
        	String sql = "DELETE FROM granja WHERE id = ?";
            logger.info("Eliminando granja con id: {}", id);
            logger.debug("Executing SQL: {}", sql);
            ps = c.prepareStatement(sql);
            DAOUtils.setParameters(ps, id);
            int deleted = ps.executeUpdate();
            logger.info("Granja con id: {} eliminada, filas afectadas: {}", id, deleted);
            return deleted == 1;
        } catch (Exception e) {
            logger.error("Error eliminando granja con id: {}, Message: {}", id, e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(null, ps);
        }
    }

    /**
     * Busca un registro de granja por su id en la base de datos.
     * @param c
     * @param id
     * @return
     * @throws Exception
     */
    public GranjaDTO findById(Connection c, Long id) throws Exception {
    	PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = BASE_QUERY + " WHERE g.id = ?";
            logger.info("Buscando granja con id: {}", id);
            logger.debug("Executing SQL: {}", sql);
            ps = c.prepareStatement(sql);
            DAOUtils.setParameters(ps, id);
            rs = ps.executeQuery();
            return rs.next() ? loadNext(rs) : null;
        } catch (Exception e) {
            logger.error("Error encontrando granja con id: {}, Message: {}", id, e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(rs, ps);
        }
    }

    /**
     * Ejecuta la operacion DAO correspondiente.
     * @throws Exception si ocurre un error al acceder o procesar datos.
     */
    public GranjaDTO findByNif(Connection c, String nif) throws Exception {
    	PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = BASE_QUERY + " WHERE g.nif = ?";
            logger.info("Buscando granja con nif: {}", nif);
            logger.debug("Executing SQL: {}", sql);
            ps = c.prepareStatement(sql);
            DAOUtils.setParameters(ps, nif);
            rs = ps.executeQuery();
            return rs.next() ? loadNext(rs) : null;
        } catch (Exception e) {
            logger.error("Error encontrando granja con nif: {}, Message: {}", nif, e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(rs, ps);
        }
    }

    /**
     * Ejecuta la operacion DAO correspondiente.
     * @param c
     * @param usuarioId
     * @return
     * @throws Exception
     */
    public List<GranjaDTO> findByEncargadoId(Connection c, Long usuarioId) throws Exception {
    	PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = BASE_QUERY
                    + " WHERE g.usuario_id = ? "
                    + " OR EXISTS (SELECT 1 FROM usuario_granja ug "
                    + "WHERE ug.granja_id = g.id AND ug.usuario_id = ?) "
                    + ORDER_BY;
            logger.info("Buscando granjas asignadas al usuario_id: {}", usuarioId);
            logger.debug("Executing SQL: {}", sql);
            ps = c.prepareStatement(sql);
            DAOUtils.setParameters(ps, usuarioId, usuarioId);
            rs = ps.executeQuery();
            List<GranjaDTO> granjas = new ArrayList<>();
            while (rs.next()) {
               granjas.add(loadNext(rs));
            }
            return granjas;
        } catch (Exception e) {
            logger.error("Error encontrando granjas del usuario_id: {}, Message: {}", usuarioId, e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(rs, ps);
        }
    }

    /**
     * Ejecuta la operacion DAO correspondiente.
     * @throws Exception si ocurre un error al acceder o procesar datos.
     */
    public List<GranjaDTO> findAll(Connection c) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = BASE_QUERY + ORDER_BY;
            logger.debug("Executing SQL: {}", sql);
            ps = c.prepareStatement(sql);
            rs = ps.executeQuery();
            List<GranjaDTO> granjas = new ArrayList<>();
            while (rs.next()) {
                granjas.add(loadNext(rs));
            }
            logger.info("Encontradas {} granjas", granjas.size());
            return granjas;
        } catch (Exception e) {
        	logger.error("Error listando todas las granjas: {}", e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(rs, ps);
        }
    }

    public List<GranjaDTO> findByCriteria(Connection c, GranjaCriteria criteria) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            StringBuilder sqlBuilder = new StringBuilder(BASE_QUERY);
            List<String> condiciones = new ArrayList<String>();
            List<Object> parametros = new ArrayList<Object>();

            SQLUtils.addClause(criteria.getId(), condiciones, " g.id = ? ",
                    parametros, criteria.getId());
            SQLUtils.addClause(criteria.getCodigoRega(), condiciones, " UPPER(g.codigo_rega) LIKE UPPER(?)",
                    parametros, criteria.getCodigoRega());
            SQLUtils.addClause(criteria.getNif(), condiciones, " UPPER(g.nif) LIKE UPPER(?) ",
                    parametros, "%" + criteria.getNif() + "%");
            SQLUtils.addClause(criteria.getCalle(), condiciones, " UPPER(g.calle) LIKE UPPER(?) ",
                    parametros, "%" + criteria.getCalle() + "%");
            SQLUtils.addClause(criteria.getNumero(), condiciones, " g.numero = ? ",
                    parametros, criteria.getNumero());
            SQLUtils.addClause(criteria.getCp(), condiciones, " g.CP = ? ",
                    parametros, criteria.getCp());
            SQLUtils.addClause(criteria.getEncargado(), condiciones,
                    " (UPPER(u.nombre) LIKE UPPER(?) OR UPPER(u.apellido1) LIKE UPPER(?) OR UPPER(u.apellido2) LIKE UPPER(?)) ",
                    parametros, "%" + criteria.getEncargado() + "%");
            if (criteria.getEncargado() != null) {
                parametros.add("%" + criteria.getEncargado() + "%");
                parametros.add("%" + criteria.getEncargado() + "%");
            }
            SQLUtils.addClause(criteria.getLocalidad(), condiciones,
                    " EXISTS (SELECT 1 FROM localidad l WHERE l.granja_id = g.id AND UPPER(l.nombre) LIKE UPPER(?)) ",
                    parametros, "%" + criteria.getLocalidad() + "%");
            SQLUtils.addClause(criteria.getProvincia(), condiciones,
                    " EXISTS (SELECT 1 FROM localidad l INNER JOIN provincia p ON p.id = l.provincia_id "
                    + "WHERE l.granja_id = g.id AND UPPER(p.nombre) LIKE UPPER(?)) ",
                    parametros, "%" + criteria.getProvincia() + "%");

            if (!condiciones.isEmpty()) {
                sqlBuilder.append(" WHERE ").append(String.join(" AND ", condiciones));
            }
            sqlBuilder.append(ORDER_BY);

            String sql = sqlBuilder.toString();
            logger.debug("Executing SQL: {}", sql);
            logger.debug("Parameters: {}", parametros);
            ps = c.prepareStatement(sql);
            DAOUtils.setParameters(ps, parametros);
            rs = ps.executeQuery();
            List<GranjaDTO> granjas = new ArrayList<GranjaDTO>();
            while (rs.next()) {
                granjas.add(loadNext(rs));
            }
            return granjas;
        } catch (Exception e) {
            logger.error("Criteria: {}, Message: {}", criteria, e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(rs, ps);
        }
    }

    private GranjaDTO loadNext(ResultSet rs) throws Exception {
        int i = 1;
        GranjaDTO granja = new GranjaDTO();
        granja.setId(rs.getLong(i++));
        granja.setCodigoRega(rs.getString(i++));
        granja.setNif(rs.getString(i++));
        granja.setCalle(rs.getString(i++));
        granja.setNumero(rs.getInt(i++));
        granja.setCp(rs.getLong(i++));
        granja.setUsuarioId(rs.getLong(i++));
        granja.setUsuarioNombre(rs.getString(i++));
        return granja;
    }
}
