package com.andreina.ushi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.andreina.ushi.dao.criteria.EventoCriteria;
import com.andreina.ushi.model.EventoDTO;
import com.andreina.ushi.model.Results;
import com.andreina.ushi.utils.DAOUtils;
import com.andreina.ushi.utils.JDBCUtils;
import com.andreina.ushi.utils.SQLUtils;

public class EventoDAO {

    private static final Logger logger = LogManager.getLogger(EventoDAO.class.getName());

    private static final String BASE_QUERY =
            "SELECT e.id, e.fecha_hora, e.descripcion, e.tipo_evento_id, "
            + "te.nombre AS tipo_evento_nombre, "
            + "e.animal_id, a.num_registro AS animal_num_registro, "
            + "e.cria_id, e.veterinario_id, "
            + "v.nombre AS veterinario_nombre, v.apellido1 AS veterinario_apellido, "
            + "e.semilla_id, e.dosis_id, d.num_dosis, e.valor_diagnostico "
            + "FROM evento e "
            + "INNER JOIN tipo_evento te ON te.id = e.tipo_evento_id "
            + "INNER JOIN animal a ON a.id = e.animal_id "
            + "LEFT JOIN usuario v ON v.id = e.veterinario_id "
            + "LEFT JOIN dosis d ON d.id = e.dosis_id ";

    private static final String ORDER_BY = " ORDER BY e.id DESC";
    private static final String PAGINATION = " LIMIT ? OFFSET ?";

