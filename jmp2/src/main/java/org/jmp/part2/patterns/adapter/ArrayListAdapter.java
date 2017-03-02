package org.jmp.part2.patterns.adapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 02.03.17.
 */
public class ArrayListAdapter implements Adapter {

    private List<Object> list = new ArrayList<Object>();

    public void push(final Object t) {
        this.list.add(t);
    }

    public Object pop() {
        if (this.list.size() > 0) {
            return this.list.remove(this.list.size() - 1);
        }
        return null;
    }
}
