package jmp.database.dbtools.stubs;

import javafx.geometry.Pos;
import jmp.dao.Like;
import jmp.dao.Post;
import jmp.dao.User;
import jmp.database.dbtools.dao.*;
import jmp.exceptions.BussinessException;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by alex on 01.04.17.
 */
public class LikesStub implements Stub {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SS");

    private UserDAO userDAO = new UserDAOImpl();
    private PostDAO postDAO = new PostDAOImpl();
    private LikesDAO likesDAO = new LikesDAOImpl();

    @Override
    public void addNewOne(final Connection connection) throws SQLException, ParseException, BussinessException {
        final List<User> users = userDAO.getAllUsers();
        final Random rnd = new Random(1);
        for (User user : users) {
            final List<Post> posts = postDAO.getAllUserPost(user.getId());
            if (posts != null) {
                final int maxCount = rnd.nextInt(posts.size());
                for (int i = 0; i < posts.size() && i < maxCount; i++) {
                    final Post post = posts.get(i);
                    final Like like = new Like();
                    like.setUserId(user.getId());
                    like.setPostId(post.getId());
                    like.setTimestamp(generateLikeData(rnd.nextInt(12), rnd.nextInt(28), rnd.nextInt(23), rnd.nextInt(59)));
                    likesDAO.addLike(like);
                }
            }
        }

    }

    private Date generateLikeData(final int month, final int day, final int hour, final int minutes) throws ParseException {

        final String[] params = new String[]{
                "2015",
                String.format("%02d", month),
                String.format("%02d", day),
                String.format("%02d", hour),
                String.format("%02d", minutes)
        };
        final String resultDate = MessageFormat.format("{0}-{1}-{2} {3}:{4}:00.00", params);

        return sdf.parse(resultDate);
    }
}
