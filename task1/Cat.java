/**
 * Created by alex on 04.02.17.
 */
public class Cat implements Animal {

    private static String VOICE = "meow";

    @Override
    public String play() {
        return getPlayString();
    }

    @Override
    public String voice() {
        return VOICE;
    }
}
