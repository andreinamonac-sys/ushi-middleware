package com.andreina.ushi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.andreina.ushi.model.DosisDTO;
import com.andreina.ushi.model.Tratamiento;
import com.andreina.ushi.utils.DAOUtils;
import com.andreina.ushi.utils.JDBCUtils;

public class TratamientoDAO {

    private static final Logger logger = LogManager.getLogger(TratamientoDAO.class.getName());
    private static final String BASE_QUERY = "SELECT t.id, t.nombre FROM tratamiento t";
    private static final String ORDER_BY= " ORDER BY t.nombre DESC";
    
    public TratamientoDAO() {
    }

    /**
     * Crea un nuevo tratamiento.
     * @param c conexion activa con la base de datos.
     * @param tratamiento datos del tratamiento a crear.
     * @return tratamiento creado con el id generado.
     * @throws Exception si ocurre un error al crear el tratamiento.
     */
    public Tratamiento create(Connection c, Tratamiento tratamiento) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "INSERT INTO tratamiento (nombre) VALUES (?)";
            logger.debug("Executing SQL: {}", sql);
            ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            DAOUtils.setParameters(ps, tratamiento.getNombre());
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    tratamiento.setId(rs.getLong(1));
                    logger.info("Tratamiento creado con id: {}", tratamiento.getId());
                }
            }
            return tratamiento;
        } catch (Exception e) {
            logger.error("Error creando tratamiento con nombre: {}, Message: {}", tratamiento.getNombre(), e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(rs, ps);
        }
    }

    /**
     * Actualiza un tratamiento existente.
     * @param c conexion activa con la base de datos.
     * @param tratamiento datos del tratamiento a actualizar; debe incluir el id.
     * @return tratamiento actualizado si se modifico una fila, o null si no se encontro.
     * @throws Exception si ocurre un error al actualizar el tratamiento.
     */
    public boolean update(Connection c, Tratamiento tratamiento) throws Exception {
        PreparedStatement ps = null;
        try {
            String sql = "UPDATE tratamiento SET nombre = ? WHERE id = ?";
            logger.info("Actualizando tratamiento con id: {}", tratamiento.getId());
            logger.debug("Executing SQL: {}", sql);
            ps = c.prepareStatement(sql);
            DAOUtils.setParameters(ps, tratamiento.getNombre(), tratamiento.getId());
            int updatedRows = ps.executeUpdate();
            logger.info("Tratamiento con id: {} actualizado, filas afectadas: {}", tratamiento.getId(), updatedRows);
            return updatedRows == 1;
        } catch (Exception e) {
            logger.error("Error actualizando tratamiento con id: {}, Message: {}", tratamiento.getId(), e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(null, ps);
        }
    }

    /**
     * Elimina un tratamiento por su id.
     * @param c conexion activa con la base de datos.
     * @param id identificador del tratamiento a eliminar.
     * @return true si se elimino una fila, false si no se encontro el tratamiento.
     * @throws Exception si ocurre un error al eliminar el tratamiento.
     */
    public boolean delete(Connection c, Long id) throws Exception {
        PreparedStatement ps = null;
        try {
            String sql = "DELETE FROM tratamiento WHERE id = ?";
            logger.info("Eliminando tratamiento con id: {}", id);
            logger.debug("Executing SQL: {}", sql);
            ps = c.prepareStatement(sql);
            DAOUtils.setParameters(ps, id);
            int deleted = ps.executeUpdate();
            logger.info("Tratamiento con id: {} eliminado, filas afectadas: {}", id, deleted);
            return deleted == 1;
        } catch (Exception e) {
            logger.error("Error eliminando tratamiento con id: {}, Message: {}", id, e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(null, ps);
        }
    }

    /**
     * Lista tratamientos ordenados alfabeticamente por nombre.
     * @param c conexion activa con la base de datos.
     * @return lista de tratamientos ordenados por nombre.
     * @throws Exception si ocurre un error al listar tratamientos.
     */
    public List<Tratamiento> findByNombre(Connection c) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = BASE_QUERY + ORDER_BY;
            logger.debug("Executing SQL: {}", sql);
            ps = c.prepareStatement(sql);
            rs = ps.executeQuery();
            List<Tratamiento> tratamientos = new ArrayList<>();
            while (rs.next()) {
                tratamientos.add(loadNext(rs));
            }
            return tratamientos;
        } catch (Exception e) {
            logger.error("Error listando tratamientos, Message: {}", e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(rs, ps);
        }
    }

    /**
     * Lista las dosis asociadas a un tratamiento.
     * @param c conexion activa con la base de datos.
     * @param tratamientoId identificador del tratamiento.
     * @return lista de dosis del tratamiento.
     * @throws Exception si ocurre un error al listar dosis.
     */
    public List<DosisDTO> findDosisByTratamientoId(Connection c, Long tratamientoId) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT d.id, d.plazo_siguiente, d.num_dosis, d.tratamiento_id "
                    + "FROM dosis d WHERE d.tratamiento_id = ? "+ORDER_BY;
            logger.debug("Executing SQL: {}", sql);
            ps = c.prepareStatement(sql);
            DAOUtils.setParameters(ps, tratamientoId);
            rs = ps.executeQuery();
            List<DosisDTO> dosis = new ArrayList<>();
            while (rs.next()) {
                dosis.add(loadNextDosis(rs));
            }
            return dosis;
        } catch (Exception e) {
            logger.error("Error listando dosis para tratamiento_id: {}, Message: {}", tratamientoId, e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(rs, ps);
        }
    }

    /**
     * Crea una dosis para un tratamiento.
     * @param c conexion activa con la base de datos.
     * @param dosis datos de la dosis a crear.
     * @return dosis creada con el id generado.
     * @throws Exception si ocurre un error al crear la dosis.
     */
    public DosisDTO addDosis(Connection c, DosisDTO dosis) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "INSERT INTO dosis (plazo_siguiente, num_dosis, tratamiento_id) VALUES (?, ?, ?)";
            logger.debug("Executing SQL: {}", sql);
            ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            DAOUtils.setParameters(ps, dosis.getPlazoSiguiente(), dosis.getNumDosis(), dosis.getTratamientoId());
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    dosis.setId(rs.getLong(1));
                    logger.info("Dosis creada con id: {}", dosis.getId());
                }
            }
            return dosis;
        } catch (Exception e) {
            logger.error("Error creando dosis para tratamiento_id: {}, Message: {}", dosis.getTratamientoId(), e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(rs, ps);
        }
    }

    /**
     * Actualiza una dosis existente.
     * @param c conexion activa con la base de datos.
     * @param dosis datos de la dosis a actualizar; debe incluir el id.
     * @return dosis actualizada si se modifico una fila, o null si no se encontro.
     * @throws Exception si ocurre un error al actualizar la dosis.
     */
    public DosisDTO updateDosis(Connection c, DosisDTO dosis) throws Exception {
        PreparedStatement ps = null;
        try {
            String sql = "UPDATE dosis SET plazo_siguiente = ?, num_dosis = ?, tratamiento_id = ? WHERE id = ?";
            logger.info("Actualizando dosis con id: {}", dosis.getId());
            logger.debug("Executing SQL: {}", sql);
            ps = c.prepareStatement(sql);
            DAOUtils.setParameters(ps, dosis.getPlazoSiguiente(), dosis.getNumDosis(), dosis.getTratamientoId(), dosis.getId());
            int updatedRows = ps.executeUpdate();
            logger.info("Dosis con id: {} actualizada, filas afectadas: {}", dosis.getId(), updatedRows);
            return updatedRows == 1 ? dosis : null;
        } catch (Exception e) {
            logger.error("Error actualizando dosis con id: {}, Message: {}", dosis.getId(), e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(null, ps);
        }
    }

    /**
     * Elimina una dosis por su id.
     * @param c conexion activa con la base de datos.
     * @param id identificador de la dosis a eliminar.
     * @return true si se elimino una fila, false si no se encontro la dosis.
     * @throws Exception si ocurre un error al eliminar la dosis.
     */
    public boolean deleteDosis(Connection c, Long id) throws Exception {
        PreparedStatement ps = null;
        try {
            String sql = "DELETE FROM dosis WHERE id = ?";
            logger.info("Eliminando dosis con id: {}", id);
            logger.debug("Executing SQL: {}", sql);
            ps = c.prepareStatement(sql);
            DAOUtils.setParameters(ps, id);
            int deleted = ps.executeUpdate();
            logger.info("Dosis con id: {} eliminada, filas afectadas: {}", id, deleted);
            return deleted == 1;
        } catch (Exception e) {
            logger.error("Error eliminando dosis con id: {}, Message: {}", id, e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(null, ps);
        }
    }

  
    private Tratamiento loadNext(ResultSet rs) throws Exception {
        int i = 1;
        Tratamiento tratamiento = new Tratamiento();
        tratamiento.setId(rs.getLong(i++));
        tratamiento.setNombre(rs.getString(i++));
        return tratamiento;
    }

  
    private DosisDTO loadNextDosis(ResultSet rs) throws Exception {
        int i = 1;
        DosisDTO dosis = new DosisDTO();
        dosis.setId(rs.getLong(i++));
        dosis.setPlazoSiguiente(rs.getInt(i++));
        dosis.setNumDosis(rs.getInt(i++));
        dosis.setTratamientoId(rs.getLong(i++));
        return dosis;
    }
}