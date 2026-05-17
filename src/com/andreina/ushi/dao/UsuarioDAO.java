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

import com.andreina.ushi.dao.criteria.UsuarioCriteria;
import com.andreina.ushi.model.UsuarioDTO;
import com.andreina.ushi.utils.DAOUtils;
import com.andreina.ushi.utils.JDBCUtils;
import com.andreina.ushi.utils.SQLUtils;

public class UsuarioDAO {

    private static final Logger logger = LogManager.getLogger(UsuarioDAO.class.getName());

    private static final String BASE_QUERY =
            "SELECT u.id, u.dni_nie, u.nombre, u.apellido1, u.apellido2, "
            + "u.telefono, u.email, u.password, r.id AS rol_id, r.nombre AS rol_nombre "
            + "FROM usuario u "
            + "INNER JOIN rol r ON u.rol_id = r.id ";
    private static final String ORDER_BY = " ORDER BY u.id DESC";
    private static final String PAGINATION = " LIMIT ? OFFSET ?";
   

    public UsuarioDAO() {
    }

    /**
     * Crea un nuevo usuario.
     * @param c conexion activa con la base de datos.
     * @param usuario datos del usuario a crear.
     * @return usuario creado con el id generado.
     * @throws Exception si ocurre un error al crear el usuario.
     */
    public UsuarioDTO create(Connection c, UsuarioDTO usuario) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "INSERT INTO usuario (dni_nie, nombre, apellido1, apellido2, "
            		+ "telefono, email, password, rol_id) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            logger.debug("Executing SQL: {}", sql);
            ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            DAOUtils.setParameters(ps, usuario.getDniNie(), usuario.getNombre(), usuario.getApellido1(),
					                    usuario.getApellido2(), usuario.getTelefono(), usuario.getEmail(), 
					                    usuario.getPassword(), usuario.getRolId());
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No se insertó ningún registro en la tabla usuario.");
            }
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                usuario.setId(rs.getLong(1));
                logger.info("Usuario creado con id: {}", usuario.getId());
            }
            return usuario;
        } catch (Exception e) {
            logger.error("Error creando usuario con email: {}, Message: {}", usuario.getEmail(), e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(rs, ps);
        }
    }

    /**
     * Actualiza un usuario existente.
     * @param c conexion activa con la base de datos.
     * @param usuario datos del usuario a actualizar; debe incluir el id.
     * @return usuario actualizado si se modifico una fila, o null si no se encontro.
     * @throws Exception si ocurre un error al actualizar el usuario.
     */
    public boolean update(Connection c, UsuarioDTO usuario) throws Exception {
        PreparedStatement ps = null;
        try {
            String sql = "UPDATE usuario SET dni_nie = ?, nombre = ?, apellido1 = ?, apellido2 = ?, "
                    + "telefono = ?, email = ?, password = ?, rol_id = ? WHERE id = ?";
            logger.info("Actualizando usuario con id: {}", usuario.getId());
            logger.debug("Executing SQL: {}", sql);
            ps = c.prepareStatement(sql);
            DAOUtils.setParameters(ps, usuario.getDniNie(), usuario.getNombre(), usuario.getApellido1(),
					                    usuario.getApellido2(), usuario.getTelefono(), 
					                    usuario.getEmail(), usuario.getPassword(),
					                    usuario.getRolId(), usuario.getId());
            int updatedRows = ps.executeUpdate();
            logger.info("Usuario con id: {} actualizado, filas afectadas: {}", usuario.getId(), updatedRows);
            return updatedRows == 1;
        } catch (Exception e) {
            logger.error("Error actualizando usuario con id: {}, Message: {}", usuario.getId(), e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(null, ps);
        }
    }

    /**
     * Elimina un usuario por su id.
     * @param c conexion activa con la base de datos.
     * @param id identificador del usuario a eliminar.
     * @return true si se elimino una fila, false si no se encontro el usuario.
     * @throws Exception si ocurre un error al eliminar el usuario.
     */
    public boolean delete(Connection c, Long id) throws Exception {
        PreparedStatement ps = null;
        try {
            String sql = "DELETE FROM usuario WHERE id = ?";
            logger.info("Eliminando usuario con id: {}", id);
            logger.debug("Executing SQL: {}", sql);
            ps = c.prepareStatement(sql);
            DAOUtils.setParameters(ps, id);
            int deleted = ps.executeUpdate();
            logger.info("Usuario con id: {} eliminado, filas afectadas: {}", id, deleted);
            return deleted == 1;
        } catch (Exception e) {
            logger.error("Error eliminando usuario con id: {}, Message: {}", id, e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(null, ps);
        }
    }

    /**
     * Busca un usuario por su id.
     * @param c conexion activa con la base de datos.
     * @param id identificador del usuario a buscar.
     * @return usuario encontrado, o null si no existe.
     * @throws Exception si ocurre un error al buscar el usuario.
     */
    public UsuarioDTO findById(Connection c, Long id) throws Exception {
        return findOne(c, BASE_QUERY + " WHERE u.id = ?", id, "id", id);
    }

    /**
     * Busca un usuario por su email.
     * @param c conexion activa con la base de datos.
     * @param email email del usuario a buscar.
     * @return usuario encontrado, o null si no existe.
     * @throws Exception si ocurre un error al buscar el usuario.
     */
    public UsuarioDTO findByEmail(Connection c, String email) throws Exception {
        return findOne(c, BASE_QUERY + " WHERE u.email = ?", email, "email", email);
    }

    /**
     * Busca usuarios que cumplen los criterios indicados.
     * @param c conexion activa con la base de datos.
     * @param criteria criterios de busqueda.
     * @param from posicion inicial de la pagina, empezando en 1.
     * @param pageSize numero maximo de resultados a devolver.
     * @return lista de usuarios encontrados.
     * @throws Exception si ocurre un error al buscar usuarios.
     */
    public List<UsuarioDTO> findByCriteria(Connection c, UsuarioCriteria criteria, int from, int pageSize) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            StringBuilder sqlBuilder = new StringBuilder(BASE_QUERY);
            List<String> condiciones = new ArrayList<>();
            List<Object> parametros = new ArrayList<>();
            SQLUtils.addClause(criteria.getId(), condiciones, " u.id = ? ",
                    parametros, criteria.getId());
            SQLUtils.addClause(criteria.getDniNie(), condiciones, " UPPER(u.dni_nie) LIKE UPPER(?) ",
                    parametros, "%" + criteria.getDniNie() + "%");
            SQLUtils.addClause(criteria.getNombre(), condiciones, " UPPER(u.nombre) LIKE UPPER(?) ",
                    parametros, "%" + criteria.getNombre() + "%");
            SQLUtils.addClause(criteria.getEmail(), condiciones, " UPPER(u.email) LIKE UPPER(?) ",
                    parametros, "%" + criteria.getEmail() + "%");
            SQLUtils.addClause(criteria.getRolNombre(), condiciones, " UPPER(r.nombre) LIKE UPPER(?) ",
                    parametros, "%" + criteria.getRolNombre() + "%");
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
            List<UsuarioDTO> usuarios = new ArrayList<>();
            while (rs.next()) {
                usuarios.add(loadNext(rs));
            }
            return usuarios;
        } catch (Exception e) {
            logger.error("Criteria: {}, Message: {}", criteria, e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(rs, ps);
        }
    }

    /**
     * Ejecuta una consulta que debe devolver como maximo un usuario.
     * @param c conexion activa con la base de datos.
     * @param sql consulta SQL a ejecutar.
     * @param value parametro de busqueda de la consulta.
     * @param field nombre del campo usado para logging.
     * @param logValue valor usado para logging.
     * @return usuario encontrado, o null si no existe.
     * @throws Exception si ocurre un error al ejecutar la consulta.
     */
    private UsuarioDTO findOne(Connection c, String sql, Object value, String field, Object logValue) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            logger.info("Buscando usuario por {}: {}", field, logValue);
            logger.debug("Executing SQL: {}", sql);
            ps = c.prepareStatement(sql);
            DAOUtils.setParameters(ps, value);
            rs = ps.executeQuery();
            return rs.next() ? loadNext(rs) : null;
        } catch (Exception e) {
            logger.error("Error buscando usuario por {}: {}, Message: {}", field, logValue, e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(rs, ps);
        }
    }

    /**
     * Carga un UsuarioDTO desde la fila actual del ResultSet.
     * @param rs ResultSet posicionado en la fila a leer.
     * @return usuario cargado con los datos de la fila actual.
     * @throws Exception si ocurre un error leyendo el ResultSet.
     */
    private UsuarioDTO loadNext(ResultSet rs) throws Exception {
        int i = 1;
        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setId(rs.getLong(i++));
        usuario.setDniNie(rs.getString(i++));
        usuario.setNombre(rs.getString(i++));
        usuario.setApellido1(rs.getString(i++));
        usuario.setApellido2(rs.getString(i++));
        usuario.setTelefono(rs.getLong(i++));
        usuario.setEmail(rs.getString(i++));
        usuario.setPassword(rs.getString(i++));
        usuario.setRolId(rs.getLong(i++));
        usuario.setNombreRol(rs.getString(i++));
        return usuario;
    }
}