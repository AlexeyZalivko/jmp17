package org.jmp.part2.patterns.observer.data;

/**
 * Created by alex on 02.03.17.
 */
public class Event {

    public enum Type {
        ID_LINES_COUNT_EVENT,
        ID_LONGEST_WORD_EVENT,
        ID_REVERSE_WORD_EVENT
    }

    private Type type;
    private String string;

    public Event(final Type type, final String string) {
        this.string = string;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Event{" +
                "string='" + string + '\'' +
                '}';
    }
}
