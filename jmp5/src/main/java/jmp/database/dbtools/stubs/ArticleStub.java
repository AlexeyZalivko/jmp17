package jmp.database.dbtools.stubs;

import com.google.gson.Gson;
import jmp.dao.Article;
import jmp.dao.ArticlesCollection;
import jmp.dao.Post;
import jmp.dao.User;
import jmp.database.dbtools.dao.PostDAO;
import jmp.database.dbtools.dao.PostDAOImpl;
import jmp.database.dbtools.dao.UserDAO;
import jmp.database.dbtools.dao.UserDAOImpl;
import jmp.exceptions.BussinessException;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by alex on 01.04.17.
 */
public class ArticleStub implements Stub {

    private static Logger log = Logger.getLogger(ArticleStub.class.toString());

    private static ArticlesCollection articlesCollection = null;

    private static final String ARTICLES_JSON = "Articles.json";

    private static final String ADD_ARTICLE_SQL = "INSERT INTO Posts (userId, text) VALUES(";

    private PostDAO postDAO = new PostDAOImpl();
    private UserDAO userDAO = new UserDAOImpl();

    static {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            final List<String> fileContent = IOUtils.readLines(classLoader.getResourceAsStream(ARTICLES_JSON), StandardCharsets.UTF_8);
            final String json = String.join(" ", fileContent);
            final Gson gson = new Gson();
            articlesCollection = gson.fromJson(json, ArticlesCollection.class);
        } catch (IOException e) {
            log.warning("Initializing names error: " + e.getMessage());
        }
    }

    @Override
    public void addNewOne(Connection connection) throws SQLException, BussinessException {
        if (articlesCollection == null || articlesCollection.getItems() == null) {
            throw new BussinessException("No any articles");
        }
        try {
            final List<User> users = userDAO.getAllUsers();

            for (User user : users) {
                for (Article article : articlesCollection.getItems()) {
                    final Post post = postDAO.getPostByArticle(article, user);
                    postDAO.addNewPost(post);
                }
            }
        } catch (BussinessException e) {
            log.warning(e.getMessage());
            throw new BussinessException(e.getMessage());
        }
    }
}
