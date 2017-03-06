package org.jmp.part2.patterns.decorator;

import org.jmp.part2.patterns.data.Person;

import java.io.IOException;

/**
 * Created by alex on 04.03.17.
 */
public interface PersonOutputDecorator extends PersonDecorator {

    void writePerson(final Person person) throws IOException;
}
