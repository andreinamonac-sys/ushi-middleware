package com.andreina.ushi.utils;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DAOUtils {

    /**
     * Establece los parametros del PreparedStatement desde una lista.
     * @param ps PreparedStatement sobre el que se establecen los parametros.
     * @param params valores a establecer en orden.
     * @throws SQLException si ocurre un error al establecer parametros.
     */
    public static void setParameters(PreparedStatement ps, List<Object> params) throws SQLException {
        int i = 1;
        for (Object param : params) {
            ps.setObject(i++, param);
        }
    }

    /**
     * Establece los parametros del PreparedStatement desde varargs.
     * @param ps PreparedStatement sobre el que se establecen los parametros.
     * @param params valores a establecer en orden.
     * @throws SQLException si ocurre un error al establecer parametros.
     */
    public static void setParameters(PreparedStatement ps, Object... params) throws SQLException {
        setParameters(ps, Arrays.asList(params));
    }

    /**
     * Convierte un java.util.Date a java.sql.Timestamp conservando null.
     * @param date fecha a convertir.
     * @return Timestamp equivalente, o null si date es null.
     */
    public static Timestamp toTimestamp(Date date) {
        return date == null ? null : new Timestamp(date.getTime());
    }

    /**
     * Lee un Long nullable desde el ResultSet actual.
     * @param rs ResultSet posicionado en la fila a leer.
     * @param index indice de columna, empezando en 1.
     * @return valor Long de la columna, o null si la columna SQL era NULL.
     * @throws SQLException si ocurre un error leyendo el ResultSet.
     */
    public static Long getLong(ResultSet rs, int index) throws SQLException {
        Long value = rs.getLong(index);
        return rs.wasNull() ? null : value;
    }
}
