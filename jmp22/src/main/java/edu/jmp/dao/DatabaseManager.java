package edu.jmp.dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by alex on 09.03.17.
 */
public interface DatabaseManager<T> {

    void add(final T item) throws SQLException;

    T getById(final Long id) throws SQLException;

    List<T> getAll() throws SQLException;
}
