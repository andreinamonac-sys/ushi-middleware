package com.andreina.ushi.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.andreina.ushi.dao.ParametroDAO;
import com.andreina.ushi.dao.criteria.ParametroCriteria;
import com.andreina.ushi.exception.ServiceException;
import com.andreina.ushi.model.Parametro;
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
    public Long create(Parametro parametro) throws ServiceException {
        Connection c = null;
        boolean commit = false;
        try {
            c = JDBCUtils.getConnection();
            c.setAutoCommit(false);
            Parametro result = dao.create(c, parametro);
            commit = true;
            return result != null ? result.getId() : null;
        } catch (Exception e) {
            logger.error("Creando parámetro{}: {}", parametro, e.getMessage(), e);
            throw new ServiceException(e);
        } finally {
            JDBCUtils.close(c, commit);
        }
    }

    @Override
    public List<ParametroDTO> findByCriteria(ParametroCriteria criteria, int from, int pageSize) throws ServiceException {
    	 Connection c = null;
         boolean commit = false;
         try {
             c = JDBCUtils.getConnection();
             c.setAutoCommit(false);
             List<ParametroDTO> results = dao.findByCriteria(c, criteria, from, pageSize);
             commit = true;
            return results;
        } catch (Exception e) {
            logger.error("Buscando parámetros{}: {}", criteria, e.getMessage(), e);
            throw new ServiceException(e);
        } finally {
            JDBCUtils.close(c, commit);
        }
    }
}