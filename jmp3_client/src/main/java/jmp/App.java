package jmp;

import jmp.clients.UserClient;
import jmp.data.User;
import org.apache.log4j.Logger;

import java.util.Random;

/**
 * Hello world!
 */
public class App {
    private static Logger log = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        final UserClient client = new UserClient();
        try {
            log.info("Create user: ");
            User user = getUser();
            user = client.addUser(getUser());
            log.info("User: " + user);


            log.info("Update user: ");
            user.setFirstName("AnotherFirstName");
            user.setLastName("LastName");
            user = client.updateUser(user);
            log.info("User: " + user);
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
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
