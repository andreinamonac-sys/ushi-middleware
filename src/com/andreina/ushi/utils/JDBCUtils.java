package com.andreina.ushi.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JDBCUtils {

    private static Logger logger = LogManager.getLogger(JDBCUtils.class.getName());

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/ushi", "root", "abc123.");
        } catch (Exception e) {
            logger.fatal(e);
        }
        return null;
    }

    public static void close(ResultSet rs, PreparedStatement ps) {
        try {
            if (rs != null) rs.close();
        } catch (Exception e) {
            logger.error(e);
        }
        try {
            if (ps != null) ps.close();
        } catch (Exception e) {
            logger.error(e);
        }
    }

    /**
     * Factoriza el (commit o rollback) + cierre de conexión.
     * Si la conexión tiene autoCommit=true (lecturas), no intenta
     * commit/rollback ya que la base de datos lo gestiona automáticamente.
     * @param c conexion a cerrar.
     * @param commitOrRollback true para commit, false para rollback.
     */
    public static void close(Connection c, boolean commitOrRollback) {
        if (c != null) {
            try {
                // [CORRECCIÓN] Solo gestionar transacción si autoCommit está desactivado,
                // es decir, si el llamador hizo explícitamente c.setAutoCommit(false).
                if (!c.getAutoCommit()) {
                    if (commitOrRollback) {
                        c.commit();
                    } else {
                        c.rollback();
                    }
                }
            } catch (SQLException e) {
                logger.error(e);
            }
            try {
                c.close();
            } catch (SQLException sqle) {
                logger.warn(sqle);
            }
        }
    }

    public static void rollback(Connection c) {
        try {
            c.rollback();
        } catch (SQLException sqle) {
            logger.error(sqle);
        }
    }
}