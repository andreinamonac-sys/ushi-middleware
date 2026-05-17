package com.andreina.ushi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.andreina.ushi.model.Tag;
import com.andreina.ushi.utils.DAOUtils;
import com.andreina.ushi.utils.JDBCUtils;

public class TagDAO {

    private static final Logger logger = LogManager.getLogger(TagDAO.class.getName());
    private static final String BASE_QUERY =
            "SELECT t.id, t.numero, t.ultima_actualizacion, t.version_software, t.tipo, t.incidencias FROM tag t";
    private static final String ORDER_BY = " ORDER BY t.id ASC";
    
    public TagDAO() {
    }
    /**
	 * Crea un nuevo tag.
	 * @param c conexion activa con la base de datos.
	 * @param tag datos del tag a crear.
	 * @return tag creado con el id generado.
	 * @throws Exception si ocurre un error al crear el tag.
	 */
    public Tag create(Connection c, Tag tag) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            
            String sql = "INSERT INTO tag (numero, ultima_actualizacion, version_software, tipo, incidencias) "
                    + "VALUES (?, ?, ?, ?, ?)";
            logger.debug("Executing SQL: {}", sql);
            ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            DAOUtils.setParameters(ps, tag.getNumero(), DAOUtils.toTimestamp(tag.getUltimaActualizacion()),
                    tag.getVersionSoftware(), tag.getTipo(), tag.getIncidencias());
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    tag.setId(rs.getLong(1));
                    logger.info("Tag creado con id: {}", tag.getId());
                }
            }
            return tag;
        } catch (Exception e) {
            logger.error("Error creando tag con id: {}", tag.getId(), e);
            throw e;
        } finally {
            JDBCUtils.close(rs, ps);
        }
    }
    /**
     * Actualiza un tag existente.
     * @param c
     * @param tag
     * @return
     * @throws Exception
     */
    public boolean update(Connection c, Tag tag) throws Exception {
        PreparedStatement ps = null;
        try {
            
            String sql = "UPDATE tag SET numero = ?, ultima_actualizacion = ?, version_software = ?, "
                    + "tipo = ?, incidencias = ? WHERE id = ?";
            logger.info("Actualizando tag con id: {}", tag.getId());
            logger.debug("Executing SQL: {}", sql);
            ps = c.prepareStatement(sql);
            DAOUtils.setParameters(ps, tag.getNumero(), DAOUtils.toTimestamp(tag.getUltimaActualizacion()),
					                    tag.getVersionSoftware(), tag.getTipo(), 
					                    tag.getIncidencias(), tag.getId());
            int updatedRows = ps.executeUpdate();
            logger.info("Tag con id: {} actualizado, filas afectadas: {}", tag.getId(), updatedRows);
            return updatedRows == 1;
        } catch (Exception e) {
            logger.error("Error actualizando tag con id: {}", tag.getId(), e);
            throw e;
        } finally {
            JDBCUtils.close(null, ps);
        }
    }
    /**
	 * Elimina un tag por su id.
	 * @param c conexion activa con la base de datos.
	 * @param id del tag a eliminar.
	 * @return true si se eliminó un registro, false si no se encontró el id.
	 * @throws Exception si ocurre un error al eliminar el tag.
	 */
    public boolean delete(Connection c, Long id) throws Exception {
        PreparedStatement ps = null;
        try {
            String sql = "DELETE FROM tag WHERE id = ?";
            logger.info("Eliminando tag con id: {}", id);
            logger.debug("Executing SQL: {}", sql);
            ps = c.prepareStatement(sql);
            DAOUtils.setParameters(ps, id);
            int deleted = ps.executeUpdate();
            logger.info("Tag con id: {} eliminado, filas afectadas: {}", id, deleted);
            return deleted == 1;
        } catch (Exception e) {
            logger.error("Error eliminando tag con id: {}", id, e);
            throw e;
        } finally {
            JDBCUtils.close(null, ps);
        }
    }
    /**
     * Busca un tag por su id.
     * @param c
     * @param id
     * @return
     * @throws Exception
     */
    public Tag findById(Connection c, Long id) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Tag tag = null;
        try {
            String sql = BASE_QUERY + " WHERE t.id = ?";
            logger.info("Buscando tag con id: {}", id);
            logger.debug("Executing SQL: {}", sql);
            ps = c.prepareStatement(sql);
            DAOUtils.setParameters(ps, id);
            rs = ps.executeQuery();
            if (rs.next()) {
				tag= loadNext(rs);
				 logger.info("Tag encontrado: {}", tag);
			}
			return tag;	
        } catch (Exception e) {
            logger.error("Error encontrando tag con id: {}", id, e);
            throw e;
        } finally {
            JDBCUtils.close(rs, ps);
        }
    }

    /**
     * Busca un tag por su numero.
     * @param c
     * @param numero
     * @return
     * @throws Exception
     */
    public Tag findByNumero(Connection c, String numero) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Tag tag = null;
        try {
            
            String sql = BASE_QUERY + " WHERE t.numero = ?";
            logger.info("Buscando tag con numero: {}", numero);
            logger.debug("Executing SQL: {}", sql);
            ps = c.prepareStatement(sql);
            DAOUtils.setParameters(ps, numero);
            rs = ps.executeQuery();
            if (rs.next()) {
				tag= loadNext(rs);
				 logger.info("Tag encontrado: {}", tag);
			}
			return tag;	
        } catch (Exception e) {
            logger.error("Error encontrando tag con numero: {}", numero, e);
            throw e;
        } finally {
            JDBCUtils.close(rs, ps);
        }
    }

    /**
     * Busca los tags asociados a un animal por su id.
     * @param c
     * @param animalId
     * @return
     * @throws Exception
     */
    public List<Tag> findByAnimalId(Connection c, Long animalId) throws Exception {
       
        String sql = "SELECT DISTINCT t.id, t.numero, t.ultima_actualizacion, t.version_software, t.tipo, t.incidencias "
                + "FROM tag t INNER JOIN parametro p ON p.tag_id = t.id WHERE p.animal_id = ?" + ORDER_BY;
        return findList(c, sql, animalId);
    }
    /**
     * Busca los tags que tienen incidencias registradas (no nulas, no vacías y distintas de 'NINGUNA').
     * @param c
     * @return
     * @throws Exception
     */
    public List<Tag> findConIncidencias(Connection c) throws Exception {
        return findList(c, BASE_QUERY + " WHERE t.incidencias IS NOT NULL AND t.incidencias <> '' "
                + "AND UPPER(t.incidencias) <> 'NINGUNA'" + ORDER_BY);
    }
	/**
	 * Busca los tags que no tienen incidencias registradas (nulas, vacías o iguales a 'NINGUNA').
	 * @param c
	 * @return
	 * @throws Exception
	 */
    public List<Tag> findDisponible(Connection c) throws Exception {
        return findList(c, BASE_QUERY + " WHERE t.incidencias IS NULL OR t.incidencias = '' "
                + "OR UPPER(t.incidencias) = 'NINGUNA' " + ORDER_BY);
    }
    /**
     * Ejecuta una consulta de tags y carga sus resultados.
     * @param c
     * @param sql
     * @param params
     * @return
     * @throws Exception
     */
    private List<Tag> findList(Connection c, String sql, Object... params) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            logger.debug("Executing SQL: {}", sql);
            ps = c.prepareStatement(sql);
            DAOUtils.setParameters(ps, params);
            rs = ps.executeQuery();
            List<Tag> tags = new ArrayList<>();
            while (rs.next()) {
                tags.add(loadNext(rs));
            }
            return tags;
        } catch (Exception e) {
            logger.error("Error listando tags, Message: {}", e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(rs, ps);
        }
    }

    private Tag loadNext(ResultSet rs) throws Exception {
        int i = 1;
        Tag tag = new Tag();
        tag.setId(rs.getLong(i++));
        tag.setNumero(rs.getString(i++));
        tag.setUltimaActualizacion(rs.getTimestamp(i++));
        tag.setVersionSoftware(rs.getString(i++));
        tag.setTipo(rs.getString(i++));
        tag.setIncidencias(rs.getString(i++));
        return tag;
    }
}