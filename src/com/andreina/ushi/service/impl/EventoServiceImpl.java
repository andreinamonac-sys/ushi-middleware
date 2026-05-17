package com.andreina.ushi.service.impl;

import java.sql.Connection;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.andreina.ushi.dao.EventoDAO;
import com.andreina.ushi.dao.criteria.EventoCriteria;
import com.andreina.ushi.model.EventoDTO;
import com.andreina.ushi.model.Results;
import com.andreina.ushi.service.EventoService;
import com.andreina.ushi.utils.JDBCUtils;

public class EventoServiceImpl implements EventoService {

    private EventoDAO dao = null;
    private static Logger logger =
            LogManager.getLogger(EventoServiceImpl.class.getName());

    public EventoServiceImpl() {
        dao = new EventoDAO();
    }

    @Override
    public EventoDTO create(EventoDTO evento) throws Exception {
        Connection c = null;
        boolean commit = false;
        try {
            c = JDBCUtils.getConnection();
            c.setAutoCommit(false);
            EventoDTO result = dao.create(c, evento);
            commit = true;
            return result;
        } catch (Exception e) {
            logger.error("Creando {}: {}", evento, e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(c, commit);
        }
    }

    @Override
    public boolean anularEvento(Long id) throws Exception {
        Connection c = null;
        boolean commit = false;
        try {
            c = JDBCUtils.getConnection();
            c.setAutoCommit(false);
            boolean result = dao.anularEvento(c, id);
            commit = true;
            return result;
        } catch (Exception e) {
            logger.error("Anulando evento con id {}: {}", id, e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(c, commit);
        }
    }

    @Override
    public Results<EventoDTO> findByCriteria(EventoCriteria criteria,
                                              int from, int pageSize) throws Exception {
        Connection c = null;
        try {
            c = JDBCUtils.getConnection();
            return dao.findByCriteria(c, criteria, from, pageSize);
        } catch (Exception e) {
            logger.error("Buscando {}: {}", criteria, e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(c, true);
        }
    }

    @Override
    public List<EventoDTO> findByTipoEventoId(Long tipoEventoId) throws Exception {
        Connection c = null;
        try {
            c = JDBCUtils.getConnection();
            return dao.findByTipoEventoId(c, tipoEventoId);
        } catch (Exception e) {
            logger.error("Buscando eventos por tipo {}: {}", tipoEventoId, e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(c, true);
        }
    }

    @Override
    public List<EventoDTO> findByAnimalId(Long animalId) throws Exception {
        Connection c = null;
        try {
            c = JDBCUtils.getConnection();
            return dao.findByAnimalId(c, animalId);
        } catch (Exception e) {
            logger.error("Buscando eventos por animal_id {}: {}", animalId, e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(c, true);
        }
    }

    @Override
    public EventoDTO findById(Long id) throws Exception {
        Connection c = null;
        try {
            c = JDBCUtils.getConnection();
            return dao.findById(c, id);
        } catch (Exception e) {
            logger.error("Buscando evento por id {}: {}", id, e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(c, true);
        }
    }
}