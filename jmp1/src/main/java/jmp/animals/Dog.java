package jmp.animals;

/**
 * Created by alex on 04.02.17.
 */
public class Dog implements Animal {

    private static String VOICE = "gav";

    @Override
    public String play() {
        return getPlayString();
    }

    @Override
    public String voice() {
        return VOICE;
    }
}
