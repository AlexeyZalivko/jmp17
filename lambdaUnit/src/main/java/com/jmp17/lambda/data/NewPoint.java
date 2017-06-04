package com.jmp17.lambda.data;

import lombok.Data;

/**
 * Created by Alex on 04.06.2017.
 */
@Data
public class NewPoint {
    private int a;
    private int b;

    public NewPoint(final int a, final int b) {
        this.a = a;
        this.b = b;
    }
}
