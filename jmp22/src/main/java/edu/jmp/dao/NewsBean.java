package edu.jmp.dao;

import edu.jmp.dao.data.NewsItem;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by alex on 09.03.17.
 */
public interface NewsBean {

    void add(final NewsItem item) throws SQLException;

    NewsItem getById(final Long id) throws SQLException;

    List<NewsItem> getAll() throws SQLException;
}
