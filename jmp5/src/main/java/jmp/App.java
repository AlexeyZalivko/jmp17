package jmp;

import com.google.common.collect.Lists;
import jmp.database.DBManager;
import jmp.database.dbtools.dao.*;
import jmp.exceptions.BussinessException;

import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.logging.Logger;

/**
 * Hello world!
 */
public class App {
    private static Logger log = Logger.getLogger(App.class.toString());

    private final static String FRIENDSHIP_FILTER = "  select u.id, count(f.userid2) as cnt"
            + " from users u"
            + " join friendship f"
            + " on f.userid1 = u.id"
            + " group by u.id"
            + " having cnt > ?";
/*
    private final static String REPORT_SQL = "select distinct u.name, count(l.postid) as likesCount"
            + " from users u"
            + " join likes l"
            + " on u.id = l.userid"
            + " and l.time between ? and ?"
            + " where u.id in (?)"
            + " group by u.name"
            + " having likesCount > ?"
            + " order by 1";
    */
    private final static String REPORT_SQL = "select distinct u.name, count(l.postid) as likesCount"
            + " from users u"
            + " join likes l"
            + " on u.id = l.userid"
            + " and l.time between '%s' and '%s'"
            + " where u.id in (%s)"
            + " group by u.name"
            + " having likesCount > %d"
            + " order by 1";

    private final static int FRIENDS_COUNT = 1000;
    private final static int LIKES_COUNT = 10;

    private final static String START_DATE = "2015-03-01";
    private final static String END_DATE = "2015-03-31";


    public static void main(String[] args) throws IOException, SQLException, BussinessException {
        DBManager.getConnection();

        final LikesDAO likesDAO = new LikesDAOImpl();
        System.out.println(likesDAO.getLikes().size());

        final UserDAO userDAO = new UserDAOImpl();
        System.out.println(userDAO.getAllUsers().size());

        final List<String> userIds = getUsersWhoHaveFriends();

        try (Connection conn = DBManager.getConnection()) {
            /*final PreparedStatement statement = conn.prepareStatement(REPORT_SQL);
            statement.setString(1, START_DATE);
            statement.setString(2, END_DATE);
            //statement.setObject(3, userIds.toArray());
            statement.setObject(3, );
            statement.setInt(4, LIKES_COUNT);

            log.info("final request: " + statement.toString());

            final ResultSet rs = statement.executeQuery();*/

            final String arrayJoin = String.join(", ", userIds);
            final String request = String.format(REPORT_SQL, START_DATE, END_DATE, arrayJoin, LIKES_COUNT);
            final Statement statement = conn.createStatement();

            final ResultSet rs = statement.executeQuery(request);

            if (rs != null) {
                while (rs.next()) {
                    log.info("User: " + rs.getString(1));
                }
            }
        }
    }

    private static List<String> getUsersWhoHaveFriends() throws BussinessException {
        final List<String> result = Lists.newArrayList();

        try (Connection conn = DBManager.getConnection()) {
            final PreparedStatement statement = conn.prepareStatement(FRIENDSHIP_FILTER);
            statement.setInt(1, FRIENDS_COUNT);

            final ResultSet rs = statement.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    result.add(rs.getString(1));
                }
            }
        } catch (Exception e) {
            log.warning(e.getMessage());
            throw new BussinessException(e.getMessage());
        }

        return result;
    }
}
