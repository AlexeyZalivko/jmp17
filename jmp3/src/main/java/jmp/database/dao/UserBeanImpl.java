package jmp.database.dao;

import com.google.common.collect.Lists;
import jmp.database.dao.helper.SQLHelper;
import jmp.database.data.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by alex on 15.03.17.
 */
public class UserBeanImpl implements UserBean {

    private static Logger log = Logger.getLogger(UserBeanImpl.class.getName());

    private static String USER_GET_BY_ID = "SELECT ID, LOGIN, FIRST_NAME, LAST_NAME, MAIL FROM USERS WHERE ID=";
    private static String USER_GET_ALL = "SELECT ID, LOGIN, FIRST_NAME, LAST_NAME, MAIL FROM USERS";
    private static String USER_INSERT = "INSERT INTO USERS(LOGIN, FIRST_NAME, LAST_NAME, MAIL) VALUES('";

    public User getUserById(final Long id) throws SQLException {

        Statement statement = null;
        User user = null;

        try (Connection conn = SQLHelper.getConnection()) {
            statement = conn.createStatement();

            final ResultSet results = statement.executeQuery(USER_GET_BY_ID + id);

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

    public void addUser(final User user) throws SQLException {

        if (user == null) {
            return;
        }

        Statement statement = null;

        try (Connection conn = SQLHelper.getConnection()) {
            statement = conn.createStatement();

            statement.execute(USER_INSERT
                    + user.getLogin()
                    + "', '"
                    + user.getFirstName()
                    + "', '"
                    + user.getLastName()
                    + "', '"
                    + user.getMail()
                    + "')"
            );

        } catch (SQLException e) {
            log.warning(e.getMessage());
            throw e;
        } finally {
            if (statement != null) {
                statement.close();
            }
        }

    }

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
        return user;
    }

}
