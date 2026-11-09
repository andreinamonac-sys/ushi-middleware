package com.andreina.ushi.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.andreina.ushi.dao.UsuarioDAO;
import com.andreina.ushi.dao.criteria.UsuarioCriteria;
import com.andreina.ushi.exception.DataException;
import com.andreina.ushi.exception.MailException;
import com.andreina.ushi.exception.ServiceException;
import com.andreina.ushi.model.Usuario;
import com.andreina.ushi.model.UsuarioDTO;
import com.andreina.ushi.service.EncryptionService;
import com.andreina.ushi.service.MailService;
import com.andreina.ushi.service.UsuarioService;
import com.andreina.ushi.utils.JDBCUtils;

public class UsuarioServiceImpl implements UsuarioService {

    private static Logger logger = LogManager.getLogger(UsuarioServiceImpl.class.getName());

    private UsuarioDAO dao = null;
    private MailService mailService = null;
    private EncryptionService encryptionService = null;

    public UsuarioServiceImpl() {
        dao = new UsuarioDAO();
        mailService = new MailServiceApacheImpl();
        encryptionService = new EncryptionServiceBCryptImpl();
    }

    public Long registrar(Usuario usuario) throws ServiceException {
		// Encriptamos antes de tocar la BD para no "secuestrar" la conexión
		String passwordEncrypted = encryptionService.encrypt(usuario.getPassword());
		usuario.setPassword(passwordEncrypted);

		Connection c = null;
		boolean commit = false;
		Usuario usuarioRegistrado = null;

		// 2. PERSISTENCIA (Transacción corta)
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false); 

			if (dao.findByEmail(c, usuario.getEmail()) != null) {
				throw new ServiceException("El usuario ya existe");
			}
			usuarioRegistrado = dao.create(c, usuario);
			commit = true;
		} catch (DataException e) {
			logger.error("Error de persistencia al registrar cliente {}: {}", usuario, e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Error registrando {}: {} ", usuario.getEmail(), e.getMessage());
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}

		// Solo enviamos el email si la transacción terminó con éxito (commit == true)
		if (commit && usuarioRegistrado != null) {
			enviarEmailBienvenida(usuarioRegistrado);
		}
		return usuarioRegistrado.getId();
	}

	private void enviarEmailBienvenida(Usuario usuario) {
		try {
			mailService.sendEmail(
					usuario.getEmail(), 
					"Bienvenido a Ushi!", 
					"Hola " + usuario.getNombre() + ", bienvenido.", "Ushi");
		} catch (MailException e) {
			// Logueamos pero no lanzamos excepción para no romper el registro
			// ya que el usuario YA se guardó en la BD.
			
			logger.warn("No se pudo enviar el email a {}", usuario.getEmail());
		}
	}
    
    @Override
	public UsuarioDTO findById(Long id) throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			UsuarioDTO encontrado = dao.findById(c, id);
			commit = true;
			return encontrado;
		} catch (ServiceException e) {
			logger.error("Error de persistencia al buscar usuario con id{}: {}", id, e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Error buscando usuario por id {}: {}", id, e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}				
	}

    @Override
    public UsuarioDTO login(String email, String password) throws ServiceException {
        Connection c = null;
        try {
            c = JDBCUtils.getConnection();
            UsuarioDTO usuario = dao.findByEmail(c, email);
            if (usuario == null) {
                return null;
            }
            return encryptionService.verify(password, usuario.getPassword()) ? usuario : null;
        } catch (Exception e) {
            logger.error("Login de usuario {}: {}", email, e.getMessage(), e);
            throw new ServiceException(e);
        } finally {
            JDBCUtils.close(c, true);
        }
    }

    @Override
    public List<UsuarioDTO> findByCriteria(UsuarioCriteria usuario, int from, int pageSize) throws ServiceException {
        Connection c = null;
        try {
            c = JDBCUtils.getConnection();
            return dao.findByCriteria(c, usuario, from, pageSize);
        } catch (Exception e) {
            logger.error("Buscando {}: {}", usuario, e.getMessage(), e);
            throw new ServiceException(e);
        } finally {
            JDBCUtils.close(c, true);
        }
    }

    @Override
    public boolean update(UsuarioDTO usuario) throws ServiceException {
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
            throw new ServiceException(e);
        } finally {
            JDBCUtils.close(c, commit);
        }
    }

    @Override
    public void delete(Long id) throws ServiceException {
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
            throw new ServiceException(e);
        } finally {
            JDBCUtils.close(c, commit);
        }
    }

    @Override
    public boolean updateContrasena(Long id, String oldContrasena, String newContrasena) throws ServiceException {
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
            if (!encryptionService.verify(oldContrasena, usuario.getPassword())) {
                commit = true;
                return false;
            }
            if (encryptionService.verify(newContrasena, usuario.getPassword())) {
                commit = true;
                return false;
            }
            String encrypted = encryptionService.encrypt(newContrasena);
            usuario.setPassword(encrypted);
            boolean updated = dao.update(c, usuario);
            commit = true;
            return updated;
        } catch (Exception e) {
            logger.error("Actualizando contrasena de usuario {}: {}", id, e.getMessage(), e);
            throw new ServiceException(e);
        } finally {
            JDBCUtils.close(c, commit);
        }
    }

    @Override
    public void resetearContrasena(String email) throws ServiceException {
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
            String nueva = RandomStringUtils.secure().nextAlphanumeric(10);
            String encrypted = encryptionService.encrypt(nueva);
            usuario.setPassword(encrypted);
            dao.update(c, usuario);
            mailService.sendEmail(usuario.getEmail(), "Nueva contrasena",
                    "Tu nueva contrasena es: " + nueva, null);
            commit = true;
        } catch (Exception e) {
            logger.error("Reseteando contrasena de email {}: {}", email, e.getMessage(), e);
            throw new ServiceException(e);
        } finally {
            JDBCUtils.close(c, commit);
        }
    }
}