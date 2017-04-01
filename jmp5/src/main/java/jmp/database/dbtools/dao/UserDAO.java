package jmp.database.dbtools.dao;

import jmp.dao.User;
import jmp.exceptions.BussinessException;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by alex on 01.04.17.
 */
public interface UserDAO {
    List<User> getAllUsers() throws BussinessException, SQLException;

    void addUser(final User user) throws BussinessException, SQLException;
}
