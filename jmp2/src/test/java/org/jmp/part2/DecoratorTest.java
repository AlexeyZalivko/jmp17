package org.jmp.part2;

import org.jmp.part2.patterns.data.Person;
import org.jmp.part2.patterns.decorator.PersonDecorator;
import org.jmp.part2.patterns.decorator.PersonInputDecorator;
import org.jmp.part2.patterns.decorator.PersonInputStream;
import org.jmp.part2.patterns.decorator.PersonOutputStream;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.logging.Logger;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by alex on 04.03.17.
 */
public class DecoratorTest {
    private final static Logger log = Logger.getLogger(DecoratorTest.class.getName());

    private static final String USER_DIR = "user.dir";
    private static final String TEST_USER_NAME = "User name tesT";
    private static final String DELIMITER = "/";

    private FileOutputStream outputStream = null;
    private FileInputStream inputStreamStream = null;
    private Person person;

    @Before
    public void init() throws FileNotFoundException {
        final String fullPath = buildPath();

        try {
            outputStream = new FileOutputStream(fullPath);
            inputStreamStream = new FileInputStream(fullPath);
        } catch (FileNotFoundException e) {
            log.warning(e.getMessage());
            throw e;
        }

        this.person = new Person();
        person.setName(TEST_USER_NAME);

    }

    @AfterClass
    public static void clean() {

        final File file = new File(buildPath());
        if (file != null) {
            file.delete();
        }
    }


    @Test
    public void readWriteTest() throws Exception {

        final PersonOutputStream write = new PersonOutputStream(outputStream);
        final PersonInputDecorator read = new PersonInputStream(inputStreamStream);

        write.writePerson(person);
        final Person anotherPerson = read.readPerson();

        for (String namePart : anotherPerson.getName().split(PersonDecorator.SPACE_DELIMITER)) {
            if (namePart.charAt(0) != Character.toUpperCase(namePart.charAt(0))) {
                throw new Exception(namePart.charAt(0) + " is in the lowwer case");
            }
        }

        log.info("Source name: " + person.getName());
        log.info("After read/write name: " + anotherPerson.getName());
    }

    private static String buildPath() {
        final String currentPath = System.getProperty(USER_DIR);
        return currentPath + DELIMITER + TEST_USER_NAME + PersonDecorator.EXTENSION;
    }
}
