package com.andreina.ushi.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.andreina.ushi.dao.TagDAO;
import com.andreina.ushi.model.Tag;
import com.andreina.ushi.service.TagService;
import com.andreina.ushi.utils.JDBCUtils;

public class TagServiceImpl implements TagService {

    private static Logger logger = LogManager.getLogger(TagServiceImpl.class.getName());

    private TagDAO dao = null;

    public TagServiceImpl() {
        dao = new TagDAO();
    }

    @Override
    public Long create(Tag tag) throws Exception {
        Connection c = null;
        boolean commit = false;
        try {
            c = JDBCUtils.getConnection();
            c.setAutoCommit(false);
            Tag result = dao.create(c, tag);
            commit = true;
            return result != null ? result.getId() : null;
        } catch (Exception e) {
            logger.error("Creando {}: {}", tag, e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(c, commit);
        }
    }

    @Override
    public boolean update(Tag tag) throws Exception {
        if (tag.getId() == null || tag.getId() <= 0) return false;
        Connection c = null;
        boolean commit = false;
        try {
            c = JDBCUtils.getConnection();
            c.setAutoCommit(false);
            boolean result = dao.update(c, tag);
            commit = true;
            return result;
        } catch (Exception e) {
            logger.error("Actualizando {}: {}", tag, e.getMessage(), e);
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
            logger.error("Eliminando tag {}: {}", id, e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(c, commit);
        }
    }

    @Override
    public Tag findById(Long id) throws Exception {
        if (id == null || id <= 0) return null;
        Connection c = null;
        try {
            c = JDBCUtils.getConnection();
            return dao.findById(c, id);
        } catch (Exception e) {
            logger.error("Buscando tag por id {}: {}", id, e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(c, true);
        }
    }

    @Override
    public Tag findByNumero(String numero) throws Exception {
        if (numero == null) return null;
        Connection c = null;
        try {
            c = JDBCUtils.getConnection();
            return dao.findByNumero(c, numero);
        } catch (Exception e) {
            logger.error("Buscando tag por numero {}: {}", numero, e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(c, true);
        }
    }

    @Override
    public List<Tag> findByAnimalId(Long animalId) throws Exception {
        if (animalId == null || animalId <= 0) return null;
        Connection c = null;
        try {
            c = JDBCUtils.getConnection();
            return dao.findByAnimalId(c, animalId);
        } catch (Exception e) {
            logger.error("Buscando tags por animal {}: {}", animalId, e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(c, true);
        }
    }

    @Override
    public List<Tag> findConIncidencias() throws Exception {
        Connection c = null;
        try {
            c = JDBCUtils.getConnection();
            return dao.findConIncidencias(c);
        } catch (Exception e) {
            logger.error("Buscando tags con incidencias: {}", e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(c, true);
        }
    }

    @Override
    public List<Tag> findDisponible() throws Exception {
        Connection c = null;
        try {
            c = JDBCUtils.getConnection();
            return dao.findDisponible(c);
        } catch (Exception e) {
            logger.error("Buscando tags disponibles: {}", e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(c, true);
        }
    }
}