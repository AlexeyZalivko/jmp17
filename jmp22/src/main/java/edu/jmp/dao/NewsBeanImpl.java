package edu.jmp.dao;

import edu.jmp.dao.data.NewsItem;
import edu.jmp.dao.management.SQLHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by alex on 09.03.17.
 */
public class NewsBeanImpl implements NewsBean {

    final static Logger log = Logger.getLogger(NewsBeanImpl.class.getName());
    private static final String NEWS_INSERT = "INSERT INTO NEWS (AUTHOR_ID, CAPTION, TEXT) VALUES(";
    private static final String NEWS_SELECT_ID = "SELECT ID, AUTHOR_ID, CAPTION, TEXT FROM NEWS WHERE ID = ";
    private static final String NEWS_SELECT = "SELECT ID, AUTHOR_ID, CAPTION, TEXT FROM NEWS";

    @Override
    public void add(final NewsItem newsItem) throws SQLException {
        Connection conn = null;
        Statement statement = null;
        try {
            conn = SQLHelper.getConnection();
            statement = conn.createStatement();

            statement.execute(NEWS_INSERT
                    + newsItem.getAuthorId()
                    + ", '"
                    + newsItem.getCaption()
                    + "', '"
                    + newsItem.getText()
                    + "')");

        } catch (SQLException e) {
            log.warning(e.getMessage());
            throw e;
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    @Override
    public NewsItem getById(final Long id) throws SQLException {
        Connection conn = null;
        Statement statement = null;
        try {
            conn = SQLHelper.getConnection();
            statement = conn.createStatement();

            final ResultSet set = statement.executeQuery(NEWS_SELECT_ID
                    + id);

            if (set == null) {
                return null;
            }

            if (set.next()) {
                return createNewsItem(set);
            }

            return null;
        } catch (SQLException e) {
            log.warning(e.getMessage());
            throw e;
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    @Override
    public List<NewsItem> getAll() throws SQLException {
        Connection conn = null;
        Statement statement = null;
        try {
            conn = SQLHelper.getConnection();
            statement = conn.createStatement();

            final ResultSet set = statement.executeQuery(NEWS_SELECT);

            if (set == null) {
                return null;
            }

            final List<NewsItem> news = new ArrayList<>();
            while (set.next()) {
                news.add(createNewsItem(set));
            }

            return news;
        } catch (SQLException e) {
            log.warning(e.getMessage());
            throw e;
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    private NewsItem createNewsItem(final ResultSet set) throws SQLException {
        if (set == null) {
            return null;
        }

        final NewsItem newsItem = new NewsItem();
        newsItem.setId(set.getLong(1));
        newsItem.setAuthorId(set.getLong(2));
        newsItem.setCaption(set.getString(3));
        newsItem.setText(set.getString(4));
        return newsItem;
    }
}
