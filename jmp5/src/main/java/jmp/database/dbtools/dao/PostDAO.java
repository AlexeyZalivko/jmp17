package jmp.database.dbtools.dao;

import jmp.dao.Article;

import java.util.List;

/**
 * Created by alex on 01.04.17.
 */
public interface ArticleDAO {
    List<Article> getAllArticles();

    void addNewArticle(final Article article);
}
