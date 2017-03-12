package edu.jmp.dao;

import edu.jmp.dao.data.Author;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by alex on 12.03.17.
 */
public interface AuthorBean {

    void add(final Author item) throws SQLException;

    Author getById(final Long id) throws SQLException;

    List<Author> getAll() throws SQLException;

    public Author getAuthorByName(final String name) throws SQLException;
}
