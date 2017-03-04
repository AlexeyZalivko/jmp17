package org.jmp.part2.patterns.decorator;

import org.jmp.part2.patterns.data.Person;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

/**
 * Created by alex on 04.03.17.
 */
public class PersonInputStream implements PersonInputDecorator {
    private InputStream stream;

    public PersonInputStream(final InputStream stream) {
        this.stream = stream;
    }

    @Override
    public Person readPerson() throws IOException, ClassNotFoundException {
        final ObjectInputStream ostream = new ObjectInputStream(stream);
        Person person = (Person) ostream.readObject();
        person = capitalizeName(person);

        return person;
    }
}
