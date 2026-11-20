package com.andreina.ushi.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JDBCUtils {
	
	private static Logger logger = LogManager.getLogger(JDBCUtils.class.getName());
	

    private static ComboPooledDataSource dataSource;

    static {
        try {
            dataSource = new ComboPooledDataSource();
            // Configurar el driver y la URL de la base de datos
            dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
            dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/ushi");
            dataSource.setUser("andreina2026");
            dataSource.setPassword("abc123.");

            // Parámetros recomendados del pool de c3p0
            dataSource.setMinPoolSize(5);
            dataSource.setAcquireIncrement(5);
            dataSource.setMaxPoolSize(20);
            dataSource.setMaxIdleTime(300); // Tiempo de inactividad antes de cerrar la conexión (segundos)

        } catch (Exception e) {
            logger.error("Error al inicializar el DataSource de c3p0", e);
        }
    }

    public static Connection getConnection() {
        try {
            // Solicita una conexión al pool en lugar de usar DriverManager
            return dataSource.getConnection();
        } catch (SQLException e) {
            logger.error("Error al obtener la conexión del pool de c3p0", e);
        }
        return null;
    }
	
	public static void close(ResultSet rs, PreparedStatement ps) {
		try { if (rs != null) rs.close(); } catch (Exception ignored) {}
		try { if (ps != null) ps.close(); } catch (Exception ignored) {}
	}	
	
	public static void close(Connection c) {
		try { if (c != null) c.close(); } catch (Exception ignored) {}
	}
	
	/**
	 * Factoriza el (commit o rollback) + cierre de conexión.
	 * @param c
	 * @param commitOrRollback
	 */
	public static void close(Connection c, boolean commitOrRollback) {
		if (c != null) {
			try {
				if (commitOrRollback) {
					c.commit();
				} else {
					c.rollback();
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