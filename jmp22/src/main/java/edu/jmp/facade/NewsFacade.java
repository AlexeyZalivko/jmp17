package edu.jmp.facade;

import edu.jmp.ui.frames.data.NewsDisplayData;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by alex on 12.03.17.
 */
public interface NewsFacade {
    void addNewNews(final String caption, final String text, final String authorName) throws SQLException;

    List<NewsDisplayData> getAllNews() throws SQLException;
}