    public EventoDAO() {
    }
    /**
	 * Crea un nuevo evento en la base de datos.
	 * @param c conexión a la base de datos.
	 * @param evento datos del evento a crear.
	 * @return el evento creado con su id generado.
	 * @throws Exception si ocurre un error durante la creación.
	 */
    public EventoDTO create(Connection c, EventoDTO evento) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "INSERT INTO evento (fecha_hora, descripcion, tipo_evento_id, animal_id, "
                    + "cria_id, veterinario_id, semilla_id, dosis_id, valor_diagnostico) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            logger.debug("Executing SQL: {}", sql);
            ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            DAOUtils.setParameters(ps,
                    DAOUtils.toTimestamp(evento.getFechaDesde()),DAOUtils.toTimestamp(evento.getFechaHasta()), 
					                    evento.getDescripcion(), evento.getTipoEventoId(),
					                    evento.getAnimalId(), evento.getCriaId(), evento.getVeterinarioId(), 
					                    evento.getSemillaId(),evento.getDosisId(), evento.getValorDiagnostico());
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    evento.setId(rs.getLong(1));
                    logger.info("Evento creado con id: {}", evento.getId());
                }
            }
            return evento;
        } catch (Exception e) {
            logger.error("Error creando evento para animal_id: {}, Message: {}", evento.getAnimalId(), e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(rs, ps);
        }
    }
    /**
     * Actualiza un evento existente en la base de datos.
     * @param c
     * @param evento
     * @return
     * @throws Exception
     */
    public boolean update(Connection c, EventoDTO evento) throws Exception {
        PreparedStatement ps = null;
        try {
            String sql = "UPDATE evento SET fecha_hora = ?, descripcion = ?, tipo_evento_id = ?, animal_id = ?, "
                    + "cria_id = ?, veterinario_id = ?, semilla_id = ?, dosis_id = ?, valor_diagnostico = ? "
                    + "WHERE id = ?";
            logger.info("Actualizando evento con id: {}", evento.getId());
            logger.debug("Executing SQL: {}", sql);
            ps = c.prepareStatement(sql);
            DAOUtils.setParameters(ps,
                    DAOUtils.toTimestamp(evento.getFechaDesde()),DAOUtils.toTimestamp(evento.getFechaHasta()),
					                    evento.getDescripcion(), evento.getTipoEventoId(),
					                    evento.getAnimalId(), evento.getCriaId(), evento.getVeterinarioId(), 
					                    evento.getSemillaId(),
					                    evento.getDosisId(), evento.getValorDiagnostico(), evento.getId());
            int updatedRows = ps.executeUpdate();
            logger.info("Evento con id: {} actualizado, filas afectadas: {}", evento.getId(), updatedRows);
            return updatedRows == 1;
        } catch (Exception e) {
            logger.error("Error actualizando evento con id: {}, Message: {}", evento.getId(), e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(null, ps);
        }
    }
    /**
	 * Anula un evento estableciendo su campo 'anulado' a 1.
	 * @param c conexión a la base de datos.
	 * @param id id del evento a anular.
	 * @return true si el evento fue anulado, false si no se encontró o ya estaba anulado.
	 * @throws Exception si ocurre un error durante la operación.
	 */
    public boolean anularEvento(Connection c, Long id) throws Exception {
        PreparedStatement ps = null;
        try {
            String sql = "UPDATE evento SET anulado = 1 WHERE id = ?";
            logger.info("Anulando evento con id: {}", id);
            logger.debug("Executing SQL: {}", sql);
            ps = c.prepareStatement(sql);
            DAOUtils.setParameters(ps, id);
            int updatedRows = ps.executeUpdate();
            logger.info("Evento con id: {} anulado, filas afectadas: {}", id, updatedRows);
            return updatedRows == 1;
        } catch (Exception e) {
            logger.error("Error anulando evento con id: {}, Message: {}", id, e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(null, ps);
        }
    }
    /**
	 * Elimina un evento de la base de datos. En este caso, se implementa como una anulación lógica.
	 * @param c conexión a la base de datos.
	 * @param id id del evento a eliminar.
	 * @return true si el evento fue eliminado (anulado), false si no se encontró o ya estaba anulado.
	 * @throws Exception si ocurre un error durante la operación.
	 */
    public boolean delete(Connection c, Long id) throws Exception {
        return anularEvento(c, id);
    }
    /**
     * Busca un evento por su id.
     * @param c
     * @param id
     * @return
     * @throws Exception
     */
    public EventoDTO findById(Connection c, Long id) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = BASE_QUERY + " WHERE e.id = ?";
            logger.info("Buscando evento con id: {}", id);
            logger.debug("Executing SQL: {}", sql);
            ps = c.prepareStatement(sql);
            DAOUtils.setParameters(ps, id);
            rs = ps.executeQuery();
            return rs.next() ? loadNext(rs) : null;
        } catch (Exception e) {
            logger.error("Error encontrando evento con id: {}, Message: {}", id, e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(rs, ps);
        }
    }
    
    /**
     * Busca eventos por su tipo de evento.
     * @param c
     * @param tipoEventoId
     * @return
     * @throws Exception
     */
    public List<EventoDTO> findByTipoEventoId(Connection c, Long tipoEventoId) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = BASE_QUERY + " WHERE e.tipo_evento_id = ?" + ORDER_BY;
            logger.info("Buscando eventos con tipo_evento_id: {}", tipoEventoId);
            logger.debug("Executing SQL: {}", sql);
            ps = c.prepareStatement(sql);
            DAOUtils.setParameters(ps, tipoEventoId);
            rs = ps.executeQuery();
            List<EventoDTO> eventos = new ArrayList<>();
            while (rs.next()) {
                eventos.add(loadNext(rs));
            }
            return eventos;
        } catch (Exception e) {
            logger.error("Error encontrando eventos con tipo_evento_id: {}, Message: {}", tipoEventoId, e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(rs, ps);
        }
    }
    /**
	 * Busca eventos por su animal asociado.
	 * @param c
	 * @param animalId
	 * @return
	 * @throws Exception
	 */
    public List<EventoDTO> findByAnimalId(Connection c, Long animalId) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = BASE_QUERY + " WHERE e.animal_id = ?" + ORDER_BY;
            logger.info("Buscando eventos con animal_id: {}", animalId);
            logger.debug("Executing SQL: {}", sql);
            ps = c.prepareStatement(sql);
            DAOUtils.setParameters(ps, animalId);
            rs = ps.executeQuery();
            List<EventoDTO> eventos = new ArrayList<>();
            while (rs.next()) {
                eventos.add(loadNext(rs));
            }
            return eventos;
        } catch (Exception e) {
            logger.error("Error encontrando eventos con animal_id: {}, Message: {}", animalId, e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(rs, ps);
        }
    }
    /**
	 * Busca eventos que coincidan con los criterios especificados, con paginación.
	 * @param c conexión a la base de datos.
	 * @param criteria criterios de búsqueda para filtrar eventos.
	 * @param from índice de la primera fila a retornar (1-based).
	 * @param pageSize número máximo de filas a retornar.
	 * @return resultados paginados que coinciden con los criterios.
	 * @throws Exception si ocurre un error durante la búsqueda.
	 */
    public Results<EventoDTO> findByCriteria(Connection c, EventoCriteria criteria, int from, int pageSize) throws Exception {
        Results<EventoDTO> results = new Results<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            StringBuilder sqlBuilder = new StringBuilder(BASE_QUERY);
            List<String> condiciones = new ArrayList<>();
            List<Object> parametros = new ArrayList<>();
            SQLUtils.addClause(criteria.getId(), condiciones, " e.id = ? ", 
            		parametros, criteria.getId());
            SQLUtils.addClause(criteria.getFechaDesde(), condiciones, " e.fecha_hora >= ? ", 
            		parametros, DAOUtils.toTimestamp(criteria.getFechaDesde()));
            SQLUtils.addClause(criteria.getFechaHasta(), condiciones, " e.fecha_hora <= ? ", 
            		parametros, DAOUtils.toTimestamp(criteria.getFechaHasta()));
            SQLUtils.addClause(criteria.getNumRegistro(), condiciones, " UPPER(a.num_registro) LIKE UPPER(?) ", 
            		parametros, "%" + criteria.getNumRegistro() + "%");
            SQLUtils.addClause(criteria.getDescripcion(), condiciones, " UPPER(e.descripcion) LIKE UPPER(?) ", 
            		parametros, "%" + criteria.getDescripcion() + "%");
            SQLUtils.addClause(criteria.getTipoEventoId(), condiciones, " e.tipo_evento_id = ? ", 
            		parametros, criteria.getTipoEventoId());
            SQLUtils.addClause(criteria.getAnimalId(), condiciones, " e.animal_id = ? ", 
            		parametros, criteria.getAnimalId());
            SQLUtils.addClause(criteria.getValorDiagnostico(), condiciones, " UPPER(e.valor_diagnostico) LIKE UPPER(?) ",
            		parametros, "%" + criteria.getValorDiagnostico() + "%");
            SQLUtils.addClause(criteria.getVeterinarioId(), condiciones, " e.veterinario_id = ? ", 
            		parametros, criteria.getVeterinarioId());
            if (!condiciones.isEmpty()) {
                sqlBuilder.append(" WHERE ").append(String.join(" AND ", condiciones));
            }
            sqlBuilder.append(ORDER_BY);
            sqlBuilder.append(PAGINATION);
            String sql = sqlBuilder.toString();
            logger.debug("Executing SQL: {}", sql);
            logger.debug("Parameters: {}", parametros);
            ps = c.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            DAOUtils.setParameters(ps, parametros);
            rs = ps.executeQuery();
            List<EventoDTO> eventos = new ArrayList<>();
            if (from >= 1 && rs.absolute(from)) {
                int count = 0;
                do {
                    eventos.add(loadNext(rs));
                    ++count;
                } while (count < pageSize && rs.next());
            }
            results.setPageResults(eventos);
            results.setTotal(SQLUtils.getTotalRows(rs));
            return results;
        } catch (Exception e) {
            logger.error("Criteria: {}, Message: {}", criteria, e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(rs, ps);
        }
    }
    /**
	 * Carga un EventoDTO desde el ResultSet actual.
	 * @param rs ResultSet posicionado en la fila a cargar.
	 * @return EventoDTO con los datos de la fila actual.
	 * @throws Exception si ocurre un error al leer el ResultSet.
	 */
    private EventoDTO loadNext(ResultSet rs) throws Exception {
        int i = 1;
        EventoDTO evento = new EventoDTO();
        evento.setId(rs.getLong(i++));
        evento.setFechaDesde(rs.getTimestamp(i++));
        evento.setDescripcion(rs.getString(i++));
        evento.setTipoEventoId(rs.getLong(i++));
        evento.setNombreTipoEvento(rs.getString(i++));
        evento.setAnimalId(rs.getLong(i++));
        evento.setAnimalNumRegistro(rs.getString(i++));
        evento.setCriaId(DAOUtils.getLong(rs, i++));
        evento.setVeterinarioId(DAOUtils.getLong(rs, i++));
        evento.setNombreVeterinario(rs.getString(i++));
        evento.setApellidoVeterinario(rs.getString(i++));
        evento.setSemillaId(DAOUtils.getLong(rs, i++));
        evento.setDosisId(DAOUtils.getLong(rs, i++));
        evento.setNumDosis(DAOUtils.getLong(rs, i++));
        evento.setValorDiagnostico(rs.getString(i++));
        return evento;
    }

    
}
