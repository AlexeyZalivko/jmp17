package jmp.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.logging.Logger;

/**
 * Created by alex on 15.03.17.
 */
@Path("/user")
public class UserService {

    private static Logger log = Logger.getLogger(UserService.class.getName());

    @GET
    @Path("/{id}")
    public void getUser(@PathParam("id") Long id) {
    }
}
