package jmp.database.dbtools.stubs;

import jmp.exceptions.BussinessException;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;

/**
 * Created by alex on 29.03.17.
 */
public interface Stub {
    void addNewOne(final Connection connection) throws SQLException, ParseException, BussinessException;
}
