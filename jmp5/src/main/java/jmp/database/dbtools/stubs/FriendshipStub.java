package jmp.database.dbtools.stubs;

import jmp.dao.User;
import jmp.database.dbtools.dao.*;
import jmp.exceptions.BussinessException;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Created by alex on 01.04.17.
 */
public class FriendshipStub implements Stub {
    private static Logger log = Logger.getLogger(FriendshipStub.class.toString());

    private UserDAO userDAO = new UserDAOImpl();
    private FriendshipDAO friendshipDAO = new FriendshipDAOImpl();

    @Override
    public void addNewOne(final Connection connection) throws SQLException, ParseException, BussinessException {
        final List<User> users = userDAO.getAllUsers();
        final Random rnd = new Random(0);

        for (User user : users) {
            final Long userId = user.getId();
            int maxCount = rnd.nextInt(users.size() - 1);
            for (int i = 0; i < users.size() && i < maxCount; i++) {
                final Long friendId = users.get(i).getId();
                if (userId.equals(friendId)) {
                    maxCount++;
                    continue;
                }

                friendshipDAO.addFriendship(userId, friendId);
            }
        }
    }
}
