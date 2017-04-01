package jmp.database.dbtools.stubs;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by alex on 29.03.17.
 */
public interface Stub {
    void addNewOne(final Connection connection) throws SQLException;
}
