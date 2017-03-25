package jmp.resources;

import jmp.exceptions.ServiceException;
import jmp.services.data.User;

import java.util.List;

/**
 * Created by alex on 25.03.17.
 */
public interface UserResource {
    User createUser(final User user) throws ServiceException;

    User getUserById(final Long id) throws ServiceException;

    List<User> getAllUsers() throws ServiceException;

    void deleteUser(final Long id) throws ServiceException;
}
