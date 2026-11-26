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
import com.andreina.ushi.model.UsuarioDTO;
import com.andreina.ushi.service.GranjaService;
import com.andreina.ushi.utils.JDBCUtils;
import com.andreina.ushi.utils.RolIds;


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
        boolean commit = false;
        try {
            c = JDBCUtils.getConnection();
            c.setAutoCommit(false);
            GranjaDTO granja = dao.findById(c, id);
            commit = true;
            return granja;
        } catch (Exception e) {
            logger.error("Buscando granja por id {}: {}", id, e.getMessage(), e);
            throw new ServiceException(e);
        } finally {
            JDBCUtils.close(c, commit);
        }
    }

    @Override
    public GranjaDTO findByRega(String rega) throws ServiceException {
        if (rega == null) return null;
        Connection c = null;
        boolean commit = false;
        try {
            c = JDBCUtils.getConnection();
            c.setAutoCommit(false);
            GranjaDTO granja = dao.findByRega(c, rega);
            commit = true;
            return granja;
        } catch (Exception e) {
            logger.error("Buscando granja por Código REGA {}: {}", rega, e.getMessage(), e);
            throw new ServiceException(e);
        } finally {
            JDBCUtils.close(c, commit);
        }
    }

    @Override
    public List<GranjaDTO> findByEncargadoId(Long encargadoId) throws ServiceException {
        if (encargadoId == null || encargadoId <= 0) return null;
        Connection c = null;
        boolean commit = false;
        try {
            c = JDBCUtils.getConnection();
            c.setAutoCommit(false);
            List<GranjaDTO> results = dao.findByEncargadoId(c, encargadoId);
            commit = true;
            return results;
        } catch (Exception e) {
            logger.error("Buscando granjas por encargado {}: {}", encargadoId, e.getMessage(), e);
            throw new ServiceException(e);
        } finally {
            JDBCUtils.close(c, commit);
        }
    }

    
    @Override
    public boolean tieneAccesoAGranja(UsuarioDTO usuario, Long granjaId) throws ServiceException {
        if (usuario == null || usuario.getId() == null
                || usuario.getRolId() == null || granjaId == null) {
            return false;
        }

        Long usuarioId = usuario.getId();
        Long rolId = usuario.getRolId();
        Connection c = null;
        boolean commit = false;
        try {
            c = JDBCUtils.getConnection();
            c.setAutoCommit(false);

            if (RolIds.ADMINISTRADOR.equals(rolId)) {
                return dao.findById(c, granjaId) != null;
            }

            if (RolIds.ENCARGADO.equals(rolId)) {
                GranjaDTO granja = dao.findById(c, granjaId);
                return granja != null && usuarioId.equals(granja.getUsuarioId());
            }

            if (RolIds.VETERINARIO.equals(rolId) || RolIds.OPERARIO.equals(rolId)) {
                return dao.existeAsignacion(c, usuarioId, granjaId);
            }
            commit = true;
            return false;
        } catch (Exception e) {
            logger.error("Error al comprobar el acceso del usuario {} a la granja {}",
                    usuarioId, granjaId, e);
            throw new ServiceException(e);
        } finally {
            JDBCUtils.close(c,commit);
        }
    }
    @Override
    public List<GranjaDTO> findAll() throws ServiceException {
    	 Connection c = null;
         boolean commit = false;
         try {
             c = JDBCUtils.getConnection();
             c.setAutoCommit(false);
             List<GranjaDTO> results = dao.findAll(c); 
             commit = true;
            return results;
        } catch (Exception e) {
            logger.error("Listando granjas: {}", e.getMessage(), e);
            throw new ServiceException(e);
        } finally {
            JDBCUtils.close(c, commit);
        }
    }

    @Override
    public List<GranjaDTO> findByCriteria(GranjaCriteria criteria) throws ServiceException {
    	 Connection c = null;
         boolean commit = false;
         try {
             c = JDBCUtils.getConnection();
             c.setAutoCommit(false);
             List<GranjaDTO> results = dao.findByCriteria(c, criteria == null ? new GranjaCriteria() : criteria);
             commit = true;
            return results;
        } catch (Exception e) {
            logger.error("Buscando granjas por criterios: {}", e.getMessage(), e);
            throw new ServiceException(e);
        } finally {
            JDBCUtils.close(c,  commit);
        }
    }
}
