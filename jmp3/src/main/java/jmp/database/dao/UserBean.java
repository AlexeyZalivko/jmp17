package jmp.database.dao;

import jmp.database.data.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by alex on 15.03.17.
 */
public interface UserBean {

    User getUserById(final Long id) throws SQLException;

    User addUser(final User user) throws SQLException;

    List<User> getAllUsers() throws SQLException;

    void updateUser(final User user) throws SQLException;
}