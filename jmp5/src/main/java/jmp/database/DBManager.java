package jmp.database;

import jmp.database.dbtools.DBInit;
import org.h2.jdbcx.JdbcConnectionPool;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Created by alex on 29.03.17.
 */
public final class DBManager {

    private static Logger log = Logger.getLogger(DBManager.class.toString());

    private static String JDBC_CONNECTION_STRING = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";

    private static JdbcConnectionPool pool = null;

    private DBManager() {
    }

    public static Connection getConnection() throws SQLException, IOException {
        if (pool == null) {
            synchronized (DBManager.class) {
                if (pool == null) {
                    pool = JdbcConnectionPool.create(JDBC_CONNECTION_STRING, "user", "pwd");
                }

                try (final Connection connection = pool.getConnection()) {
                    connection.setAutoCommit(true);
                    DBInit.init(connection);
                } catch (SQLException e) {
                    log.warning("Failed init database " + e.getMessage());
                    throw e;
                } catch (IOException e) {
                    log.warning("Failed init database scheme" + e.getMessage());
                    throw e;
                }
            }
        }

        final Connection connection = pool.getConnection();
        connection.setAutoCommit(true);
        return connection;
    }
}
