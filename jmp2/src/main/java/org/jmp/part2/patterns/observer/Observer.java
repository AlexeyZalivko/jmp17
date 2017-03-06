package org.jmp.part2.patterns.observer;

import org.jmp.part2.patterns.observer.data.Event;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 02.03.17.
 */
public abstract class Observer {
    static List<EventListener> listeners = new ArrayList<EventListener>();

    public void register(final EventListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    protected void notifyEvent(final Event event) {
        for (EventListener listener : this.listeners) {
            listener.event(event);
        }
    }

    public abstract void readFile(final File file);
}
