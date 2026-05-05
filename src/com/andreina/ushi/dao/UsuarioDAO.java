package com.andreina.ushi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.andreina.ushi.dao.criteria.AnimalCriteria;
import com.andreina.ushi.dao.criteria.UsuarioCriteria;
import com.andreina.ushi.model.AnimalDTO;
import com.andreina.ushi.model.UsuarioDTO;
import com.andreina.ushi.utils.DAOUtils;
import com.andreina.ushi.utils.SQLUtils;

public class UsuarioDAO {
	
	private static final String BASE_QUERY = 
		    "SELECT u.id, u.dni_nie, u.nombre, u.apellido1, u.apellido2, " +
		    "u.telefono, u.email, u.password, " +
		    "r.id AS rol_id, r.nombre AS rol_nombre " +
		    "FROM usuario u " +
		    "INNER JOIN rol r ON u.rol_id = r.id ";
	
	/**
	 * Crea un nuevo usuario en la base de datos.
	 * @param usuario
	 * @return el usuario creado con su id asignado, o null si no se pudo crear.
	 */
	public UsuarioDTO create(UsuarioDTO usuario) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			c=DAOUtils.getConnection();
			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append("INSERT INTO usuario (dni_nie, nombre, apellido1, apellido2, telefono, email, password, rol_id) ");
			sqlBuilder.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
			ps = c.prepareStatement(sqlBuilder.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
			int i = 1;
			ps.setString(i++, usuario.getDniNie());
			ps.setString(i++, usuario.getNombre());
			ps.setString(i++, usuario.getApellido1());
			ps.setString(i++, usuario.getApellido2());
			ps.setLong(i++, usuario.getTelefono());
			ps.setString(i++, usuario.getEmail());
			ps.setString(i++, usuario.getPassword());
			ps.setLong(i++, usuario.getRolId());
			
			ps.executeUpdate();
			
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				usuario.setId(rs.getLong(1));
			}
			return usuario;
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	/**
	 * Actualizar un usuario existente.
	 * @param usuario
	 * @return
	 */
	public UsuarioDTO update(UsuarioDTO usuario) {
		Connection c = null;
		PreparedStatement ps = null;
		try {
			c = DAOUtils.getConnection();
			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append("UPDATE usuario SET dni_nie = ?, nombre = ?, apellido1 = ?, ");
			sqlBuilder.append("apellido2 = ?, telefono = ?, email = ?, password = ?, rol_id = ? ");
			sqlBuilder.append("WHERE id = ?");
			ps = c.prepareStatement(sqlBuilder.toString());
			int i = 1;
			
			ps.setString(i++, usuario.getDniNie());
			ps.setString(i++, usuario.getNombre());
			ps.setString(i++, usuario.getApellido1());
			ps.setString(i++, usuario.getApellido2());
			ps.setLong(i++, usuario.getTelefono());
			ps.setString(i++, usuario.getEmail());
			ps.setString(i++, usuario.getPassword());
			ps.setLong(i++, usuario.getRolId());
			ps.setLong(i++, usuario.getId());
			
			ps.executeUpdate();			
			return usuario;		
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(null, ps, c);
		}
		return null;
	}
	
	/**
	 * Eliminar un usuario por su id.
	 * @param id
	 */
	public boolean delete(Long id) {
		Connection c = null;
		PreparedStatement ps = null;
		
		try {
			c = DAOUtils.getConnection();
			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append("DELETE FROM usuario WHERE id = ?");
			ps = c.prepareStatement(sqlBuilder.toString());
			int i = 1;
			ps.setLong(i++, id);
			ps.executeUpdate();			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(null, ps, c);
		}
		return false;
	}

