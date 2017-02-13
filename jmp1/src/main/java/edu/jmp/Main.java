package edu.jmp;

import edu.jmp.animals.Animal;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static Logger log = Logger.getLogger(Main.class);

    public static void main(String[] args) throws ClassNotFoundException {
        final Main main = new Main();


        for (Animal animal : main.getAnimals()) {
            log.info(animal.play());
            log.info(animal.voice());
        }
    }

    private List<Animal> getAnimals() {
        final List<Animal> animals = new ArrayList<>();

        final MyClassloader myLoader = new MyClassloader(Animal.class, ClassLoader.getSystemClassLoader());

        try {
            for (int i = 0; i < 4; i++) {
                final Class animal = myLoader.loadClass((i % 2 == 0) ? "Dog" : "Cat");
                animals.add((Animal) animal.newInstance());
            }
        } catch (InstantiationException e) {
            log.error("Can't to instance Animal: {}", e);
        } catch (IllegalAccessException e) {
            log.error("Illegal access: {}", e);
        } catch (ClassNotFoundException e) {
            log.error("Can't ot fing class: {}", e);
        }

        return animals;
    }

}
