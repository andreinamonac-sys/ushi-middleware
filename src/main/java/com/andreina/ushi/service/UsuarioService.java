package com.andreina.ushi.service;

import java.util.List;

import com.andreina.ushi.dao.criteria.UsuarioCriteria;
import com.andreina.ushi.exception.ServiceException;
import com.andreina.ushi.model.Usuario;
import com.andreina.ushi.model.UsuarioDTO;

public interface UsuarioService {
	
	/**
	 * Registra un nuevo usuario en el sistema
	 * @param usuario Datos del usuario a insertar
	 * @return Id del ususario registrado , pero tambien lo setea en el objeto.
	 */
	public Long registrar(Usuario usuario)throws ServiceException;

	/**
	 * Encontrar usuario por su id
	 * @param id
	 * @return usuario
	 * @throws ServiceException
	 */
	public UsuarioDTO findById(Long id) throws ServiceException;
	/**
	 * Autentica un usuario.
	 * @param usuario 
	 * @param contrasena
	 * @return Datos del usuario si la autenticacion ha sido exitosa, 
	 * o null en otro caso.
	 */
	public UsuarioDTO login(String usuario, String contrasena)throws ServiceException;
	
	/**
	 * Busqueda estructurada de usuarios.
	 * @param usuario
	 * @return
	 */
	public List<UsuarioDTO> findByCriteria( UsuarioCriteria usuario,
			int from, int pageSize)throws ServiceException;
	
	/**
	 * Actualiza los datos de un usuario
	 * en base a su id
	 * @param usuario Datos a actualizar
	 * @return Si se ha podido actualizar.
	 */
	public boolean update(UsuarioDTO usuario)throws ServiceException;
	
	/**
	 * Borrar un usuario
	 */
	public void delete(Long id)throws ServiceException;
	
	/**
	 * Actualiza la contrasena.
	 */
	public boolean updateContrasena(Long id, String oldContrasena, 
									String newContrasena)throws ServiceException; 
	
	/**
	 * Genera una contrasena nueva aleatoria y la envia por e-mail.
	 * @param email
	 */
	public void resetearContrasena (String email)throws ServiceException;
}
