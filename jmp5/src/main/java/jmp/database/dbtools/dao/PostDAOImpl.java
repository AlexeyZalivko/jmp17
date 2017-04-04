package jmp.database.dbtools.dao;

import com.google.common.collect.Lists;
import jmp.dao.Article;
import jmp.dao.Post;
import jmp.dao.User;
import jmp.database.DBManager;
import jmp.exceptions.BussinessException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by alex on 01.04.17.
 */
public class PostDAOImpl implements PostDAO {
    private static Logger log = Logger.getLogger(PostDAOImpl.class.toString());

    private static final String ALL_ARTICLES = "SELECT id, userId, text FROM Posts";
    private static final String ALL_USER_ARTICLES = "SELECT id, userId, text FROM Posts WHERE userId = ";

    private static String ADD_ARTICLES = "INSERT INTO Posts (userId, text) VALUES (";

    @Override
    public List<Post> getAllPosts() throws BussinessException, SQLException {
        return getPosts(null);
    }

    @Override
    public List<Post> getAllUserPost(final Long userId) throws BussinessException, SQLException {
        if (userId == null) {
            throw new BussinessException("UserId is undefined");
        }
        return getPosts(userId);
    }

    @Override
    public void addNewPost(final Post post) throws BussinessException, SQLException {
        if (post == null) {
            return;
        }
        Statement statement = null;

        try (Connection conn = DBManager.getConnection()) {
            statement = conn.createStatement();

            final int result = statement.executeUpdate(
                    ADD_ARTICLES
                            + post.getUserId()
                            + ", '"
                            + post.getText()
                            + "')"
            );

            if (result == 0) {
                throw new BussinessException("Post isn't created");
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

    @Override
    public Post getPostByArticle(final Article article, final User user) {
        final Post post = new Post();
        post.setText(article.getDescription());
        post.setUserId(user.getId());
        return post;
    }

    private Post getArticle(final ResultSet results) throws SQLException, ParseException {

        if (results == null) {
            return null;
        }

        final Post post = new Post();
        post.setId(results.getLong(1));
        post.setUserId(results.getLong(2));
        post.setText(results.getString(3));
        return post;
    }

    private List<Post> getPosts(final Long userId) throws BussinessException, SQLException {

        Statement statement = null;
        final List<Post> posts = Lists.newArrayList();

        try (Connection conn = DBManager.getConnection()) {
            statement = conn.createStatement();

            final ResultSet results = statement.executeQuery(((userId != null) ?
                    ALL_USER_ARTICLES + userId
                    : ALL_ARTICLES));

            if (results != null) {
                while (results.next()) {
                    posts.add(getArticle(results));
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
        return posts;
    }
}
