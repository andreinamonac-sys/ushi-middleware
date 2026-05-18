package com.andreina.ushi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.andreina.ushi.model.UsuarioLoginDTO;
import com.andreina.ushi.utils.DAOUtils;
import com.andreina.ushi.utils.JDBCUtils;

public class UsuarioLoginDAO {

    private static Logger logger = LogManager.getLogger(UsuarioLoginDAO.class.getName());

    private static final String BASE_QUERY =
            "SELECT u.id, u.email, u.password, u.rol_id, r.nombre as rol_nombre " +
            "FROM usuario u " +
            "INNER JOIN rol r ON u.rol_id = r.id ";
    private static final String ORDER_BY = " ORDER BY u.email";

    public UsuarioLoginDAO() {
    }

    public UsuarioLoginDTO findById(Connection c, Long id) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = c.prepareStatement(BASE_QUERY + " WHERE u.id = ?");
            DAOUtils.setParameters(ps, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return loadNext(rs);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(rs, ps);
        }
        return null;
    }

    public UsuarioLoginDTO findByEmail(Connection c, String email) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = c.prepareStatement(BASE_QUERY + " WHERE UPPER(u.email) = UPPER(?)");
            DAOUtils.setParameters(ps, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                return loadNext(rs);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(rs, ps);
        }
        return null;
    }

    public List<UsuarioLoginDTO> findAll(Connection c) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = c.prepareStatement(BASE_QUERY + ORDER_BY);
            rs = ps.executeQuery();
            List<UsuarioLoginDTO> results = new ArrayList<>();
            while (rs.next()) {
                results.add(loadNext(rs));
            }
            return results;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(rs, ps);
        }
    }

    public Long create(Connection c, UsuarioLoginDTO entity) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "INSERT INTO usuario (email, password, rol_id) VALUES (?, ?, ?)";
            ps = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            DAOUtils.setParameters(ps, entity.getEmail(), entity.getPassword(), entity.getRolId());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(rs, ps);
        }
        return null;
    }

    public boolean update(Connection c, UsuarioLoginDTO entity) throws Exception {
        PreparedStatement ps = null;
        try {
            String sql = "UPDATE usuario SET email = ?, password = ?, rol_id = ? WHERE id = ?";
            ps = c.prepareStatement(sql);
            DAOUtils.setParameters(ps, entity.getEmail(), entity.getPassword(), entity.getRolId(), entity.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(null,ps);
        }
    }

    public boolean delete(Connection c, Long id) throws Exception {
        PreparedStatement ps = null;
        try {
            ps = c.prepareStatement("DELETE FROM usuario WHERE id = ?");
            DAOUtils.setParameters(ps, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(null, ps);
        }
    }

    private UsuarioLoginDTO loadNext(ResultSet rs) throws Exception {
        int i = 1;
        UsuarioLoginDTO usuarioL = new UsuarioLoginDTO();
        usuarioL.setId(rs.getLong(i++));
        usuarioL.setEmail(rs.getString(i++));
        usuarioL.setPassword(rs.getString(i++));
        usuarioL.setRolId(rs.getLong(i++));
        usuarioL.setRolNombre(rs.getString(i++));
        return usuarioL;
    }
}