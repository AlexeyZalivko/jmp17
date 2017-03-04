package org.jmp.part2;

import org.jmp.part2.patterns.data.Person;
import org.jmp.part2.patterns.proxy.Proxy;
import org.jmp.part2.patterns.proxy.ProxyImpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by alex on 04.03.17.
 */
public class ProxyTest {
    private static Logger log = Logger.getLogger(ProxyTest.class.getName());

    private static String currentDir;

    private static List<String> testUsers = new ArrayList<String>();

    @BeforeClass
    public static void init() throws Exception {
        currentDir = System.getProperty(ProxyImpl.USER_DIR);

        if (currentDir == null) {
            log.warning("Current location is not found");
            throw new Exception("Current location is not found");
        }

        testUsers.add("Alex");
        testUsers.add("TestUser");
        testUsers.add("Test_User_2");
        testUsers.add("Another test user");

        for (String name : testUsers) {
            final Person person = new Person();
            person.setName(name);

            FileOutputStream fout = null;
            ObjectOutputStream ostream = null;
            try {
                fout = new FileOutputStream(ProxyImpl.buildPath(name));
                ostream = new ObjectOutputStream(fout);
                ostream.writeObject(person);
            } catch (Exception exc) {
                log.warning(exc.getMessage());
                throw exc;
            } finally {
                if (ostream != null) {
                    ostream.close();
                }
                if (fout != null) {
                    fout.close();
                }
            }
        }
    }

    @Test
    public void readPersonFromFile() {
        final String nameFirstPerson = testUsers.get(0);

        final Proxy proxy = new ProxyImpl();

        try {
            final Person firstPerson = proxy.readPerson(nameFirstPerson);
            log.info(nameFirstPerson + " loaded: " + firstPerson.toString());
        } catch (final IOException e) {
            log.warning(e.getMessage());
        } catch (final ClassNotFoundException e) {
            log.warning(e.getMessage());
        }

        final File firstNameFile = new File(ProxyImpl.buildPath(nameFirstPerson));
        if (firstNameFile != null) {
            firstNameFile.delete();
        }

        final List<Person> persons = new ArrayList<Person>();
        try {
            for (String name : testUsers) {
                persons.add(proxy.readPerson(name));
            }
        } catch (final IOException e) {
            log.warning(e.getMessage());
        } catch (final ClassNotFoundException e) {
            log.warning(e.getMessage());
        }

        assertThat(persons.size(), is(testUsers.size()));
    }

    @AfterClass
    public static void clean() {
        for (String name : testUsers) {
            final File file = new File(ProxyImpl.buildPath(name));
            if (file != null) {
                file.delete();
            }
        }
    }
}
