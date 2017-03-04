package org.jmp.part2.patterns.proxy;

import org.jmp.part2.patterns.data.Person;

import java.io.IOException;

/**
 * Created by alex on 04.03.17.
 */
public interface Proxy {
    Person readPerson(String name) throws IOException, ClassNotFoundException;
}
