package jmp.database.dbtools.dao;

import com.google.common.collect.Lists;
import jmp.dao.Article;
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
public class ArticleDAOImpl implements ArticleDAO {
    private static Logger log = Logger.getLogger(ArticleDAOImpl.class.toString());

    private static final String ALL_ARTICLES = "SELECT ID, userId, text Posts";

    private static String ADD_ARTICLES = "INSERT INTO Posts (userId, text) VALUES (";

    @Override
    public List<Article> getAllArticles() {

        Statement statement = null;
        final List<Article> articles = Lists.newArrayList();

        try (Connection conn = DBManager.getConnection()) {
            statement = conn.createStatement();

            final ResultSet results = statement.executeQuery(ALL_ARTICLES);

            if (results != null) {
                while (results.next()) {
                    articles.add(getArticle(results));
                }
            }

        } catch (IOException e) {
            log.warning(e.getMessage());
            throw new BussinessException(e.getMessage());
        } catch (ParseException e) {
            log.warning(e.getMessage());
            throw new BussinessException(e.getMessage());
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
        return articles;
    }

    @Override
    public void addNewArticle(Article article) {

    }

    private Article getArticle(final ResultSet results) throws SQLException, ParseException {

        if (results == null) {
            return null;
        }

        final Article article = new Article();
        article.setId(results.getLong(1));
        article.setName(results.getString(2));
        article.setSurname(results.getString(3));
        return article;
    }
}
