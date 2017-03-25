package jmp.dao;

import jmp.dao.entity.UserEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * Created by alex on 24.03.17.
 */
@Repository
@Transactional
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(final UserEntity user) {
        entityManager.persist(user);
    }

    @Override
    public void update(final UserEntity user) {
        entityManager.merge(user);
    }

    @Override
    public UserEntity getUseByLogin(final String login) {
        final List<UserEntity> result = entityManager.createNamedQuery("UserEntity.byLogin")
                .setParameter("login", login)
                .getResultList();
        return CollectionUtils.isEmpty(result) ? null : result.get(0);
    }

    @Override
    public UserEntity getUseById(final Long id) {
        return entityManager.find(UserEntity.class, id);
    }

    @Override
    public List<UserEntity> getUsers() {
        return entityManager.createNamedQuery("UserEntity.all").getResultList();
    }

    @Override
    public void delete(final Long id) {
        final UserEntity user = getUseById(id);
        if (user != null) {
            entityManager.remove(user);
        }
    }
}
