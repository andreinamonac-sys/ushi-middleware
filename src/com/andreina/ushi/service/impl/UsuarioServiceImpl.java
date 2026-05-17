package com.andreina.ushi.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.andreina.ushi.dao.UsuarioDAO;
import com.andreina.ushi.dao.criteria.UsuarioCriteria;
import com.andreina.ushi.model.UsuarioDTO;
import com.andreina.ushi.service.EncriptacionService;
import com.andreina.ushi.service.MailService;
import com.andreina.ushi.service.UsuarioService;
import com.andreina.ushi.utils.JDBCUtils;

public class UsuarioServiceImpl implements UsuarioService {

    private static Logger logger = LogManager.getLogger(UsuarioServiceImpl.class.getName());

    private UsuarioDAO dao = null;
    private MailService mailService = null;
    private EncriptacionService encriptacionService = null;

    public UsuarioServiceImpl() {
        dao = new UsuarioDAO();
        mailService = new MailServiceApacheImpl();
        encriptacionService = new EncriptacionServiceBCryptImpl();
    }

    @Override
    public Long registrar(UsuarioDTO usuario) throws Exception {
        Connection c = null;
        boolean commit = false;
        try {
            c = JDBCUtils.getConnection();
            c.setAutoCommit(false);

            UsuarioDTO existingUser = dao.findByEmail(c, usuario.getEmail());
            if (existingUser != null) {
                commit = true;
                return null;
            }

            String passwordEncrypted = encriptacionService.encrypt(usuario.getPassword());
            usuario.setPassword(passwordEncrypted);

            UsuarioDTO result = dao.create(c, usuario);
            if (result != null) {
                mailService.sendEmail(result.getEmail(), "Bienvenido a Ushi",
                        "Hola " + result.getNombre() + ", bienvenido a Ushi.", null);
            }
            commit = true;
            return result != null ? result.getId() : null;
        } catch (Exception e) {
            logger.error("Registrando {}: {}", usuario, e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(c, commit);
        }
    }

    @Override
    public UsuarioDTO login(String email, String password) throws Exception {
        Connection c = null;
        try {
            c = JDBCUtils.getConnection();
            UsuarioDTO usuario = dao.findByEmail(c, email);
            if (usuario == null) {
                return null;
            }
            return encriptacionService.verify(password, usuario.getPassword()) ? usuario : null;
        } catch (Exception e) {
            logger.error("Login de usuario {}: {}", email, e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(c, true);
        }
    }

    @Override
    public List<UsuarioDTO> findByCriteria(UsuarioCriteria usuario, int from, int pageSize) throws Exception {
        Connection c = null;
        try {
            c = JDBCUtils.getConnection();
            return dao.findByCriteria(c, usuario, from, pageSize);
        } catch (Exception e) {
            logger.error("Buscando {}: {}", usuario, e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(c, true);
        }
    }

    @Override
    public boolean update(UsuarioDTO usuario) throws Exception {
        if (usuario.getId() == null || usuario.getId() <= 0) return false;
        Connection c = null;
        boolean commit = false;
        try {
            c = JDBCUtils.getConnection();
            c.setAutoCommit(false);
            boolean updated = dao.update(c, usuario);
            commit = true;
            return updated;
        } catch (Exception e) {
            logger.error("Actualizando {}: {}", usuario, e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(c, commit);
        }
    }

    @Override
    public void delete(Long id) throws Exception {
        if (id == null || id <= 0) return;
        Connection c = null;
        boolean commit = false;
        try {
            c = JDBCUtils.getConnection();
            c.setAutoCommit(false);
            dao.delete(c, id);
            commit = true;
        } catch (Exception e) {
            logger.error("Eliminando usuario {}: {}", id, e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(c, commit);
        }
    }

    @Override
    public boolean updateContrasena(Long id, String oldContrasena, String newContrasena) throws Exception {
        if (id == null || id <= 0) return false;
        Connection c = null;
        boolean commit = false;
        try {
            c = JDBCUtils.getConnection();
            c.setAutoCommit(false);
            UsuarioDTO usuario = dao.findById(c, id);
            if (usuario == null) {
                commit = true;
                return false;
            }
            if (!encriptacionService.verify(oldContrasena, usuario.getPassword())) {
                commit = true;
                return false;
            }
            if (encriptacionService.verify(newContrasena, usuario.getPassword())) {
                commit = true;
                return false;
            }
            String encrypted = encriptacionService.encrypt(newContrasena);
            usuario.setPassword(encrypted);
            boolean updated = dao.update(c, usuario);
            commit = true;
            return updated;
        } catch (Exception e) {
            logger.error("Actualizando contrasena de usuario {}: {}", id, e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(c, commit);
        }
    }

    @Override
    public void resetearContrasena(String email) throws Exception {
        Connection c = null;
        boolean commit = false;
        try {
            c = JDBCUtils.getConnection();
            c.setAutoCommit(false);
            UsuarioDTO usuario = dao.findByEmail(c, email);
            if (usuario == null) {
                commit = true;
                return;
            }
            String nueva = RandomStringUtils.randomAlphanumeric(10);
            String encrypted = encriptacionService.encrypt(nueva);
            usuario.setPassword(encrypted);
            dao.update(c, usuario);
            mailService.sendEmail(usuario.getEmail(), "Nueva contrasena",
                    "Tu nueva contrasena es: " + nueva, null);
            commit = true;
        } catch (Exception e) {
            logger.error("Reseteando contrasena de email {}: {}", email, e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(c, commit);
        }
    }
}