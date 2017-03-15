package jmp.service;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import jmp.database.dao.UserBean;
import jmp.database.dao.UserBeanImpl;
import jmp.database.data.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by alex on 15.03.17.
 */
@Path("/user")
public class UserService {

    private static Logger log = Logger.getLogger(UserService.class.getName());

    private Gson gson = new Gson();

    private UserBean userBean = new UserBeanImpl();


    @GET
    @Path("/{id}")
    public Response getUser(@PathParam("id") Long id) {

        String result = null;
        User user = null;
        try {
            user = userBean.getUserById(id);
            result = gson.toJson(user);
        } catch (SQLException e) {
            log.warning(e.getMessage());
            result = e.getMessage();
        }

        return Response.status(200).entity(result).build();
    }

    @GET
    @Path("/")
    public Response getUsers() {

        String result = null;
        List<User> users = null;
        try {
            users = userBean.getAllUsers();
            result = gson.toJson(users);
        } catch (SQLException e) {
            log.warning(e.getMessage());
            result = e.getMessage();
        }

        return Response.status(200).entity(result).build();
    }

}
