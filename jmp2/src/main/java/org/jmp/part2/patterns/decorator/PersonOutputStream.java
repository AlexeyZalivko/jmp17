package org.jmp.part2.patterns.decorator;

import java.io.OutputStream;

/**
 * Created by alex on 04.03.17.
 */
public interface PersonOutputStream {
    void writePerson(final OutputStream stream);
}
