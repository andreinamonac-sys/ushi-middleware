package com.andreina.ushi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.andreina.ushi.model.LocalidadDTO;
import com.andreina.ushi.utils.JDBCUtils;

public class LocalidadDAO {

    private static final Logger logger = LogManager.getLogger(LocalidadDAO.class.getName());

    public List<LocalidadDTO> findAll(Connection c) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT l.id, l.nombre, l.provincia_id, p.nombre AS provincia_nombre, l.granja_id "
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

    private LocalidadDTO loadNext(ResultSet rs) throws Exception {
        int i = 1;
        LocalidadDTO localidad = new LocalidadDTO();
        localidad.setId(rs.getLong(i++));
        localidad.setNombre(rs.getString(i++));
        localidad.setProvinciaId(rs.getLong(i++));
        localidad.setProvinciaNombre(rs.getString(i++));
        localidad.setGranjaId(rs.getLong(i++));
        return localidad;
    }
}
