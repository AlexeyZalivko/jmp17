package jmp.animals;

/**
 * Created by alex on 04.02.17.
 */
public interface Animal {

    String PLAY = " play";

    String play();

    String voice();

    default String getPlayString() {
        return getClass().getSimpleName() + PLAY;
    }
}
