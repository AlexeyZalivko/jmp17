package edu.jmp.facade;

import edu.jmp.dao.AuthorBean;
import edu.jmp.dao.AuthorBeanImpl;
import edu.jmp.dao.NewsBean;
import edu.jmp.dao.NewsBeanImpl;
import edu.jmp.dao.data.Author;
import edu.jmp.dao.data.NewsItem;

import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Created by alex on 12.03.17.
 */
public class NewsFacade {

    public static final long DEFAULT_AUTHOR_ID = 0L;
    private static Logger log = Logger.getLogger(NewsFacade.class.getName());

    private static NewsBean newsBean;
    private static AuthorBean authorBean;

    public NewsFacade() {
        if (newsBean == null) {
            synchronized (this) {
                if (newsBean == null) {
                    newsBean = new NewsBeanImpl();
                }
            }
        }

        if (authorBean == null) {
            synchronized (this) {
                if (authorBean == null) {
                    authorBean = new AuthorBeanImpl();
                }
            }
        }
    }

    public void addNewNews(final String caption, final String text, final String authorName) throws SQLException {
        if (authorName == null || caption == null || text == null) {
            return;
        }

        final NewsItem newsItem = new NewsItem();
        newsItem.setCaption(caption);
        newsItem.setText(text);
        newsItem.setAuthorId(getAuthorId(authorName));

        if (DEFAULT_AUTHOR_ID == newsItem.getAuthorId()) {
            final Author author = new Author();
            author.setName(authorName);

            try {
                authorBean.add(author);
            } catch (SQLException e) {
                log.warning(e.getMessage());
                throw e;
            }
        }

        try {
            newsBean.add(newsItem);
        } catch (SQLException e) {
            log.warning(e.getMessage());
            throw e;
        }
    }

    private Long getAuthorId(final String authorName) {
        try {
            final Author author = authorBean.getAuthorByName(authorName);
            return author.getId();
        } catch (SQLException e) {
            log.warning(e.getMessage());
            return DEFAULT_AUTHOR_ID;
        }
    }
}
