package jmp.resources;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import jmp.database.dao.UserDAO;
import jmp.database.dao.UserDAOImpl;
import jmp.database.data.User;
import jmp.services.FileSystemService;
import jmp.services.FileSystemServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Created by alex on 18.03.17.
 */
@Path("/images")
public class ImageResource {

    public static final String ERROR_USER_HASN_T_LOGO = "That user hasn't logo";
    private static Logger log = Logger.getLogger(UserDAOImpl.class.getName());

    private UserDAO userResource = new UserDAOImpl();
    private FileSystemService fsServeice = new FileSystemServiceImpl();

    public static final String USER_HOME = "/home/alex";
    public static final String FS_SEPARATOR = "/";
    public static final String UNDERLINE = "_";

    @GET
    @Path("/{id}")
    public Response getImageByUserId(@PathParam("id") final Long id) {
        File file = null;
        String fileName = null;
        try {
            final User user = userResource.getUserById(id);
            file = fsServeice.getLogoByUser(user);

            fileName = user.getLogo();
        } catch (SQLException e) {
            log.warning(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            log.warning(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        final Response.ResponseBuilder responseBuilder = Response.ok((Object) file);
        responseBuilder.header("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        return responseBuilder.build();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadImage(@FormDataParam("file") final InputStream imageStream,
                                @FormDataParam("file") final FormDataContentDisposition form,
                                @FormDataParam("id") final String id) {
        User user = null;
        try {
            fsServeice.saveImage(imageStream, form, id);
            user = userResource.addUserLogo(id, form.getFileName());
        } catch (IOException e) {
            log.warning(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (SQLException e) {
            log.warning(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.status(Response.Status.OK).entity(user).build();
    }

    private String getBasePath(final String fileName, final String id) {
        final String name = USER_HOME + FS_SEPARATOR + id + UNDERLINE + fileName;
        log.info("Image name: " + name);
        return name;
    }
}
