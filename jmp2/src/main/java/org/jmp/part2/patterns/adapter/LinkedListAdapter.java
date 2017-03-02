package org.jmp.part2.patterns.adapter;

import java.util.LinkedList;

/**
 * Created by alex on 02.03.17.
 */
public class LinkedListAdapter implements Adapter {

    private LinkedList<Object> list = new LinkedList<Object>();

    public void push(final Object o) {
        this.list.addFirst(o);
    }

    public Object pop() {
        return this.list.removeFirst();
    }
}
