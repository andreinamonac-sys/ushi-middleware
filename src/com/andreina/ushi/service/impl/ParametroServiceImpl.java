package com.andreina.ushi.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.andreina.ushi.dao.ParametroDAO;
import com.andreina.ushi.dao.criteria.ParametroCriteria;
import com.andreina.ushi.model.ParametroDTO;
import com.andreina.ushi.service.ParametroService;
import com.andreina.ushi.utils.JDBCUtils;

public class ParametroServiceImpl implements ParametroService {

    private static Logger logger = LogManager.getLogger(ParametroServiceImpl.class.getName());

    private ParametroDAO dao = null;

    public ParametroServiceImpl() {
        dao = new ParametroDAO();
    }

    @Override
    public Long create(ParametroDTO parametro) throws Exception {
        Connection c = null;
        boolean commit = false;
        try {
            c = JDBCUtils.getConnection();
            c.setAutoCommit(false);
            ParametroDTO result = dao.create(c, parametro);
            commit = true;
            return result != null ? result.getId() : null;
        } catch (Exception e) {
            logger.error("Creando {}: {}", parametro, e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(c, commit);
        }
    }

    @Override
    public List<ParametroDTO> findByCriteria(ParametroCriteria criteria, int from, int pageSize) throws Exception {
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