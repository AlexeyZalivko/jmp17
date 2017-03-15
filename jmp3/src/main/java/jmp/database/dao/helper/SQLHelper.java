package jmp.database.dao.helper;

import org.h2.jdbcx.JdbcConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by alex on 15.03.17.
 */
public class SQLHelper {

    private static String JDBC_CONNECTION_STRING = "jdbc:h2:mem:test;INIT=runscript from 'classpath:scripts/init.sql';DB_CLOSE_DELAY=-1";

    private static JdbcConnectionPool pool = null;

    public static JdbcConnectionPool getConnectionPool() {
        if (pool == null) {
            synchronized (SQLHelper.class) {
                if (pool == null) {
                    pool = JdbcConnectionPool.create(JDBC_CONNECTION_STRING, "user", "password");
                }
            }
        }
        return pool;
    }

    public static Connection getConnection() throws SQLException {
        final Connection conn = getConnectionPool().getConnection();
        conn.setAutoCommit(true);
        return conn;
    }
}
