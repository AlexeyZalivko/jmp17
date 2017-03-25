package jmp.services;

import com.google.common.collect.Lists;
import jmp.dao.LogoDao;
import jmp.dao.UserDao;
import jmp.dao.entity.LogoEntity;
import jmp.dao.entity.UserEntity;
import jmp.exceptions.ServiceException;
import jmp.services.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
    @Autowired
    private LogoDao logoDao;

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
                .map(u->{
                    final User user = new User();
                    user.setId(u.getId());
                    user.setEmail(u.getMail());
                    user.setLogin(u.getLogin());
                    user.setFirstName(u.getFirstName());
                    user.setLastName(u.getLastName());
                    return user;
                })
                .collect(toList());
    }

    @Override
    public void remove(final Long id) throws ServiceException {
        if (id == null) {
            throw new ServiceException(ERROR_CODE, REQUEST_PARAMETER_IS_NULL);
        }

        logoDao.delete(id);
        userDao.delete(id);
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

    private void insertLogo(final User user, final Long userId) {
        final LogoEntity logo = new LogoEntity();
        logo.setId(userId);
        logo.setImage(user.getLogo());
        logoDao.create(logo);
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

        final LogoEntity logo = logoDao.getLogoById(userEntity.getId());
        if (logo == null || logo.getImage() == null) {
            return user;
        }

        user.setLogo(logo.getImage());
        return user;
    }
}
