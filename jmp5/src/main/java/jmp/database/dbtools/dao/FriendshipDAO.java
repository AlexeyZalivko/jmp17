package jmp.database.dbtools.dao;

import jmp.exceptions.BussinessException;

import java.sql.SQLException;

/**
 * Created by alex on 01.04.17.
 */
public interface FriendshipDAO {
    void addFriendship(final Long user1Id, final Long user2Id) throws BussinessException, SQLException;
}
