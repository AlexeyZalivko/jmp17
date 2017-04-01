package jmp.database.dbtools.stubs;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Created by alex on 29.03.17.
 */
public class UserStub implements Stub {

    private static Logger log = Logger.getLogger(UserStub.class.toString());

    private static Integer MIN_USERS = 1000;

    private static String ADD_USER_SQL = "INSERT INTO Users (NAME, SURNAME, BIRTHDAY) VALUES (";

    private static List<String> names = new ArrayList<String>();
    private static List<String> surnames = new ArrayList<String>();

    static {
        names.addAll(Arrays.asList(new String[]{"Alex", "Eugene", "Ivan", "Oleg", "Martin", "John", "Livan", "Omar", "Omar", "Olga", "Mary", "Liza", "Jenifer"}));
        surnames.addAll(Arrays.asList(new String[]{"Ivanov", "Petrov", "Sidorov", "Scorcese", "Doe", "Olvin", "White", "Jako", "Redo", "State", "Yappo"}));
    }

    public void addNewOne(final Connection connection) throws SQLException {
        final Random rnd = new Random(MIN_USERS);
        final Random rndYear = new Random(1965);

        final int usersCount = Math.abs(rnd.nextInt(MIN_USERS) + MIN_USERS);

        for (int i = 0; i < usersCount; i++) {
            final int namePos = rnd.nextInt(names.size());
            final int surnamePos = rnd.nextInt(surnames.size());
            final String userString = getNew(namePos, surnamePos, rndYear.nextInt(2010 - 1965) + 1965);
            log.info(userString);
            /*final Statement statement = connection.createStatement();
            statement.executeUpdate(userString);*/
        }
    }

    public String getNew(final int nameId, final int surnameId, final int year) {

        return ADD_USER_SQL + "'"
                + names.get(nameId)
                + "', '"
                + surnames.get(surnameId)
                + "', '"
                + generateDOB(year)
                + "')";
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
