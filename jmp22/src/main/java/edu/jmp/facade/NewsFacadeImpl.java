package edu.jmp.facade;

import edu.jmp.dao.AuthorBean;
import edu.jmp.dao.AuthorBeanImpl;
import edu.jmp.dao.NewsBean;
import edu.jmp.dao.NewsBeanImpl;
import edu.jmp.dao.data.Author;
import edu.jmp.dao.data.NewsItem;
import edu.jmp.ui.frames.data.NewsDisplayData;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created by alex on 12.03.17.
 */
public class NewsFacadeImpl implements NewsFacade {

    public static final long DEFAULT_AUTHOR_ID = 0L;
    private static Logger log = Logger.getLogger(NewsFacadeImpl.class.getName());

    private static NewsBean newsBean;
    private static AuthorBean authorBean;

    public NewsFacadeImpl() {
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

    @Override
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

    @Override
    public List<NewsDisplayData> getAllNews() throws SQLException {
        final List<NewsDisplayData> list = new ArrayList<>();

        List<NewsItem> allNews = null;
        List<Author> allAuthors = null;
        try {
            allNews = newsBean.getAll();
            allAuthors = authorBean.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (allNews == null || allAuthors == null) {
            return list;
        }

        for (NewsItem item : allNews) {
            list.add(getDisplayNews(item, allAuthors));
        }

        return list;
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

    private NewsDisplayData getDisplayNews(final NewsItem news, final List<Author> authors) {
        NewsDisplayData result = new NewsDisplayData();

        result.setCaption(news.getCaption());
        result.setText(news.getText());

        log.info("News: " + news);

        final Author author = authors.stream()
                .filter(x -> x.getId().equals(news.getAuthorId()))
                .collect(Collectors.toList())
                .get(0);

        result.setAuthor(author);

        return result;
    }
}
