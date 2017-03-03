package org.jmp.part2.patterns.observer.listener;

import org.jmp.part2.patterns.observer.EventListener;
import org.jmp.part2.patterns.observer.data.Event;

import java.util.logging.Logger;

/**
 * Created by alex on 02.03.17.
 */
public class ConsoleListener implements EventListener {
    private static Logger log = Logger.getLogger(ConsoleListener.class.getName());

    private Long id;

    public ConsoleListener(final Long id) {
        this.id = id;
    }

    @Override
    public void event(final Event event) {
        log.info(id + ": " + event);
    }
}
