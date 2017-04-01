package jmp.database.dbtools.dao;

import com.google.common.collect.Lists;
import jmp.dao.Like;
import jmp.database.DBManager;
import jmp.exceptions.BussinessException;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by alex on 01.04.17.
 */
public class LikesDAOImpl implements LikesDAO {
    private static Logger log = Logger.getLogger(LikesDAOImpl.class.toString());

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SS");

    private static final String ALL_LIKES = "SELECT postId, userId, time FROM Likes";

    private static String ADD_LIKE = "INSERT INTO Likes (postId, userId, time) VALUES (?, ?, ?)";

    @Override
    public List<Like> getLikes() throws BussinessException, SQLException {
        Statement statement = null;
        final List<Like> likes = Lists.newArrayList();

        try (Connection conn = DBManager.getConnection()) {
            statement = conn.createStatement();

            final ResultSet results = statement.executeQuery(ALL_LIKES);

            if (results != null) {
                while (results.next()) {
                    likes.add(getLike(results));
                }
            }

        } catch (IOException e) {
            log.warning(e.getMessage());
            throw new BussinessException(e.getMessage());
        } catch (ParseException e) {
            log.warning(e.getMessage());
            throw new BussinessException(e.getMessage());
        } catch (SQLException e) {
            log.warning(e.getMessage());
            throw new BussinessException(e.getMessage());
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
        return likes;
    }

    @Override
    public void addLike(final Like like) throws BussinessException, SQLException {
        if (like == null) {
            return;
        }
        PreparedStatement statement = null;

        try (Connection conn = DBManager.getConnection()) {
            statement = conn.prepareStatement(ADD_LIKE);

            final java.sql.Timestamp timestamp = new Timestamp(like.getTimestamp().getTime());

            statement.setLong(1, like.getPostId());
            statement.setLong(2, like.getUserId());
            statement.setTimestamp(3, timestamp);

            //log.info(statement.toString());

            int result = statement.executeUpdate();

            if (result == 0) {
                throw new BussinessException("Like isn't created");
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

    private Like getLike(final ResultSet results) throws SQLException, ParseException {

        if (results == null) {
            return null;
        }

        final Like like = new Like();
        like.setPostId(results.getLong(1));
        like.setUserId(results.getLong(2));
        like.setTimestamp(results.getDate(3));
        return like;
    }
}
