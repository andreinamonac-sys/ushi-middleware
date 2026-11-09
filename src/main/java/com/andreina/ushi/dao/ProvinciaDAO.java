package com.andreina.ushi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.andreina.ushi.model.Provincia;
import com.andreina.ushi.utils.JDBCUtils;

public class ProvinciaDAO {

    private static final Logger logger = LogManager.getLogger(ProvinciaDAO.class.getName());

    public List<Provincia> findAll(Connection c) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT p.id, p.nombre FROM provincia p ORDER BY p.nombre";
            logger.debug("Executing SQL: {}", sql);
            ps = c.prepareStatement(sql);
            rs = ps.executeQuery();
            List<Provincia> provincias = new ArrayList<Provincia>();
            while (rs.next()) {
                provincias.add(loadNext(rs));
            }
            return provincias;
        } finally {
            JDBCUtils.close(rs, ps);
        }
    }

    private Provincia loadNext(ResultSet rs) throws Exception {
        int i = 1;
        Provincia provincia = new Provincia();
        provincia.setId(rs.getLong(i++));
        provincia.setNombre(rs.getString(i++));
        return provincia;
    }
}
