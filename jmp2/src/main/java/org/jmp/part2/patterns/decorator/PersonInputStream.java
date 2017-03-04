package org.jmp.part2.patterns.decorator;

import org.jmp.part2.patterns.data.Person;

import java.io.InputStream;

/**
 * Created by alex on 04.03.17.
 */
public interface PersonInputStream {

    Person readPerson(final InputStream stream);
}
