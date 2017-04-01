package jmp.database.dbtools.dao;

import jmp.database.DBManager;
import jmp.exceptions.BussinessException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

/**
 * Created by alex on 01.04.17.
 */
public class FriendshipDAOImpl implements FriendshipDAO {
    private static Logger log = Logger.getLogger(LikesDAOImpl.class.toString());

    private static final String ALL_FRIENDSHIPS = "SELECT userId1, userId2 FROM Friendship";
    private static final String ADD_FRIENDSHIPS = "INSERT INTO Friendship (userId1, userId2) VALUES(";

    @Override
    public void addFriendship(final Long user1Id, final Long user2Id) throws BussinessException, SQLException {
        if (user1Id == null || user2Id == null) {
            throw new BussinessException("One of parameters is null");
        }
        Statement statement = null;

        try (Connection conn = DBManager.getConnection()) {
            statement = conn.createStatement();

            final int result = statement.executeUpdate(
                    ADD_FRIENDSHIPS
                            + user1Id
                            + ", "
                            + user2Id
                            + ")"
            );

            if (result == 0) {
                throw new BussinessException("Friendship isn't created");
            }
        } catch (IOException e) {
            log.warning(e.getMessage());
            throw new BussinessException(e.getMessage());
        } catch (BussinessException e) {
            log.warning(e.getMessage());
            throw new BussinessException(e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                statement.close();
            }
        }

    }
}
