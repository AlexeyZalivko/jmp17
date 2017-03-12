package edu.jmp.dao.management;


import edu.jmp.dao.data.NewsItem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by alex on 09.03.17.
 */
public class NewsBeainImpl implements NewsBean {

    public static final String NEWS_CAPTION = "News #";
    public static final String NEWS_DETAILS = ". News details.";

    private List<NewsItem> news = new ArrayList<NewsItem>();

    public NewsBeainImpl() {
        for (int i = 0; i < 10; i++) {
            final NewsItem newsItem = new NewsItem();
            newsItem.setId(Long.valueOf(i));
            newsItem.setCaption(NEWS_CAPTION + i);
            newsItem.setText(NEWS_CAPTION + i + NEWS_DETAILS);

            news.add(newsItem);
        }
    }

    @Override
    public List<NewsItem> getAllNews() {
        return new ArrayList<>(news);
    }

    @Override
    public NewsItem getNewsById(final Long id) {
        final List<NewsItem> result = news.stream()
                .filter(x -> x.getId().equals(id))
                .collect(Collectors.toList());
        return (result != null && result.size() >= 1) ? result.get(0) : null;
    }

    @Override
    public void addNews(NewsItem news) {

    }
}
