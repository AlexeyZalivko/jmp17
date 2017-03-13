package edu.jmp.facade;

import edu.jmp.dao.NewsBean;
import edu.jmp.dao.NewsBeanImpl;
import edu.jmp.ui.frames.data.NewsDisplayData;
import edu.jmp.ui.frames.data.NewsListDisplayData;
import edu.jmp.ui.frames.data.NewsTableDisplayData;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by alex on 12.03.17.
 */
public class NewsFacadeImplTest {

    private static NewsBean bean;

    @BeforeClass
    public static void init() {
        bean = new NewsBeanImpl();
    }

    @Test
    public void testAllNews() throws SQLException {
        final NewsFacade facade = new NewsFacadeImpl();
        final List<NewsDisplayData> data = facade.getAllNews();

        assertNotNull(data);
        assertEquals(data.size(), bean.getAll().size());
    }


}