package edu.jmp.dao.data;

import lombok.Data;

/**
 * Created by alex on 09.03.17.
 */
@Data
public class NewsItem {
    private Long id;
    private Long authorId;
    private String caption;
    private String text;
}
