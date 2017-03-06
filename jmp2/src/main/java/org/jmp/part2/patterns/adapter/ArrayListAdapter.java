package org.jmp.part2.patterns.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by alex on 02.03.17.
 */
public class ArrayListAdapter implements Adapter {
    private static Logger log = Logger.getLogger(ArrayListAdapter.class.toString());

    private List<Object> list = new ArrayList<Object>();

    public void push(final Object t) {
        this.list.add(t);
    }

    public Object pop() {
        Object obj = null;
        try {
            obj = this.list.remove(this.list.size() - 1);
        } catch (IndexOutOfBoundsException exc) {
            log.warning(exc.getMessage());
            throw exc;
        } catch (UnsupportedOperationException exc) {
            log.warning(exc.getMessage());
            throw exc;
        } finally {
            return obj;
        }
    }
}
