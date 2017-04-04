package jmp.database.dbtools.dao;

import jmp.dao.Article;
import jmp.dao.Post;
import jmp.dao.User;
import jmp.exceptions.BussinessException;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by alex on 01.04.17.
 */
public interface PostDAO {
    List<Post> getAllPosts() throws BussinessException, SQLException;

    List<Post> getAllUserPost(final Long userId) throws BussinessException, SQLException;

    void addNewPost(final Post post) throws BussinessException, SQLException;

    Post getPostByArticle(final Article article, final User user);
}
