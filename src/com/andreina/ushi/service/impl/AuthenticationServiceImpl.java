package com.andreina.ushi.service.impl;


	import java.sql.Connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.andreina.ushi.dao.UsuarioLoginDAO;
import com.andreina.ushi.model.UsuarioLoginDTO;
import com.andreina.ushi.service.AuthenticationService;
import com.andreina.ushi.service.EncriptacionService;
import com.andreina.ushi.utils.JDBCUtils;

	public class AuthenticationServiceImpl implements AuthenticationService {

	    private static Logger logger = LogManager.getLogger(AuthenticationServiceImpl.class.getName());

	    private UsuarioLoginDAO usuarioLoginDAO = null;
	    private EncriptacionService encryptionService = null;

	    public AuthenticationServiceImpl() {
	        this.usuarioLoginDAO = new UsuarioLoginDAO();
	        this.encryptionService = new EncriptacionServiceBCryptImpl();
	    }

	    @Override
	    public UsuarioLoginDTO login(String email, String password) throws Exception {
	        Connection c = null;
	        boolean commit = false;
	        try {
	            c = JDBCUtils.getConnection();
	            c.setAutoCommit(false);
	            String safeEmail = trimToNull(email);
	            if (safeEmail == null || password == null || password.isEmpty()) {
	                commit = true;
	                return null;
	            }
	            UsuarioLoginDTO usuario = usuarioLoginDAO.findByEmail(c, safeEmail);
	            if (usuario == null || !Boolean.TRUE.equals(usuario.getActivo())) {
	                commit = true;
	                return null;
	            }
	            UsuarioLoginDTO result = encryptionService.verify(password, usuario.getPassword()) ? usuario : null;
	            commit = true;
	            return result;
	        } catch (Exception e) {
	            logger.error(e.getMessage(), e);
	            throw e;
	        } finally {
	            JDBCUtils.close(c, commit);
	        }
	    }

	    @Override
	    public UsuarioLoginDTO findByEmail(String email) throws Exception {
	        Connection c = null;
	        boolean commit = false;
	        try {
	            c = JDBCUtils.getConnection();
	            c.setAutoCommit(false);
	            String safeEmail = trimToNull(email);
	            UsuarioLoginDTO result = safeEmail == null ? null : usuarioLoginDAO.findByEmail(c, safeEmail);
	            commit = true;
	            return result;
	        } catch (Exception e) {
	            logger.error(e.getMessage(), e);
	            throw e;
	        } finally {
	            JDBCUtils.close(c, commit);
	        }
	    }

	    private String trimToNull(String value) {
	        if (value == null) {
	            return null;
	        }
	        String trimmed = value.trim();
	        return trimmed.isEmpty() ? null : trimmed;
	    }
	}

