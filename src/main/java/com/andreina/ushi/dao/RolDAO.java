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

import com.andreina.ushi.model.Rol;
import com.andreina.ushi.utils.DAOUtils;
import com.andreina.ushi.utils.JDBCUtils;

public class RolDAO {

    private static final Logger logger = LogManager.getLogger(RolDAO.class.getName());
    private static final String BASE_QUERY = "SELECT r.id, r.nombre FROM rol r";
    private static final String ORDER_BY= " ORDER BY r.id DESC";

    public RolDAO() {
    }

    /**
     * Lista todos los roles.
     * @param c conexion activa con la base de datos.
     * @return lista de roles ordenados por id.
     * @throws Exception si ocurre un error al listar los roles.
     */
    public List<Rol> findAll(Connection c) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = BASE_QUERY + ORDER_BY;
            logger.debug("Executing SQL: {}", sql);
            ps = c.prepareStatement(sql);
            rs = ps.executeQuery();
            List<Rol> roles = new ArrayList<>();
            while (rs.next()) {
                roles.add(loadNext(rs));
            }
            return roles;
        } catch (Exception e) {
            logger.error("Error listando roles, Message: {}", e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(rs, ps);
        }
    }

    /**
     * Crea un nuevo rol.
     * @param c conexion activa con la base de datos.
     * @param rol datos del rol a crear.
     * @return rol creado con el id generado.
     * @throws Exception si ocurre un error al crear el rol.
     */
    public Rol create(Connection c, Rol rol) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "INSERT INTO rol (nombre) VALUES (?)";
            logger.debug("Executing SQL: {}", sql);
            ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            DAOUtils.setParameters(ps, rol.getNombre());
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No se insertó ningún registro en la tabla rol.");
            }
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                rol.setId(rs.getLong(1));
                logger.info("Rol creado con id: {}", rol.getId());
            }
            return rol;
        } catch (Exception e) {
            logger.error("Error creando rol con nombre: {}, Message: {}", rol.getNombre(), e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(rs, ps);
        }
    }

    /**
     * Actualiza un rol existente.
     * @param c conexion activa con la base de datos.
     * @param rol datos del rol a actualizar; debe incluir el id.
     * @return rol actualizado si se modifico una fila, o null si no se encontro.
     * @throws Exception si ocurre un error al actualizar el rol.
     */
    public Rol update(Connection c, Rol rol) throws Exception {
        PreparedStatement ps = null;
        try {
            String sql = "UPDATE rol SET nombre = ? WHERE id = ?";
            logger.info("Actualizando rol con id: {}", rol.getId());
            logger.debug("Executing SQL: {}", sql);
            ps = c.prepareStatement(sql);
            DAOUtils.setParameters(ps, rol.getNombre(), rol.getId());
            int updatedRows = ps.executeUpdate();
            logger.info("Rol con id: {} actualizado, filas afectadas: {}", rol.getId(), updatedRows);
            return updatedRows == 1 ? rol : null;
        } catch (Exception e) {
            logger.error("Error actualizando rol con id: {}, Message: {}", rol.getId(), e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(null, ps);
        }
    }

    /**
     * Elimina un rol por su id.
     * @param c conexion activa con la base de datos.
     * @param id identificador del rol a eliminar.
     * @return true si se elimino una fila, false si no se encontro el rol.
     * @throws Exception si ocurre un error al eliminar el rol.
     */
    public boolean delete(Connection c, Long id) throws Exception {
        PreparedStatement ps = null;
        try {
            String sql = "DELETE FROM rol WHERE id = ?";
            logger.info("Eliminando rol con id: {}", id);
            logger.debug("Executing SQL: {}", sql);
            ps = c.prepareStatement(sql);
            DAOUtils.setParameters(ps, id);
            int deleted = ps.executeUpdate();
            logger.info("Rol con id: {} eliminado, filas afectadas: {}", id, deleted);
            return deleted == 1;
        } catch (Exception e) {
            logger.error("Error eliminando rol con id: {}, Message: {}", id, e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(null, ps);
        }
    }

    
    private Rol loadNext(ResultSet rs) throws Exception {
        int i = 1;
        Rol rol = new Rol();
        rol.setId(rs.getLong(i++));
        rol.setNombre(rs.getString(i++));
        return rol;
    }
}