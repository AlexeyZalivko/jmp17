package org.jmp.part2;

import org.jmp.part2.patterns.adapter.Adapter;
import org.jmp.part2.patterns.adapter.ArrayListAdapter;
import org.jmp.part2.patterns.adapter.LinkedListAdapter;
import org.jmp.part2.patterns.adapter.StackAdapter;
import org.junit.Test;

import java.util.Random;

/**
 * Created by alex on 02.03.17.
 */
public class AdapterTest {

    @Test
    public void arrayListAdapterTest() throws InstantiationException, IllegalAccessException {
        final Adapter arrayListAdapter = generateAdapter(ArrayListAdapter.class);
        log(arrayListAdapter);
    }

    @Test
    public void LinkedListAdapterTest() throws InstantiationException, IllegalAccessException {
        final Adapter linkedListAdapter = generateAdapter(LinkedListAdapter.class);
        log(linkedListAdapter);
    }

    @Test
    public void StackAdapterTest() throws InstantiationException, IllegalAccessException {
        final Adapter stackListAdapter = generateAdapter(StackAdapter.class);
        log(stackListAdapter);
    }

    private Adapter generateAdapter(final Class adapterClass) throws IllegalAccessException, InstantiationException {
        final Adapter adapter = (Adapter) adapterClass.newInstance();

        final Random rnd = new Random();

        for (int i = 0; i < rnd.nextInt(10) + 3; i++) {
            adapter.push(rnd.nextInt(1024));
        }

        return adapter;
    }

    private void log(final Adapter adapter) {

        try {
            System.out.println("-==================-");
            Object element = adapter.pop();
            boolean isContinue = element != null;
            while (isContinue) {
                element = adapter.pop();
                isContinue = element != null;
                System.out.println(element);
            }
        } catch (Exception e) {

        }
    }
}
