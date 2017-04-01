package jmp.dao;

import lombok.Data;

import java.util.Date;

/**
 * Created by alex on 01.04.17.
 */
@Data
public class Likes {
    private Long postId;
    private Long userId;
    private Date timestamp;
}
