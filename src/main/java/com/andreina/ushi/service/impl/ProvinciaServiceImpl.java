package com.andreina.ushi.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.andreina.ushi.dao.ProvinciaDAO;
import com.andreina.ushi.exception.ServiceException;
import com.andreina.ushi.model.Provincia;
import com.andreina.ushi.service.ProvinciaService;
import com.andreina.ushi.utils.JDBCUtils;

public class ProvinciaServiceImpl implements ProvinciaService {

    private static final Logger logger = LogManager.getLogger(ProvinciaServiceImpl.class.getName());

    private final ProvinciaDAO dao;

    public ProvinciaServiceImpl() {
        dao = new ProvinciaDAO();
    }

    @Override
    public List<Provincia> findAll() throws ServiceException {
        Connection c = null;
        try {
            c = JDBCUtils.getConnection();
            return dao.findAll(c);
        } catch (Exception e) {
            logger.error("Listando provincias: {}", e.getMessage(), e);
            throw new ServiceException(e);
        } finally {
            JDBCUtils.close(c, true);
        }
    }
}
