package jmp.dao;

import jmp.dao.entity.LogoEntity;

/**
 * Created by alex on 24.03.17.
 */
public interface LogoDao {

    void create(final LogoEntity logoEntity);

    LogoEntity getLogoById(final Long id);

    void delete(final Long id);
}
