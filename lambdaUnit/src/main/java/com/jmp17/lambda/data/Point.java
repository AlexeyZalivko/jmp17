package com.jmp17.lambda.data;

import lombok.Data;

/**
 * Created by Alex on 04.06.2017.
 */
@Data
public class Point {
    private int x;
    private int y;

    public Point(final int x, final int y) {
        this.x = x;
        this.y = y;
    }
}
