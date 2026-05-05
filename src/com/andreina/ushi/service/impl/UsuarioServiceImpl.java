package com.andreina.ushi.service.impl;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;

import com.andreina.ushi.dao.UsuarioDAO;
import com.andreina.ushi.dao.criteria.UsuarioCriteria;
import com.andreina.ushi.model.UsuarioDTO;
import com.andreina.ushi.service.EncriptacionService;
import com.andreina.ushi.service.MailService;
import com.andreina.ushi.service.UsuarioService;

public class UsuarioServiceImpl implements UsuarioService {
	
	private UsuarioDAO usuarioDAO = null;
	private MailService mailService = null;
	private EncriptacionService encriptacionService = null;
	
	public UsuarioServiceImpl() {
		mailService = new MailServiceApacheImpl();
		usuarioDAO = new UsuarioDAO();
		encriptacionService = new EncriptacionServiceBCryptImpl();
	}
	
	@Override
	public Long registrar(UsuarioDTO usuario) {
		// Comprobar si ya existe
		UsuarioDTO existingUser = usuarioDAO.findByEmail(usuario.getEmail());
		if (existingUser != null) {
			return null;
		}
		
		// Encriptar contrasena
		String passwordEncrypted = encriptacionService.encrypt(usuario.getPassword());
		usuario.setPassword(passwordEncrypted);
		
		usuario = usuarioDAO.create(usuario);
		if (usuario != null) {
			mailService.sendEmail(usuario.getEmail(), "Bienvenido a Ushi", 
					"Hola " + usuario.getNombre() + ", bienvenido a Ushi.");
			return usuario.getId();
		}
		return null;
	}

	@Override
	public UsuarioDTO login(String email, String password) {
		
		// Buscar el usuario por email
		UsuarioDTO usuario = usuarioDAO.findByEmail(email);
		
		if (usuario == null) {
			return null;
		}
		
		// Verificar la contrasena
		if (encriptacionService.verify(password, usuario.getPassword())) {
			return usuario;
		} else {
			return null;
		}
	}

	@Override
	public List<UsuarioDTO> findByCriteria(UsuarioCriteria usuario, int from, int pageSize) {
		return usuarioDAO.findByCriteria(usuario, from, pageSize);
	}

	@Override
	public boolean update(UsuarioDTO usuario) {
		return usuarioDAO.update(usuario) != null;
	}

	@Override
	public void delete(Long id) {
		usuarioDAO.delete(id);
	}

	@Override
	public boolean updateContrasena(Long id, String oldContrasena, String newContrasena) {
		UsuarioDTO usuario = usuarioDAO.findById(id);
		if (usuario == null) {
			return false;
		}
		if (!encriptacionService.verify(oldContrasena, usuario.getPassword())) {
			return false;
		}
		if (encriptacionService.verify(newContrasena, usuario.getPassword())) {
			return false;
		}
		String encrypted = encriptacionService.encrypt(newContrasena);
		usuario.setPassword(encrypted);
		return usuarioDAO.update(usuario) != null;
	}

	@Override
	public void resetearContrasena(String email) {
		UsuarioDTO usuario = usuarioDAO.findByEmail(email);
		if (usuario == null) {
			return;
		}
		String nueva = RandomStringUtils.randomAlphanumeric(10);
		String encrypted = encriptacionService.encrypt(nueva);
		usuario.setPassword(encrypted);
		usuarioDAO.update(usuario);
		mailService.sendEmail(usuario.getEmail(), "Nueva contrasena", "Tu nueva contrasena es: " + nueva);
	}
}
