package jmp.database.dao;

import com.google.common.collect.Lists;
import jmp.database.dao.helper.SQLHelper;
import jmp.database.data.User;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by alex on 15.03.17.
 */
public class UserDAOImpl implements UserDAO {

    private static Logger log = Logger.getLogger(UserDAOImpl.class.getName());

    private static String USER_GET_BY_ID = "SELECT ID, LOGIN, FIRST_NAME, LAST_NAME, MAIL, LOGO FROM USERS WHERE ID=";
    private static String USER_GET_ALL = "SELECT ID, LOGIN, FIRST_NAME, LAST_NAME, MAIL, LOGO FROM USERS";
    private static String USER_INSERT = "INSERT INTO USERS(LOGIN, FIRST_NAME, LAST_NAME, MAIL) VALUES('";
    private static String USER_UPDATE = "UPDATE USERS SET ";

    @Override
    public User getUserById(final Long id) throws SQLException {

        Statement statement = null;
        User user = null;

        try (Connection conn = SQLHelper.getConnection()) {
            statement = conn.createStatement();

            ResultSet results = statement.executeQuery(USER_GET_BY_ID + id);

            if (results != null && results.next()) {
                user = getUser(results);
            }
        } catch (SQLException e) {
            log.warning(e.getMessage());
            throw e;
        } finally {
            if (statement != null) {
                statement.close();
            }
        }

        return user;
    }

    @Override
    public User addUser(final User user) throws SQLException {

        if (user == null) {
            return null;
        }

        Statement statement = null;

        try (Connection conn = SQLHelper.getConnection()) {
            statement = conn.createStatement();

            final int result = statement.executeUpdate(USER_INSERT
                    + user.getLogin()
                    + "', '"
                    + user.getFirstName()
                    + "', '"
                    + user.getLastName()
                    + "', '"
                    + user.getMail()
                    + "')"
            );

            if (result == 0) {
                throw new SQLException("Creating user failed");
            }

            try (ResultSet set = statement.getGeneratedKeys()) {
                if (set.next()) {
                    user.setId(set.getLong(1));
                } else {
                    throw new SQLException("Creating user failed. Key wasn't created.");
                }
            }
        } catch (SQLException e) {
            log.warning(e.getMessage());
            throw e;
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() throws SQLException {

        Statement statement = null;
        final List<User> users = Lists.newArrayList();

        try (Connection conn = SQLHelper.getConnection()) {
            statement = conn.createStatement();

            final ResultSet results = statement.executeQuery(USER_GET_ALL);

            if (results != null) {
                while (results.next()) {
                    users.add(getUser(results));
                }
            }

        } catch (SQLException e) {
            log.warning(e.getMessage());
            throw e;
        } finally {
            if (statement != null) {
                statement.close();
            }
        }

        return users;
    }

    @Override
    public User updateUser(final User user) throws SQLException {

        if (user == null) {
            return null;
        }

        Statement statement = null;

        try (Connection conn = SQLHelper.getConnection()) {
            statement = conn.createStatement();

            final int result = statement.executeUpdate(USER_UPDATE
                    + "FIRST_NAME = '"
                    + user.getFirstName()
                    + "', LAST_NAME = '"
                    + user.getLastName()
                    + "', MAIL = '"
                    + user.getMail()
                    + "' WHERE ID = "
                    + user.getId()
            );

            if (result == 0) {
                throw new SQLException("Creating user failed");
            }

        } catch (SQLException e) {
            log.warning(e.getMessage());
            throw e;
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
        return user;
    }

    @Override
    public User addUserLogo(final String id, final String fileName) throws SQLException {
        User user = null;
        if (StringUtils.isEmpty(id) || StringUtils.isEmpty(fileName)) {
            return user;
        }

        Statement statement = null;

        try (Connection conn = SQLHelper.getConnection()) {
            statement = conn.createStatement();

            final int result = statement.executeUpdate(USER_UPDATE
                    + "LOGO = '"
                    + fileName
                    + "' WHERE ID = "
                    + id
            );

            if (result == 0) {
                throw new SQLException("Add user logo failed");
            }

            user = getUserById(Long.valueOf(id));
        } catch (SQLException e) {
            log.warning(e.getMessage());
            throw e;
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
        return user;
    }

    private User getUser(final ResultSet results) throws SQLException {

        if (results == null) {
            return null;
        }

        final User user = new User();
        user.setId(results.getLong(1));
        user.setLogin(results.getString(2));
        user.setFirstName(results.getString(3));
        user.setLastName(results.getString(4));
        user.setMail(results.getString(5));
        user.setLogo(results.getString(6));
        return user;
    }

}
