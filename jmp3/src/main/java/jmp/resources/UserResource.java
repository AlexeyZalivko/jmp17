package jmp.resources;

import jmp.database.dao.UserDAO;
import jmp.database.dao.UserDAOImpl;
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
public class UserResource {

    private static Logger log = Logger.getLogger(UserResource.class.getName());

    private UserDAO userBean = new UserDAOImpl();

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
    @Produces(MediaType.APPLICATION_JSON)
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

        return Response.status(Response.Status.OK).entity(updatedUser).build();
    }


    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") Long id) {

        User user = null;
        try {
            user = userBean.getUserById(id);
        } catch (SQLException e) {
            log.warning(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }

        return Response.status(Response.Status.OK).entity(user).build();
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {

        List<User> users = null;
        try {
            users = userBean.getAllUsers();
        } catch (SQLException e) {
            log.warning(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }

        return Response.status(Response.Status.OK).entity(users).build();
    }

}
