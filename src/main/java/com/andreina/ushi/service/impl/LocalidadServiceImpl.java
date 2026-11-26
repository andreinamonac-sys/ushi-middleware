package com.andreina.ushi.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.andreina.ushi.dao.LocalidadDAO;
import com.andreina.ushi.exception.ServiceException;
import com.andreina.ushi.model.LocalidadDTO;
import com.andreina.ushi.service.LocalidadService;
import com.andreina.ushi.utils.JDBCUtils;

public class LocalidadServiceImpl implements LocalidadService {

	private static final Logger logger = LogManager.getLogger(LocalidadServiceImpl.class.getName());

	private final LocalidadDAO dao;

	public LocalidadServiceImpl() {
		dao = new LocalidadDAO();
	}

	@Override
	public List<LocalidadDTO> findAll() throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			List<LocalidadDTO> results = dao.findAll(c);
			commit = true;
			return results;
		} catch (Exception e) {
			logger.error("Listando localidades: {}", e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	@Override
	public List<LocalidadDTO> findLocalidadByProvincia(Long idProvincia) throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			List<LocalidadDTO> results = dao.findLocalidadByProvincia(c, idProvincia);
			commit = true;
			return results;
		} catch (Exception e) {
			logger.error("Listando localidades por provincia: {}", idProvincia, e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}
	}
}
