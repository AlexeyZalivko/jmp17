package edu.jmp.dao;

import edu.jmp.dao.data.NewsItem;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.Assert.*;

/**
 * Created by alex on 12.03.17.
 */
public class NewsBeanImplTest {

    private static Logger log = Logger.getLogger(NewsBeanImplTest.class.getName());

    public static final long ID = 1L;
    private static final String NEWS_TEXT = "News text";
    private static final String CAPTION = "Caption";
    private static final long AUTHOR_ID = 1988L;

    private static NewsItem news;
    private static NewsBean dbManager;

    @BeforeClass
    public static void init() {
        news = new NewsItem();

        news.setAuthorId(AUTHOR_ID);
        news.setCaption(CAPTION);
        news.setText(NEWS_TEXT);

        dbManager = new NewsBeanImpl();
    }

    @Test
    public void testDBManager() throws SQLException {
        dbManager.add(news);
        final NewsItem item = dbManager.getById(ID);

        log.info("Selected info: " + item);

        assertNotNull(item);
        assertNotNull(item.getCaption());
        assertNotNull(item.getText());
    }

    @Test
    public void testAllNews() throws SQLException {
        final List<NewsItem> news = dbManager.getAll();

        assertNotNull(news);
        assertEquals(news.size(), 4);
    }

}