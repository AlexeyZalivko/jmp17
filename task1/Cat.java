/**
 * Created by alex on 04.02.17.
 */
public class Cat implements Animal {
    @Override
    public String play() {
        return "cat play";
    }

    @Override
    public String voice() {
        return "meow";
    }
}
