/**
 * Created by alex on 04.02.17.
 */
public class Dog implements Animal {
    @Override
    public String play() {
        return "dog play";
    }

    @Override
    public String voice() {
        return "gav!";
    }
}
