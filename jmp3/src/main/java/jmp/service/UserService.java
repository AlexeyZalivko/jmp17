package jmp.service;

import com.google.gson.Gson;
import jmp.database.dao.UserBean;
import jmp.database.dao.UserBeanImpl;
import jmp.database.data.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_XML)
    public Response addNewUser(final User user) {
        log.info(user.toString());

        User result = null;
        try {
            result = userBean.addUser(user);
        } catch (SQLException e) {
            log.warning(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }

        return Response.status(Response.Status.OK).entity(result).build();
    }

    @PUT
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(final User user) {
        log.info(user.toString());
        System.out.println(user);

        User updatedUser = null;
        try {
            updatedUser = userBean.updateUser(user);
        } catch (SQLException e) {
            log.warning(e.getMessage());
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(e.getMessage()).build();
        }

        final Gson gson = new Gson();
        return Response.status(Response.Status.OK).entity(gson.toJson(updatedUser)).build();
    }


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

        return Response.status(Response.Status.OK).entity(result).build();
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

        return Response.status(Response.Status.OK).entity(result).build();
    }

}
