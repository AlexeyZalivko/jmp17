package jmp.dao;

import jmp.dao.entity.UserEntity;

import java.util.List;

/**
 * Created by alex on 24.03.17.
 */
public interface UserDao {

    void create(final UserEntity user);

    void update(final UserEntity user);

    UserEntity getUseByLogin(final String login);

    UserEntity getUseById(final Long id);

    List<UserEntity> getUsers();

    void delete(final Long id);
}
