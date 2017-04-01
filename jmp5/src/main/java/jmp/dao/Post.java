package jmp.dao;

import lombok.Data;

/**
 * Created by alex on 01.04.17.
 */
@Data
public class Post {
    private Long id;
    private Long userId;
    private String text;
}
