package org.jmp.part2.patterns.decorator;

import org.jmp.part2.patterns.data.Person;

import java.io.IOException;

/**
 * Created by alex on 04.03.17.
 */
public interface PersonInputDecorator extends PersonDecorator {

    Person readPerson() throws IOException, ClassNotFoundException;
}
