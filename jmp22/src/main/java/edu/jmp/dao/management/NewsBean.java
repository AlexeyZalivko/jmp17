package edu.jmp.dao.management;

import edu.jmp.dao.data.NewsItem;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by alex on 09.03.17.
 */
public interface NewsBean {
    List<NewsItem> getAllNews() throws SQLException ;

    NewsItem getNewsById(final Long id) throws SQLException ;

    void addNews(final NewsItem news) throws SQLException;
}
