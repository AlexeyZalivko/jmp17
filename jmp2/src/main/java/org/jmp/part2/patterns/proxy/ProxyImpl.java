package org.jmp.part2.patterns.proxy;

import org.jmp.part2.patterns.data.Person;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alex on 04.03.17.
 */
public class ProxyImpl implements Proxy {

    public static final String USER_DIR = "user.dir";
    public static final String FS_DELIMETER = "/";
    public static final String EXTENSION = ".dta";

    private static Map<String, Person> loadedPersons = new HashMap<String, Person>();

    private static String currentPath;

    static  {
        currentPath = System.getProperty(USER_DIR);
    }

    @Override
    public Person readPerson(final String name) throws IOException, ClassNotFoundException {

        if (loadedPersons.containsKey(name)) {
            return loadedPersons.get(name);
        }

        final FileInputStream fin = new FileInputStream(buildPath(name));
        final ObjectInputStream objectInputStream = new ObjectInputStream(fin);

        final Person person = (Person) objectInputStream.readObject();

        if (person != null) {
            loadedPersons.put(name, person);
        }

        return person;
    }

    public static String buildPath(final String name) {
        return currentPath + FS_DELIMETER + name + EXTENSION;
    }
}
