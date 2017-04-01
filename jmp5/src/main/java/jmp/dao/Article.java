package jmp;

import lombok.Data;

import java.util.Date;

/**
 * Created by alex on 01.04.17.
 */
@Data
public class Article {
    private String title;
    private Date pubDate;
    private String description;
}
