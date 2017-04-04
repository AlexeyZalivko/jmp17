package jmp.database.dbtools.dao;

import jmp.dao.Like;
import jmp.exceptions.BussinessException;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by alex on 01.04.17.
 */
public interface LikesDAO {

    List<Like> getLikes() throws BussinessException, SQLException;

    void addLike(final Like like) throws BussinessException, SQLException;
}
