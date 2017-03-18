package jmp;

import jmp.clients.ImageClient;
import jmp.clients.UserClient;
import jmp.data.User;
import org.apache.log4j.Logger;

import java.util.Random;

/**
 * Hello world!
 */
public class App {
    private static Logger log = Logger.getLogger(App.class.getName());

    private static UserClient client = new UserClient();
    private static ImageClient imageClient = new ImageClient();

    public static void main(String[] args) {
        try {
            User user = createUser();
            user = updateUser(user);

            User userWithImage = uploadImage(user);
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
    }

    private static User uploadImage(final User user) {
        final String imageName = "thumb";
        final User result = imageClient.uploadImage(user, imageName);
        log.info("User with logo: " + result);
        return result;
    }

    private static User updateUser(final User user) throws Exception {
        user.setLastName("LastName");
        user.setFirstName("AnotherFirstName");
        log.info("Update user: " + user);
        final User updateUserNew = client.updateUser(user);
        log.info("User: " + user);
        return updateUserNew;
    }

    private static User createUser() throws Exception {
        log.info("Create user: ");
        User user = getUser();
        user = client.addUser(getUser());
        log.info("User: " + user);
        return user;
    }

    private static User getUser() {
        final User user = new User();
        final Random rnd = new Random();

        user.setFirstName("First_" + rnd.nextInt(2048));
        user.setLastName("Last_" + rnd.nextInt(2048));
        user.setLogin("login");
        user.setMail("mail@mail.net");

        return user;
    }
}
