package org.jmp.part2;

import org.jmp.part2.patterns.observer.Observer;
import org.jmp.part2.patterns.observer.ObserverImpl;
import org.jmp.part2.patterns.observer.listener.ConsoleListener;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 02.03.17.
 */
public class ObserverTest {

    private static final int MAX_LISTENERS = 3;
    private static final String FILE_NAME = "src/test/resources/test.txt";

    private Observer observer = new ObserverImpl();

    @Test
    public void testObserver() {
        final List<ConsoleListener> listeners = new ArrayList<>();

        for (int i = 0; i < MAX_LISTENERS; i++) {
            final ConsoleListener listener = new ConsoleListener(Long.valueOf(i));
            listeners.add(listener);
            observer.register(listener);
        }

        observer.readFile(new File(FILE_NAME));

    }
}
