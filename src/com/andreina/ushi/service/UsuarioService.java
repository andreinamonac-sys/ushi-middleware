package com.andreina.ushi.service;

import java.sql.Connection;
import java.util.List;

import com.andreina.ushi.dao.criteria.UsuarioCriteria;
import com.andreina.ushi.model.UsuarioDTO;

public interface UsuarioService {
	
	/**
	 * Registra un nuevo usuario en el sistema
	 * @param usuario Datos del usuario a insertar
	 * @return Id del ususario registrado , pero tambien lo setea en el objeto.
	 */
	public Long registrar(UsuarioDTO usuario)throws Exception;

	/**
	 * Autentica un usuario.
	 * @param usuario 
	 * @param contrasena
	 * @return Datos del usuario si la autenticacion ha sido exitosa, 
	 * o null en otro caso.
	 */
	public UsuarioDTO login(String usuario, String contrasena)throws Exception;
	
	/**
	 * Busqueda estructurada de usuarios.
	 * @param usuario
	 * @return
	 */
	public List<UsuarioDTO> findByCriteria( UsuarioCriteria usuario,
			int from, int pageSize)throws Exception;
	
	/**
	 * Actualiza los datos de un usuario
	 * en base a su id
	 * @param usuario Datos a actualizar
	 * @return Si se ha podido actualizar.
	 */
	public boolean update(UsuarioDTO usuario)throws Exception;
	
	/**
	 * Borrar un usuario
	 */
	public void delete(Long id)throws Exception;
	
	/**
	 * Actualiza la contrasena.
	 */
	public boolean updateContrasena(Long id, String oldContrasena, 
									String newContrasena)throws Exception; 
	
	/**
	 * Genera una contrasena nueva aleatoria y la envia por e-mail.
	 * @param email
	 */
	public void resetearContrasena (String email)throws Exception;
}
