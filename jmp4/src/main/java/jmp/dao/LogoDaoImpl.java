package jmp.dao;

import jmp.dao.entity.LogoEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by alex on 24.03.17.
 */
@Repository
@Transactional
public class LogoDaoImpl implements LogoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(final LogoEntity logoEntity) {
        entityManager.persist(logoEntity);
    }

    @Override
    public LogoEntity getLogoById(final Long id) {
        return entityManager.find(LogoEntity.class, id);
    }

    @Override
    public void delete(final Long id) {
        final LogoEntity logoEntity = getLogoById(id);
        if (logoEntity != null) {
            entityManager.remove(logoEntity);
        }
    }
}
