package org.jmp.part2.patterns.observer;

import org.jmp.part2.patterns.observer.data.Event;

/**
 * Created by alex on 02.03.17.
 */
public interface EventListener {

    void event(final Event event);
}
