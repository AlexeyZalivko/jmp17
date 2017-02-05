import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class Main {

    private static Logger log = Logger.getLogger(Main.class);

    public static void main(String[] args) throws ClassNotFoundException {
        final String path = System.getProperty("user.dir");

        MyClassloader myLoader = new MyClassloader(path, ClassLoader.getSystemClassLoader());

        final List<Animal> animals = new ArrayList<>();

        final Class dog = myLoader.loadClass("Dog");
        final Class cat = myLoader.loadClass("Cat");

        try {
            for (int i = 0; i < 4; i++) {
                if (i % 2 == 0) {
                    animals.add((Animal) dog.newInstance());
                } else {
                    animals.add((Animal) cat.newInstance());
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        for (Animal animal : animals) {
            log.info(animal.play());
            log.info(animal.voice());
        }
    }

}
