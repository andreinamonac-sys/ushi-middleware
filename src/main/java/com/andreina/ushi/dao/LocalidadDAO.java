package com.andreina.ushi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.andreina.ushi.model.LocalidadDTO;
import com.andreina.ushi.utils.DAOUtils;
import com.andreina.ushi.utils.JDBCUtils;

public class LocalidadDAO {

    private static final Logger logger = LogManager.getLogger(LocalidadDAO.class.getName());

    public List<LocalidadDTO> findAll(Connection c) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT l.id, l.nombre, l.provincia_id, p.nombre AS provincia_nombre"
                    + "FROM localidad l "
                    + "INNER JOIN provincia p ON p.id = l.provincia_id "
                    + "ORDER BY l.nombre";
            logger.debug("Executing SQL: {}", sql);
            ps = c.prepareStatement(sql);
            rs = ps.executeQuery();
            List<LocalidadDTO> localidades = new ArrayList<LocalidadDTO>();
            while (rs.next()) {
                localidades.add(loadNext(rs));
            }
            return localidades;
        } finally {
            JDBCUtils.close(rs, ps);
        }
    }
    

    public List<LocalidadDTO>  findLocalidadByProvincia(Connection c, Long idProvincia)
            throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT l.id, l.nombre, l.provincia_id, "
                    + "p.nombre AS provincia_nombre"
                    + "FROM localidad l "
                    + "INNER JOIN provincia p ON p.id = l.provincia_id "
                    + "WHERE l.provincia_id = ? "
                    + "ORDER BY l.nombre";

            logger.debug("Executing SQL: {}", sql);
            ps = c.prepareStatement(sql);
            DAOUtils.setParameters(ps, idProvincia);
            rs = ps.executeQuery();

            List<LocalidadDTO> localidades = new ArrayList<>();
            while (rs.next()) {
                localidades.add(loadNext(rs));
            }

            return localidades;
        } finally {
            JDBCUtils.close(rs, ps);
        }
    }
    
    private LocalidadDTO loadNext(ResultSet rs) throws Exception {
        int i = 1;
        LocalidadDTO localidad = new LocalidadDTO();
        localidad.setId(rs.getLong(i++));
        localidad.setNombre(rs.getString(i++));
        localidad.setProvinciaId(rs.getLong(i++));
        localidad.setProvinciaNombre(rs.getString(i++));
        return localidad;
    }
}
