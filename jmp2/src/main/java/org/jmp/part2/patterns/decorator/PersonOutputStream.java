package org.jmp.part2.patterns.decorator;

import org.jmp.part2.patterns.data.Person;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * Created by alex on 04.03.17.
 */
public class PersonOutputStream implements PersonOutputDecorator {

    private OutputStream stream;

    public PersonOutputStream(final OutputStream stream) {
        this.stream = stream;
    }

    @Override
    public void writePerson(final Person person) throws IOException {
        final Person localPerson = capitalizeName(person);
        final ObjectOutputStream ostream = new ObjectOutputStream(stream);
        ostream.writeObject(person);
    }
}
