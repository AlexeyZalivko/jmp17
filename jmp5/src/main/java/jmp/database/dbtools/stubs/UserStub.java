package jmp.database.dbtools.stubs;

import jmp.dao.User;
import jmp.database.dbtools.dao.UserDAO;
import jmp.database.dbtools.dao.UserDAOImpl;
import jmp.exceptions.BussinessException;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Created by alex on 29.03.17.
 */
public class UserStub implements Stub {

    private static Logger log = Logger.getLogger(UserStub.class.toString());
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private static Integer MIN_USERS = 1000;

    public static final String NAMES_TXT = "Names.txt";
    public static final String SURNAMES_TXT = "Surnames.txt";

    private static List<String> names = new ArrayList<String>();
    private static List<String> surnames = new ArrayList<String>();

    private UserDAO userDAO = new UserDAOImpl();

    static {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            names = IOUtils.readLines(classLoader.getResourceAsStream(NAMES_TXT), StandardCharsets.UTF_8);
            surnames = IOUtils.readLines(classLoader.getResourceAsStream(SURNAMES_TXT), StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.warning("Initializing names error: " + e.getMessage());
        }
    }

    public void addNewOne(final Connection connection) throws BussinessException {
        final Random rnd = new Random(MIN_USERS);
        final Random rndYear = new Random(1965);

        final int usersCount = Math.abs(rnd.nextInt(MIN_USERS) + MIN_USERS);

        try {
            for (int i = 0; i < usersCount; i++) {
                final int namePos = rnd.nextInt(names.size());
                final int surnamePos = rnd.nextInt(surnames.size());
                final User user;
                user = getNew(namePos, surnamePos, rndYear.nextInt(2010 - 1965) + 1965);
                userDAO.addUser(user);
            }
        } catch (ParseException e) {
            log.warning(e.getMessage());
            throw new BussinessException(e.getMessage());
        } catch (SQLException e) {
            log.warning(e.getMessage());
            throw new BussinessException(e.getMessage());
        }
    }

    public User getNew(final int nameId, final int surnameId, final int year) throws ParseException {

        final User user = new User();
        user.setName(names.get(nameId));
        user.setSurname(surnames.get(surnameId));
        user.setBirthday(sdf.parse(generateDOB(year)));
        return user;
    }

    private String generateDOB(final int year) {
        final Random rnd = new Random(1);

        final String[] params = new String[]{
                String.valueOf(year),
                String.format("%02d", rnd.nextInt(12)),
                String.format("%02d", rnd.nextInt(28))
        };

        return MessageFormat.format("{0}-{1}-{2}", params);
    }
}
