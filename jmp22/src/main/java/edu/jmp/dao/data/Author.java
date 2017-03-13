package edu.jmp.dao.data;

import lombok.Data;

/**
 * Created by alex on 12.03.17.
 */
@Data
public class Author extends NewsItem {
    private Long id;
    private String name;
}
