package jmp.services;

import jmp.dao.entity.LogoEntity;
import jmp.exceptions.ServiceException;
import jmp.services.data.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by alex on 24.03.17.
 */
public interface UserService {

    User addUser(final User user) throws ServiceException;

    User getUserById(final Long id) throws ServiceException;

    User getUserByLogin(final String login) throws ServiceException;

    List<User> getAllUsers() throws ServiceException;

    void remove(final Long id) throws ServiceException;

    byte[] getUserLogo(final Long id) throws ServiceException;

    User addUserLogo(final Long id, final MultipartFile image) throws ServiceException;
}
