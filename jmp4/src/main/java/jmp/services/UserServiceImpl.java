package jmp.services;

import com.google.common.collect.Lists;
import jmp.dao.UserDao;
import jmp.dao.entity.UserEntity;
import jmp.exceptions.ServiceException;
import jmp.services.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by alex on 24.03.17.
 */
@Service
public class UserServiceImpl implements UserService {

    public static final String REQUEST_PARAMETER_IS_NULL = "Request parameter is null";
    public static final String USER_CREATION_PROBLEM = "User creation problem";
    public static final String USER_ISN_T_EXIST = "User isn't exist.";
    public static final int ERROR_CODE = 500;
    public static final String IMAGE_ISN_T_EXIST = "Image isn't exist";

    @Autowired
    private UserDao userDao;

    @Override
    public User addUser(final User user) throws ServiceException {
        if (user == null) {
            throw new ServiceException(ERROR_CODE, REQUEST_PARAMETER_IS_NULL);
        }

        final UserEntity userEntity = insertUser(user);

        if (user.getLogo() == null) {
            return user;
        }

        insertLogo(user, userEntity.getId());

        return user;
    }

    @Override
    public User getUserById(final Long id) throws ServiceException {
        if (id == null) {
            throw new ServiceException(ERROR_CODE, REQUEST_PARAMETER_IS_NULL);
        }

        return getUser(userDao.getUseById(id));
    }

    @Override
    public User getUserByLogin(final String login) throws ServiceException {
        if (login == null) {
            throw new ServiceException(ERROR_CODE, REQUEST_PARAMETER_IS_NULL);
        }
        return getUser(userDao.getUseByLogin(login));
    }

    @Override
    public List<User> getAllUsers() throws ServiceException {
        final List<UserEntity> userEntities = userDao.getUsers();

        if (CollectionUtils.isEmpty(userEntities)) {
            return Lists.newArrayList();
        }

        return userEntities.stream()
                .map(u -> {
                    final User user = new User();
                    user.setId(u.getId());
                    user.setEmail(u.getMail());
                    user.setLogin(u.getLogin());
                    user.setFirstName(u.getFirstName());
                    user.setLastName(u.getLastName());
                    user.setLogo(getImageByBlob(u.getImage()));
                    return user;
                })
                .collect(toList());
    }

    @Override
    public void remove(final Long id) throws ServiceException {
        if (id == null) {
            throw new ServiceException(ERROR_CODE, REQUEST_PARAMETER_IS_NULL);
        }

        userDao.delete(id);
    }

    @Override
    public byte[] getUserLogo(final Long id) throws ServiceException {
        final User user = getUserById(id);
        if (user == null || user.getLogo() == null) {
            throw new ServiceException(ERROR_CODE, IMAGE_ISN_T_EXIST);
        }

        return user.getLogo();
    }

    @Override
    public User addUserLogo(final Long id, final MultipartFile image) throws ServiceException {
        if (id == null || image == null) {
            throw new ServiceException(ERROR_CODE, REQUEST_PARAMETER_IS_NULL);
        }

        final User user = getUserById(id);

        if (user == null) {
            throw new ServiceException(ERROR_CODE, USER_ISN_T_EXIST);
        }

        try {
            user.setLogo(image.getBytes());
        } catch (IOException e) {
            throw new ServiceException(ERROR_CODE, e.getMessage());
        }

        insertLogo(user, id);
        return user;
    }

    private UserEntity insertUser(final User user) throws ServiceException {

        UserEntity userEntity = userDao.getUseByLogin(user.getLogin());
        if (userEntity != null) {
            throw new ServiceException(ERROR_CODE, "User with login " + user.getLogin() + " is already exist");
        }

        userEntity = new UserEntity();
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setLogin(user.getLogin());
        userEntity.setMail(user.getEmail());
        userDao.create(userEntity);

        userEntity = userDao.getUseByLogin(user.getLogin());
        if (userEntity == null || userEntity.getId() == null) {
            throw new ServiceException(ERROR_CODE, USER_CREATION_PROBLEM);
        }
        user.setId(userEntity.getId());

        return userEntity;
    }

    private void insertLogo(final User user, final Long userId) throws ServiceException {
        final UserEntity userEntity = userDao.getUseById(userId);
        if (userEntity != null) {
            try {
                userEntity.setImage(new SerialBlob(user.getLogo()));
            } catch (SQLException e) {
                throw new ServiceException(ERROR_CODE, REQUEST_PARAMETER_IS_NULL);
            }
            userDao.update(userEntity);
        }
    }

    private User getUser(final UserEntity userEntity) throws ServiceException {

        if (userEntity == null) {
            throw new ServiceException(ERROR_CODE, USER_ISN_T_EXIST);
        }

        final User user = new User();
        user.setId(userEntity.getId());
        user.setFirstName(userEntity.getFirstName());
        user.setLastName(userEntity.getLastName());
        user.setLogin(userEntity.getLogin());
        user.setEmail(userEntity.getMail());

        user.setLogo(getImageByBlob(userEntity.getImage()));
        return user;
    }

    private byte[] getImageByBlob(final Blob blob) {
        if (blob == null) {
            return null;
        }

        try {
            final int lenghts = (int) blob.length();
            return blob.getBytes(1, lenghts);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
