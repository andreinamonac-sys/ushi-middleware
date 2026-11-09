package com.andreina.ushi.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.andreina.ushi.dao.GranjaDAO;
import com.andreina.ushi.dao.criteria.GranjaCriteria;
import com.andreina.ushi.exception.ServiceException;
import com.andreina.ushi.model.Granja;
import com.andreina.ushi.model.GranjaDTO;
import com.andreina.ushi.service.GranjaService;
import com.andreina.ushi.utils.JDBCUtils;

public class GranjaServiceImpl implements GranjaService {

    private static Logger logger = LogManager.getLogger(GranjaServiceImpl.class.getName());

    private GranjaDAO dao = null;

    public GranjaServiceImpl() {
        dao = new GranjaDAO();
    }

    @Override
    public Granja create(Granja granja) throws ServiceException {
        Connection c = null;
        boolean commit = false;
        try {
            c = JDBCUtils.getConnection();
            c.setAutoCommit(false);
            Granja result = dao.create(c, granja);
            commit = true;
            return result != null ? result : null;
        } catch (Exception e) {
            logger.error("Creando {}: {}", granja, e.getMessage(), e);
            throw new ServiceException(e);
        } finally {
            JDBCUtils.close(c, commit);
        }
    }

    @Override
    public GranjaDTO update(GranjaDTO granja) throws ServiceException{
        if (granja.getId() == null || granja.getId() <= 0) return null;
        Connection c = null;
        boolean commit = false;
        try {
            c = JDBCUtils.getConnection();
            c.setAutoCommit(false);
            GranjaDTO result = dao.update(c, granja);
            commit = true;
            return result;
        } catch (Exception e) {
            logger.error("Actualizando {}: {}", granja, e.getMessage(), e);
            throw new ServiceException(e);
        } finally {
            JDBCUtils.close(c, commit);
        }
    }

    @Override
    public boolean delete(Long id) throws ServiceException {
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
            logger.error("Eliminando granja {}: {}", id, e.getMessage(), e);
            throw new ServiceException(e);
        } finally {
            JDBCUtils.close(c, commit);
        }
    }

    @Override
    public GranjaDTO findById(Long id) throws ServiceException {
        if (id == null || id <= 0) return null;
        Connection c = null;
        try {
            c = JDBCUtils.getConnection();
            return dao.findById(c, id);
        } catch (Exception e) {
            logger.error("Buscando granja por id {}: {}", id, e.getMessage(), e);
            throw new ServiceException(e);
        } finally {
            JDBCUtils.close(c, true);
        }
    }

    @Override
    public GranjaDTO findByNif(String nif) throws ServiceException {
        if (nif == null) return null;
        Connection c = null;
        try {
            c = JDBCUtils.getConnection();
            return dao.findByNif(c, nif);
        } catch (Exception e) {
            logger.error("Buscando granja por nif {}: {}", nif, e.getMessage(), e);
            throw new ServiceException(e);
        } finally {
            JDBCUtils.close(c, true);
        }
    }

    @Override
    public List<GranjaDTO> findByEncargadoId(Long usuarioId) throws ServiceException {
        if (usuarioId == null || usuarioId <= 0) return null;
        Connection c = null;
        try {
            c = JDBCUtils.getConnection();
            return dao.findByEncargadoId(c, usuarioId);
        } catch (Exception e) {
            logger.error("Buscando granjas por encargado {}: {}", usuarioId, e.getMessage(), e);
            throw new ServiceException(e);
        } finally {
            JDBCUtils.close(c, true);
        }
    }

    @Override
    public List<GranjaDTO> findAll() throws ServiceException {
        Connection c = null;
        try {
            c = JDBCUtils.getConnection();
            return dao.findAll(c);
        } catch (Exception e) {
            logger.error("Listando granjas: {}", e.getMessage(), e);
            throw new ServiceException(e);
        } finally {
            JDBCUtils.close(c, true);
        }
    }

    @Override
    public List<GranjaDTO> findByCriteria(GranjaCriteria criteria) throws ServiceException {
        Connection c = null;
        try {
            c = JDBCUtils.getConnection();
            return dao.findByCriteria(c, criteria == null ? new GranjaCriteria() : criteria);
        } catch (Exception e) {
            logger.error("Buscando granjas por criterios: {}", e.getMessage(), e);
            throw new ServiceException(e);
        } finally {
            JDBCUtils.close(c, true);
        }
    }
}
