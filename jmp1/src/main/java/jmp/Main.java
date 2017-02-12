package jmp;

import jmp.animals.Animal;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static Logger log = Logger.getLogger(Main.class);

    public static void main(String[] args) throws ClassNotFoundException {

        final MyClassloader myLoader = new MyClassloader(Animal.class, ClassLoader.getSystemClassLoader());

        final List<Animal> animals = new ArrayList<>();

        try {
            for (int i = 0; i < 4; i++) {
                final Class animal = myLoader.loadClass((i % 2 == 0) ? "Dog": "Cat");
                animals.add((Animal) animal.newInstance());
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        for (Animal animal : animals) {
            log.info(animal.play());
            log.info(animal.voice());
            /*System.out.println(animal.play());
            System.out.println(animal.voice());*/
        }
    }

}
