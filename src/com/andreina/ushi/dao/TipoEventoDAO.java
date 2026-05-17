package com.andreina.ushi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.andreina.ushi.model.TipoEvento;
import com.andreina.ushi.utils.DAOUtils;
import com.andreina.ushi.utils.JDBCUtils;

public class TipoEventoDAO {

    private static final Logger logger = LogManager.getLogger(TipoEventoDAO.class.getName());
    private static final String BASE_QUERY = "SELECT te.id, te.nombre FROM tipo_evento te";
    private static final String ORDER_BY = " ORDER BY te.nombre DESC";
    
    public TipoEventoDAO() {
    }

    /**
     * Crea un nuevo tipo de evento.
     * @param c conexion activa con la base de datos.
     * @param tipoEvento datos del tipo de evento a crear.
     * @return tipo de evento creado con el id generado.
     * @throws Exception si ocurre un error al crear el tipo de evento.
     */
    public TipoEvento create(Connection c, TipoEvento tipoEvento) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "INSERT INTO tipo_evento (nombre) VALUES (?)";
            logger.debug("Executing SQL: {}", sql);
            ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            DAOUtils.setParameters(ps, tipoEvento.getNombre());
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    tipoEvento.setId(rs.getLong(1));
                    logger.info("TipoEvento creado con id: {}", tipoEvento.getId());
                }
            }
            return tipoEvento;
        } catch (Exception e) {
            logger.error("Error creando tipoEvento con nombre: {}, Message: {}", tipoEvento.getNombre(), e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(rs, ps);
        }
    }

    /**
     * Actualiza un tipo de evento existente.
     * @param c conexion activa con la base de datos.
     * @param tipoEvento datos del tipo de evento a actualizar; debe incluir el id.
     * @return tipo de evento actualizado si se modifico una fila, o null si no se encontro.
     * @throws Exception si ocurre un error al actualizar el tipo de evento.
     */
    public boolean update(Connection c, TipoEvento tipoEvento) throws Exception {
        PreparedStatement ps = null;
        try {
            String sql = "UPDATE tipo_evento SET nombre = ? WHERE id = ?";
            logger.info("Actualizando tipoEvento con id: {}", tipoEvento.getId());
            logger.debug("Executing SQL: {}", sql);
            ps = c.prepareStatement(sql);
            DAOUtils.setParameters(ps, tipoEvento.getNombre(), tipoEvento.getId());
            int updatedRows = ps.executeUpdate();
            logger.info("TipoEvento con id: {} actualizado, filas afectadas: {}", tipoEvento.getId(), updatedRows);
            return updatedRows == 1 ;
        } catch (Exception e) {
            logger.error("Error actualizando tipoEvento con id: {}, Message: {}", tipoEvento.getId(), e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(null, ps);
        }
    }

    /**
     * Elimina un tipo de evento por su id.
     * @param c conexion activa con la base de datos.
     * @param id identificador del tipo de evento a eliminar.
     * @return true si se elimino una fila, false si no se encontro el tipo de evento.
     * @throws Exception si ocurre un error al eliminar el tipo de evento.
     */
    public boolean delete(Connection c, Long id) throws Exception {
        PreparedStatement ps = null;
        try {
            String sql = "DELETE FROM tipo_evento WHERE id = ?";
            logger.info("Eliminando tipoEvento con id: {}", id);
            logger.debug("Executing SQL: {}", sql);
            ps = c.prepareStatement(sql);
            DAOUtils.setParameters(ps, id);
            int deleted = ps.executeUpdate();
            logger.info("TipoEvento con id: {} eliminado, filas afectadas: {}", id, deleted);
            return deleted == 1;
        } catch (Exception e) {
            logger.error("Error eliminando tipoEvento con id: {}, Message: {}", id, e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(null, ps);
        }
    }

    /**
     * Busca un tipo de evento por su nombre.
     * @param c conexion activa con la base de datos.
     * @param nombre nombre del tipo de evento a buscar.
     * @return tipo de evento encontrado, o null si no existe.
     * @throws Exception si ocurre un error al buscar el tipo de evento.
     */
    public TipoEvento findByNombre(Connection c, String nombre) throws Exception {
        return findOne(c, BASE_QUERY + " WHERE te.nombre = ?", nombre, "nombre", nombre);
    }

    /**
     * Busca un tipo de evento por su id.
     * @param c conexion activa con la base de datos.
     * @param id identificador del tipo de evento a buscar.
     * @return tipo de evento encontrado, o null si no existe.
     * @throws Exception si ocurre un error al buscar el tipo de evento.
     */
    public TipoEvento findById(Connection c, Long id) throws Exception {
        return findOne(c, BASE_QUERY + " WHERE te.id = ?", id, "id", id);
    }

    /**
     * Ejecuta una consulta que debe devolver como maximo un tipo de evento.
     * @param c conexion activa con la base de datos.
     * @param sql consulta SQL a ejecutar.
     * @param value parametro de busqueda de la consulta.
     * @param field nombre del campo usado para logging.
     * @param logValue valor usado para logging.
     * @return tipo de evento encontrado, o null si no existe.
     * @throws Exception si ocurre un error al ejecutar la consulta.
     */
    private TipoEvento findOne(Connection c, String sql, Object value, String field, Object logValue) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            logger.info("Buscando tipoEvento por {}: {}", field, logValue);
            logger.debug("Executing SQL: {}", sql);
            ps = c.prepareStatement(sql);
            DAOUtils.setParameters(ps, value);
            rs = ps.executeQuery();
            return rs.next() ? loadNext(rs) : null;
        } catch (Exception e) {
            logger.error("Error buscando tipoEvento por {}: {}, Message: {}", field, logValue, e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(rs, ps);
        }
    }
    private TipoEvento loadNext(ResultSet rs) throws Exception {
        int i = 1;
        TipoEvento tipoEvento = new TipoEvento();
        tipoEvento.setId(rs.getLong(i++));
        tipoEvento.setNombre(rs.getString(i++));
        return tipoEvento;
    }
}