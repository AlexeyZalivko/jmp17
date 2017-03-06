package org.jmp.part2.patterns.adapter;

import java.util.Stack;

/**
 * Created by alex on 02.03.17.
 */
public class StackAdapter implements Adapter{

    private Stack<Object> list = new Stack<Object>();

    public void push(final Object o) {
        this.list.push(o);
    }

    public Object pop() {
        return this.list.pop();
    }
}
