package jmp.database.dbtools;

import jmp.database.dbtools.stubs.*;
import jmp.exceptions.BussinessException;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by alex on 29.03.17.
 * <p>
 * <i>Singleton.</i> Init database after creating.
 */
public class DBInit {

    private static Logger log = Logger.getLogger(DBInit.class.toString());

    public static final String CREATE_SQL = "create.sql";
    private static DBInit dbInit = null;

    private static Integer MIN_FRIENDSHIPS = 70000;
    private static Integer MIN_LIKES = 300000;
    private static String TARGET_YEAR = "2015";

    private DBInit() {
    }

    /**
     * Get instance.
     *
     * @return
     */
    public static void init(final Connection connection) throws IOException, SQLException, BussinessException, ParseException {
        if (dbInit == null) {
            synchronized (DBInit.class) {
                if (dbInit == null) {
                    dbInit = new DBInit();
                }
            }
        }

        log.info("Loading scheme...");

        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        final List<String> sqlFileContent = IOUtils.readLines(loader.getResourceAsStream(CREATE_SQL), StandardCharsets.UTF_8);

        if (sqlFileContent == null) {
            return;
        }

        final String scheme = String.join(" ", sqlFileContent);
        log.info(scheme);

        final Statement statement = connection.createStatement();
        statement.executeUpdate(scheme);

        log.info("Init finished");
        log.info("Starting data creating...");
        final Stub userStub = new UserStub();
        userStub.addNewOne(connection);
        final FriendshipStub friendshipStub = new FriendshipStub();
        friendshipStub.addNewOne(connection);
        final Stub articlesStub = new ArticleStub();
        articlesStub.addNewOne(connection);
        final LikesStub likesStub = new LikesStub();
        likesStub.addNewOne(connection);
    }

}
