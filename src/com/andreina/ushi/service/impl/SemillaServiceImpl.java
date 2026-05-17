package com.andreina.ushi.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.andreina.ushi.dao.SemillaDAO;
import com.andreina.ushi.model.Semilla;
import com.andreina.ushi.service.SemillaService;
import com.andreina.ushi.utils.JDBCUtils;

public class SemillaServiceImpl implements SemillaService {

    private static Logger logger = LogManager.getLogger(SemillaServiceImpl.class.getName());

    private SemillaDAO dao = null;

    public SemillaServiceImpl() {
        dao = new SemillaDAO();
    }

    @Override
    public Long create(Semilla semilla) throws Exception {
        Connection c = null;
        boolean commit = false;
        try {
            c = JDBCUtils.getConnection();
            c.setAutoCommit(false);
            Semilla result = dao.create(c, semilla);
            commit = true;
            return result != null ? result.getId() : null;
        } catch (Exception e) {
            logger.error("Creando {}: {}", semilla, e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(c, commit);
        }
    }

    @Override
    public boolean update(Semilla semilla) throws Exception {
        if (semilla.getId() == null || semilla.getId() <= 0) return false;
        Connection c = null;
        boolean commit = false;
        try {
            c = JDBCUtils.getConnection();
            c.setAutoCommit(false);
            boolean updated = dao.update(c, semilla);
            commit = true;
            return updated;
        } catch (Exception e) {
            logger.error("Actualizando {}: {}", semilla, e.getMessage(), e);
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
            logger.error("Eliminando semilla {}: {}", id, e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(c, commit);
        }
    }

    @Override
    public List<Semilla> findByMeritoNetoGreaterThan(Integer minimo) throws Exception {
        Connection c = null;
        try {
            c = JDBCUtils.getConnection();
            return dao.findByMeritoNetoGreaterThan(c, minimo);
        } catch (Exception e) {
            logger.error("Buscando semillas con merito neto mayor que {}: {}", minimo, e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(c, true);
        }
    }

    @Override
    public List<Semilla> findTopByItp(Integer limite) throws Exception {
        Connection c = null;
        try {
            c = JDBCUtils.getConnection();
            return dao.findTopByItp(c, limite);
        } catch (Exception e) {
            logger.error("Buscando top semillas por ITP con limite {}: {}", limite, e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(c, true);
        }
    }
}