package com.andreina.ushi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.andreina.ushi.model.AlertaDTO;
import com.andreina.ushi.utils.DAOUtils;
import com.andreina.ushi.utils.JDBCUtils;

public class AlertaDAO {

    private static final Logger logger = LogManager.getLogger(AlertaDAO.class.getName());
    private static final String BASE_QUERY =
            "SELECT a.id, a.mensaje, a.fecha_hora, a.tag_id, a.leida, "
            + "(SELECT p.animal_id FROM parametro p WHERE p.tag_id = a.tag_id "
            + "ORDER BY CASE WHEN p.fecha_hora <= a.fecha_hora THEN 0 ELSE 1 END, "
            + "ABS(TIMESTAMPDIFF(SECOND, p.fecha_hora, a.fecha_hora)), p.id DESC LIMIT 1) AS animal_id, "
            + "(SELECT an.num_registro FROM parametro p "
            + "INNER JOIN animal an ON an.id = p.animal_id "
            + "WHERE p.tag_id = a.tag_id "
            + "ORDER BY CASE WHEN p.fecha_hora <= a.fecha_hora THEN 0 ELSE 1 END, "
            + "ABS(TIMESTAMPDIFF(SECOND, p.fecha_hora, a.fecha_hora)), p.id DESC LIMIT 1) AS animal_num_registro "
            + "FROM alerta a ";

    public List<AlertaDTO> findUltimas(Connection c, int limit) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = c.prepareStatement(BASE_QUERY + "WHERE a.leida = 0 ORDER BY a.fecha_hora DESC LIMIT ?");
            ps.setInt(1, limit <= 0 ? 10 : limit);
            rs = ps.executeQuery();
            List<AlertaDTO> alertas = new ArrayList<AlertaDTO>();
            while (rs.next()) {
                alertas.add(loadNext(rs));
            }
            return alertas;
        } catch (Exception e) {
            logger.error("Error cargando alertas: {}", e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(rs, ps);
        }
    }

    public boolean marcarLeida(Connection c, Long alertaId) throws Exception {
        PreparedStatement ps = null;
        try {
            ps = c.prepareStatement("UPDATE alerta SET leida = 1 WHERE id = ?");
            DAOUtils.setParameters(ps, alertaId);
            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            logger.error("Error marcando alerta como leida: {}", e.getMessage(), e);
            throw e;
        } finally {
            JDBCUtils.close(null, ps);
        }
    }

    private AlertaDTO loadNext(ResultSet rs) throws Exception {
        int i = 1;
        AlertaDTO alerta = new AlertaDTO();
        alerta.setId(rs.getLong(i++));
        alerta.setMensaje(rs.getString(i++));
        alerta.setFechaHora(rs.getTimestamp(i++));
        alerta.setTagId(rs.getLong(i++));
        alerta.setLeida(rs.getBoolean(i++));
        alerta.setCrotal(rs.getString(i++));
        alerta.setNumeroTag(rs.getString(i++));
        return alerta;
    }
}
