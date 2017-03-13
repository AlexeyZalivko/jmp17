package edu.jmp.dao;

import edu.jmp.dao.data.Author;
import edu.jmp.dao.management.SQLHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by alex on 12.03.17.
 */
public class AuthorBeanImpl implements AuthorBean {
    final static Logger log = Logger.getLogger(AuthorBeanImpl.class.getName());

    final static String INSERT_AUTHOR = "INSERT  INTO AUTHOR (NAME) VALUES(";
    final static String SELECT_AUTHOR_ID = "SELECT ID, NAME FROM AUTHOR WHERE ID=";
    final static String SELECT_AUTHOR_NAME = "SELECT top 1 ID, NAME FROM AUTHOR WHERE NAME=";
    final static String SELECT_AUTHOR = "SELECT ID, NAME FROM AUTHOR";

    @Override
    public void add(final Author item) throws SQLException {
        Connection conn = null;
        Statement statement = null;
        try {
            conn = SQLHelper.getConnection();
            statement = conn.createStatement();

            statement.execute(INSERT_AUTHOR
                    + "'"
                    + item.getName()
                    + "')");

        } catch (SQLException e) {
            log.warning(e.getMessage());
            throw e;
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

    }

    @Override
    public Author getById(final Long id) throws SQLException {
        return getByParam(id, null);
    }

    @Override
    public List<Author> getAll() throws SQLException {
        Connection conn = null;
        Statement statement = null;
        try {
            conn = SQLHelper.getConnection();
            statement = conn.createStatement();

            final ResultSet set = statement.executeQuery(SELECT_AUTHOR);

            if (set == null) {
                return null;
            }

            final List<Author> authors = new ArrayList<>();
            while (set.next()) {
                authors.add(createAuthor(set));
            }

            return authors;
        } catch (SQLException e) {
            log.warning(e.getMessage());
            throw e;
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    @Override
    public Author getAuthorByName(final String name) throws SQLException {
        return getByParam(null, name);
    }

    private Author getByParam(final Long id, final String name) throws SQLException {

        Connection conn = null;
        Statement statement = null;
        try {
            conn = SQLHelper.getConnection();
            statement = conn.createStatement();

            ResultSet set;
            if (id != null) {
                set = statement.executeQuery(SELECT_AUTHOR_ID
                        + id);
            } else {
                set = statement.executeQuery(SELECT_AUTHOR_NAME
                        + "'"
                        + name
                        + "'");
            }

            if (set == null) {
                return null;
            }

            if (set.next()) {
                return createAuthor(set);
            }

            return null;
        } catch (SQLException e) {
            log.warning(e.getMessage());
            throw e;
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    private Author createAuthor(final ResultSet set) throws SQLException {
        if (set == null) {
            return null;
        }

        final Author author = new Author();
        author.setId(set.getLong(1));
        author.setName(set.getString(2));
        return author;
    }
}
