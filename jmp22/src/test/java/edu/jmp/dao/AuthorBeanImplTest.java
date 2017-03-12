package edu.jmp.dao;

import edu.jmp.dao.data.Author;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.Assert.*;

/**
 * Created by alex on 12.03.17.
 */
public class AuthorBeanImplTest {
    private static Logger log = Logger.getLogger(AuthorBeanImplTest.class.getName());

    public static final long ID = 1L;
    public static final String AUTHOR_NAME = "Alex";
    private static Author author;

    private static AuthorBean bean;

    @BeforeClass
    public static void init() {
        bean = new AuthorBeanImpl();

        author = new Author();
        author.setId(ID);
        author.setName(AUTHOR_NAME);
    }

    @Test
    public void insertGetTest() throws SQLException {
        bean.add(author);
        final Author loadedAuthor = bean.getById(ID);

        log.info(loadedAuthor.toString());

        assertNotNull(loadedAuthor);
        assertNotNull(loadedAuthor.getName());
    }

    @Test
    public void testGetAll() throws SQLException {
        final List<Author> authors = bean.getAll();

        assertNotNull(authors);
        assertEquals(authors.size(), 3);
    }

}