	/**
	 * Buscar un usuario por su id.
	 * @param id
	 * @return
	 */
	public UsuarioDTO findById(Long id) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {			
			// obtener la conexion
			c = DAOUtils.getConnection();

			// preparar la consulta
			StringBuilder sqlBuilder = new StringBuilder(BASE_QUERY);
			sqlBuilder.append(" WHERE u.id = ? ");

			// crear el PreparedStatement y ejecutar la consulta.
			ps = c.prepareStatement(sqlBuilder.toString());
			DAOUtils.setParameters(ps, id);				
			rs = ps.executeQuery();

			UsuarioDTO usuario = null; 	
			
			if (rs.next()) {
				
				usuario = loadNext(rs);
				
			}
			
			return usuario;				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	/**
	 * Busca un usuario por su email.
	 * @param email
	 * @return el usuario encontrado, o null si no se encontro ningun usuario con ese email.
	 */
	public UsuarioDTO findByEmail(String email) {

	    Connection c = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        c = DAOUtils.getConnection();

	        StringBuilder sqlBuilder = new StringBuilder(BASE_QUERY);
	        sqlBuilder.append(" WHERE u.email = ? ");

	        ps = c.prepareStatement(sqlBuilder.toString());
	        ps.setString(1, email);

	        rs = ps.executeQuery();

	        if (rs.next()) {
	            return loadNext(rs);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        DAOUtils.close(rs, ps, c);
	    }

	    return null;
	}

	/**
	 * Busca un usuario por los criterios especificados. Se pueden especificar uno o varios criterios, 
	 * y se devolvera el primer usuario que coincida con todos los criterios especificados.
	 * @param criteria
	 * @return
	 */
	public List<UsuarioDTO> findByCriteria(UsuarioCriteria criteria, int from, int pageSize) {
		
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {		

			c = DAOUtils.getConnection();

			StringBuilder sqlBuilder = new StringBuilder(BASE_QUERY);
			
			
			List<String> condiciones = new ArrayList<>();
			List<Object> parametros = new ArrayList<>();

			// Obtener los valores de los criterios
			SQLUtils.addClause(criteria.getId(),
			        condiciones, " e.id = ? ", parametros, criteria.getId());

			SQLUtils.addClause(criteria.getDniNie(),
			        condiciones, " UPPER(a.dni_nie) LIKE UPPER(?) ",
			        parametros, "%" + criteria.getDniNie() + "%");

			SQLUtils.addClause(criteria.getNombre(),
			        condiciones, " UPPER(a.nombre) LIKE UPPER(?) ",
			        parametros, "%" + criteria.getNombre() + "%");
			
			SQLUtils.addClause(criteria.getEmail(),
			        condiciones, " UPPER(a.email) LIKE UPPER(?) ",
			        parametros, "%" + criteria.getEmail() + "%");
			
			
			

			if (condiciones.size() > 0) {
				sqlBuilder.append(" WHERE ");
				sqlBuilder.append( String.join(" AND ", condiciones) );
			}
			String sql = sqlBuilder.toString();
			System.out.println("SQL: " + sql);

			ps = c.prepareStatement(sql);

			int i = 1;
			for (Object parametro: parametros) {
				ps.setObject(i++, parametro);
			}

			rs = ps.executeQuery();

			List<UsuarioDTO> usuarios = new ArrayList<>();
			while (rs.next()) {							
				usuarios.add(loadNext(rs));
				
			}

			return usuarios;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (c != null)
					c.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Carga un usuario a partir de un ResultSet. Se asume que el ResultSet ya esta posicionado en la fila correcta.
	 * @param rs
	 * @return el usuario cargado, o null si ocurrio un error al cargar el usuario.
	 */
	private UsuarioDTO loadNext(ResultSet rs) {
		try {
			UsuarioDTO usuario = new UsuarioDTO();
			usuario.setId(rs.getLong("id"));
			usuario.setDniNie(rs.getString("dni_nie"));
			usuario.setNombre(rs.getString("nombre"));
			usuario.setApellido1(rs.getString("apellido1"));
			usuario.setApellido2(rs.getString("apellido2"));
			usuario.setTelefono(rs.getLong("telefono"));
			usuario.setEmail(rs.getString("email"));
			usuario.setPassword(rs.getString("password"));
			usuario.setRolId(rs.getLong("rol_id"));
			usuario.setNombreRol(rs.getString("rol_nombre"));
			
			return usuario;
			
		} catch (Exception e) {
			e.printStackTrace();
		
		return null;
		}
	}
	
}
