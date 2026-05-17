package com.andreina.ushi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.andreina.ushi.model.Semilla;
import com.andreina.ushi.utils.DAOUtils;
import com.andreina.ushi.utils.JDBCUtils;

public class SemillaDAO {

    private static final Logger logger = LogManager.getLogger(SemillaDAO.class.getName());
    private static final String BASE_QUERY = "SELECT s.id, s.itp, s.merito_neto FROM semilla s";
    private static final String ORDER_BY = " ORDER BY s.id DESC";
    
    public SemillaDAO() {
    }

    /**
     * Crea una nueva semilla.
     * @param c conexion activa con la base de datos.
     * @param semilla datos de la semilla a crear.
     * @return semilla creada con el id generado.
     * @throws Exception si ocurre un error al crear la semilla.
     */
    public Semilla create(Connection c, Semilla semilla) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "INSERT INTO semilla (itp, merito_neto) VALUES (?, ?)";
            logger.debug("Executing SQL: {}", sql);
            ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            DAOUtils.setParameters(ps, semilla.getItp(), semilla.getMeritoNeto());
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    semilla.setId(rs.getLong(1));
                    logger.info("Semilla creada con id: {}", semilla.getId());
                }
            }
            return semilla;
        } catch (Exception e) {
            logger.error("Error creando semilla, Message: {}", e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(rs, ps);
        }
    }

    /**
     * Actualiza una semilla existente.
     * @param c conexion activa con la base de datos.
     * @param semilla datos de la semilla a actualizar; debe incluir el id.
     * @return semilla actualizada si se modifico una fila, o null si no se encontro.
     * @throws Exception si ocurre un error al actualizar la semilla.
     */
    public boolean update(Connection c, Semilla semilla) throws Exception {
        PreparedStatement ps = null;
        try {
            String sql = "UPDATE semilla SET itp = ?, merito_neto = ? WHERE id = ?";
            logger.info("Actualizando semilla con id: {}", semilla.getId());
            logger.debug("Executing SQL: {}", sql);
            ps = c.prepareStatement(sql);
            DAOUtils.setParameters(ps, semilla.getItp(), semilla.getMeritoNeto(), semilla.getId());
            int updatedRows = ps.executeUpdate();
            logger.info("Semilla con id: {} actualizada, filas afectadas: {}", semilla.getId(), updatedRows);
            return updatedRows == 1;
        } catch (Exception e) {
            logger.error("Error actualizando semilla con id: {}", semilla.getId(), e);
            throw e;
        } finally {
            JDBCUtils.close(null, ps);
        }
    }

    /**
     * Elimina una semilla por su id.
     * @param c conexion activa con la base de datos.
     * @param id identificador de la semilla a eliminar.
     * @return true si se elimino una fila, false si no se encontro la semilla.
     * @throws Exception si ocurre un error al eliminar la semilla.
     */
    public boolean delete(Connection c, Long id) throws Exception {
        PreparedStatement ps = null;
        try {
            String sql = "DELETE FROM semilla WHERE id = ?";
            logger.info("Eliminando semilla con id: {}", id);
            logger.debug("Executing SQL: {}", sql);
            ps = c.prepareStatement(sql);
            DAOUtils.setParameters(ps, id);
            int deleted = ps.executeUpdate();
            logger.info("Semilla con id: {} eliminada, filas afectadas: {}", id, deleted);
            return deleted == 1;
        } catch (Exception e) {
            logger.error("Error eliminando semilla con id: {}, Message: {}", id, e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(null, ps);
        }
    }

    /**
     * Busca semillas con merito neto superior al minimo indicado.
     * @param c conexion activa con la base de datos.
     * @param minimo valor minimo de merito neto excluyente.
     * @return lista de semillas encontradas ordenadas por merito neto ascendente.
     * @throws Exception si ocurre un error al buscar semillas.
     */
    public List<Semilla> findByMeritoNetoGreaterThan(Connection c, Integer minimo) throws Exception {
        return findList(c, BASE_QUERY + " WHERE s.merito_neto > ? ORDER BY s.merito_neto ASC", minimo);
    }

    /**
     * Lista las semillas con mayor ITP.
     * @param c conexion activa con la base de datos.
     * @param limite numero maximo de semillas a devolver.
     * @return lista de semillas ordenadas por ITP descendente.
     * @throws Exception si ocurre un error al buscar semillas.
     */
    public List<Semilla> findTopByItp(Connection c, Integer limite) throws Exception {
        return findList(c, BASE_QUERY + " ORDER BY s.itp DESC LIMIT ?", limite);
    }

    /**
     * Ejecuta una consulta de semillas y carga sus resultados.
     * @param c conexion activa con la base de datos.
     * @param sql consulta SQL a ejecutar.
     * @param params parametros de la consulta en orden.
     * @return lista de semillas obtenidas por la consulta.
     * @throws Exception si ocurre un error al ejecutar la consulta.
     */
    private List<Semilla> findList(Connection c, String sql, Object... params) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            logger.debug("Executing SQL: {}", sql);
            ps = c.prepareStatement(sql);
            DAOUtils.setParameters(ps, params);
            rs = ps.executeQuery();
            List<Semilla> semillas = new ArrayList<>();
            while (rs.next()) {
                semillas.add(loadNext(rs));
            }
            return semillas;
        } catch (Exception e) {
            logger.error("Error listando semillas, Message: {}", e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(rs, ps);
        }
    }

    private Semilla loadNext(ResultSet rs) throws Exception {
        int i = 1;
        Semilla semilla = new Semilla();
        semilla.setId(rs.getLong(i++));
        semilla.setItp(rs.getInt(i++));
        semilla.setMeritoNeto(rs.getInt(i++));
        return semilla;
    }
}