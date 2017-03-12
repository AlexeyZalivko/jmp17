package edu.jmp.dao;

import edu.jmp.dao.data.NewsItem;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;
import java.util.logging.Logger;

import static org.junit.Assert.*;

/**
 * Created by alex on 12.03.17.
 */
public class NewsDBManagerTest {

    private static Logger log = Logger.getLogger(NewsDBManagerTest.class.getName());

    private static final String NEWS_TEXT = "News text";
    private static final String CAPTION = "Caption";
    private static final long ID = 16L;
    private static final long AUTHOR_ID = 1988L;

    private static NewsItem news;
    private static NewsDBManager dbManager;

    @BeforeClass
    public static void init() {
        news = new NewsItem();

        news.setId(ID);
        news.setAuthorId(AUTHOR_ID);
        news.setCaption(CAPTION);
        news.setText(NEWS_TEXT);

        dbManager = new NewsDBManager();
    }

    @Test
    public void testDBManager() throws SQLException {
        dbManager.add(news);
        final NewsItem item = dbManager.getById(ID);

        log.info("Selected info: " + item);

        assertNotNull(item);
        assertEquals(item.getId(), ID);
        assertEquals(item.getCaption(), CAPTION);
        assertEquals(item.getText(), NEWS_TEXT);
    }

}