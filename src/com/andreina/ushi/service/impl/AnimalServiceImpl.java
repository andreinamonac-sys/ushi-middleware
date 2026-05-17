package com.andreina.ushi.service.impl;

import java.sql.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.andreina.ushi.dao.AnimalDAO;
import com.andreina.ushi.dao.criteria.AnimalCriteria;
import com.andreina.ushi.model.AnimalDTO;
import com.andreina.ushi.model.Results;
import com.andreina.ushi.service.AnimalService;
import com.andreina.ushi.utils.JDBCUtils;

public class AnimalServiceImpl implements AnimalService {

    private static Logger logger =
            LogManager.getLogger(AnimalServiceImpl.class.getName());

    private AnimalDAO dao = null;

    public AnimalServiceImpl() {
        dao = new AnimalDAO();
    }

    @Override
    public AnimalDTO create(AnimalDTO animal) throws Exception {
        Connection c = null;
        boolean commit = false;
        try {
            c = JDBCUtils.getConnection();
            c.setAutoCommit(false);
            AnimalDTO result = dao.create(c, animal);
            commit = true;
            return result;
        } catch (Exception e) {
            logger.error("Creando {}: {}", animal, e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(c, commit);
        }
    }

    @Override
    public boolean update(AnimalDTO animal) throws Exception {
        if (animal.getId() == null || animal.getId() <= 0) return false;
        Connection c = null;
        boolean commit = false;
        try {
            c = JDBCUtils.getConnection();
            c.setAutoCommit(false);
            boolean updated = dao.update(c, animal);
            commit = true;
            return updated;
        } catch (Exception e) {
            logger.error("Actualizando {}: {}", animal, e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(c, commit);
        }
    }

    @Override
    public boolean delete(Long id) throws Exception {
        if (id == null || id <= 0) return false;
        Connection c = null;
        boolean commit = false;
        try {
            c = JDBCUtils.getConnection();
            c.setAutoCommit(false);
            boolean deleted = dao.delete(c, id);
            commit = true;
            return deleted;
        } catch (Exception e) {
            logger.error("Eliminando animal {}: {}", id, e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(c, commit);
        }
    }

    @Override
    public AnimalDTO findById(Long id) throws Exception {
    	if (id == null || id <= 0) return null;
        Connection c = null;
        try {
            c = JDBCUtils.getConnection();
            return dao.findById(c, id);
        } catch (Exception e) {
            logger.error("Buscando animal por id {}: {}", id, e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(c, true);
        }
    }

    @Override
    public AnimalDTO findByNumRegistro(String numRegistro) throws Exception {
    	if (numRegistro == null) return null;
        Connection c = null;
        try {
            c = JDBCUtils.getConnection();
            return dao.findByNumRegistro(c, numRegistro);
        } catch (Exception e) {
            logger.error("Buscando animal por numRegistro {}: {}", numRegistro, e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(c, true);
        }
    }

    @Override
    public Results<AnimalDTO> findByCriteria(AnimalCriteria criteria,
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
}