package com.andreina.ushi.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.andreina.ushi.dao.AlertaDAO;
import com.andreina.ushi.exception.ServiceException;
import com.andreina.ushi.model.AlertaDTO;
import com.andreina.ushi.service.AlertaService;
import com.andreina.ushi.utils.JDBCUtils;

public class AlertaServiceImpl implements AlertaService {

    private static final Logger logger = LogManager.getLogger(AlertaServiceImpl.class.getName());
    private final AlertaDAO dao;

    public AlertaServiceImpl() {
        this.dao = new AlertaDAO();
    }

    @Override
    public List<AlertaDTO> findUltimas(int limit) throws ServiceException {
        Connection c = null;
        try {
            c = JDBCUtils.getConnection();
            return dao.findUltimas(c, limit);
        } catch (Exception e) {
            logger.error("Buscando alertas: {}", e.getMessage(), e);
            throw new ServiceException(e);
        } finally {
            JDBCUtils.close(c, true);
        }
    }

    @Override
    public boolean marcarLeida(Long alertaId) throws ServiceException {
        Connection c = null;
        boolean commit = false;
        try {
            c = JDBCUtils.getConnection();
            c.setAutoCommit(false);
            boolean result = dao.marcarLeida(c, alertaId);
            commit = true;
            return result;
        } catch (Exception e) {
            logger.error("Marcando alerta como leida: {}", e.getMessage(), e);
            throw new ServiceException(e);
        } finally {
            JDBCUtils.close(c, commit);
        }
    }
}
