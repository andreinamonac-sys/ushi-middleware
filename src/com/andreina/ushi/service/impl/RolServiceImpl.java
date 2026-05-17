package com.andreina.ushi.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.andreina.ushi.dao.RolDAO;
import com.andreina.ushi.model.Rol;
import com.andreina.ushi.service.RolService;
import com.andreina.ushi.utils.JDBCUtils;

public class RolServiceImpl implements RolService {

    private static Logger logger = LogManager.getLogger(RolServiceImpl.class.getName());

    private RolDAO dao = null;

    public RolServiceImpl() {
        dao = new RolDAO();
    }

    @Override
    public List<Rol> findAll() throws Exception {
        Connection c = null;
        try {
            c = JDBCUtils.getConnection();
            return dao.findAll(c);
        } catch (Exception e) {
            logger.error("Listando roles: {}", e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(c, true);
        }
    }
}