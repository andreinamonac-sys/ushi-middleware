package com.andreina.ushi.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.andreina.ushi.dao.TipoEventoDAO;
import com.andreina.ushi.exception.ServiceException;
import com.andreina.ushi.model.TipoEvento;
import com.andreina.ushi.service.TipoEventoService;
import com.andreina.ushi.utils.JDBCUtils;

public class TipoEventoServiceImpl implements TipoEventoService{
	
	private static Logger logger = LogManager.getLogger(GranjaServiceImpl.class.getName());

    private TipoEventoDAO dao = null;

    public TipoEventoServiceImpl (){
        dao = new TipoEventoDAO();
    }

	@Override
    public List<TipoEvento> findAll() throws ServiceException {
        Connection c = null;
        try {
            c = JDBCUtils.getConnection();
            return dao.findAll(c);
        } catch (Exception e) {
            logger.error("Listando tipos de eventos: {}", e.getMessage(), e);
            throw new ServiceException(e);
        } finally {
            JDBCUtils.close(c, true);
        }
    }
}
