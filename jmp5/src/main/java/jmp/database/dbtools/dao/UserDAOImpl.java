package jmp.database.dbtools.dao;

import com.google.common.collect.Lists;
import jmp.dao.User;
import jmp.database.DBManager;
import jmp.exceptions.BussinessException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by alex on 01.04.17.
 */
public class UserDAOImpl implements UserDAO {
    private static Logger log = Logger.getLogger(UserDAOImpl.class.toString());

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private static final String ALL_USERS = "SELECT ID, NAME, SURNAME, BIRTHDAY FROM Users";

    private static String ADD_USER_SQL = "INSERT INTO Users (NAME, SURNAME, BIRTHDAY) VALUES (";

    @Override
    public List<User> getAllUsers() throws BussinessException, SQLException {
        Statement statement = null;
        final List<User> users = Lists.newArrayList();

        try (Connection conn = DBManager.getConnection()) {
            statement = conn.createStatement();

            final ResultSet results = statement.executeQuery(ALL_USERS);

            if (results != null) {
                while (results.next()) {
                    users.add(getUser(results));
                }
            }

        } catch (IOException e) {
            log.warning(e.getMessage());
            throw new BussinessException(e.getMessage());
        } catch (ParseException e) {
            log.warning(e.getMessage());
            throw new BussinessException(e.getMessage());
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
        return users;
    }

    @Override
    public void addUser(User user) throws BussinessException, SQLException {
        if (user == null) {
            return;
        }
        Statement statement = null;

        try (Connection conn = DBManager.getConnection()) {
            statement = conn.createStatement();

            final int result = statement.executeUpdate(
                    ADD_USER_SQL + "'"
                            + user.getName()
                            + "', '"
                            + user.getSurname()
                            + "', '"
                            + sdf.format(user.getBirthday())
                            + "')"
            );

            if (result == 0) {
                throw new BussinessException("User isn't created");
            }
        } catch (IOException e) {
            log.warning(e.getMessage());
            throw new BussinessException(e.getMessage());
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    private User getUser(final ResultSet results) throws SQLException, ParseException {

        if (results == null) {
            return null;
        }

        final User user = new User();
        user.setId(results.getLong(1));
        user.setName(results.getString(2));
        user.setSurname(results.getString(3));
        user.setBirthday(sdf.parse(results.getString(4)));
        return user;
    }
}
