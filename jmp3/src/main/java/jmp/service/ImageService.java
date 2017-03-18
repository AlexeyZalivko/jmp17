package jmp.service;

import com.google.gson.Gson;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import jmp.database.dao.UserBean;
import jmp.database.dao.UserBeanImpl;
import jmp.database.data.User;

import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Created by alex on 18.03.17.
 */
@Path("/images")
public class ImageService {

    private static Logger log = Logger.getLogger(UserBeanImpl.class.getName());

    private UserBean userBean = new UserBeanImpl();

    private Gson gson = new Gson();

    public static final String USER_HOME = "/home/alex";
    public static final String PNG_EXTENSION = ".png";
    public static final String FS_SEPARATOR = "/";
    public static final String UNDERLINE = "_";

    @POST
    @Path("/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces("image/png")
    public Response uploadImage(@FormDataParam("file") final InputStream imageStream,
                                @FormDataParam("file") final FormDataContentDisposition form,
                                @FormDataParam("id") final String id) {
        User user = null;
        try {
            saveImage(imageStream, form, id);
            user = userBean.addUserLogo(id, form.getFileName());
        } catch (IOException e) {
            log.warning(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (SQLException e) {
            log.warning(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.status(Response.Status.OK).entity(gson.toJson(user)).build();
    }

    private void saveImage(final InputStream imageStream, final FormDataContentDisposition form, final String id) throws IOException {
        OutputStream ostream = null;
        final String resultPath = getBasePath(form, id);
        final File file = new File(resultPath);
        ostream = new FileOutputStream(file);
        byte[] b = new byte[1024];
        int length = 0;
        while ((length = imageStream.read(b)) != -1) {
            ostream.write(b, 0, length);
        }
    }

    private String getBasePath(final FormDataContentDisposition form, final String id) {
        final String name = USER_HOME + FS_SEPARATOR + id + UNDERLINE + form.getFileName();
        log.info("Image name: " + name);
        return name;
    }
}
