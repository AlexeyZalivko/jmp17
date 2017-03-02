package org.jmp.part2.patterns.adapter;

import java.util.List;

/**
 * Created by alex on 02.03.17.
 */
public interface Adapter {
    void push(Object t);

    Object pop();
}
