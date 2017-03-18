package jmp.service;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import jmp.database.dao.UserBeanImpl;

import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.logging.Logger;

/**
 * Created by alex on 18.03.17.
 */
@Path("/images")
public class ImageService {

    private static Logger log = Logger.getLogger(UserBeanImpl.class.getName());

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
                                @FormDataParam("login") final String login) {
        try {
            saveImage(imageStream, form, login);
        } catch (IOException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        return Response.status(Response.Status.OK).build();
    }

    private void saveImage(final InputStream imageStream, final FormDataContentDisposition form, final String login) throws IOException {
        OutputStream ostream = null;
        final String resultPath = getBasePath(form, login);
        final File file = new File(resultPath);
        ostream = new FileOutputStream(file);
        byte[] b = new byte[1024];
        int length = 0;
        while ((length = imageStream.read(b)) != -1) {
            ostream.write(b, 0, length);
        }
    }

    private String getBasePath(final FormDataContentDisposition form, final String login) {
        final String name = USER_HOME + FS_SEPARATOR + login + UNDERLINE + form.getFileName();
        log.info("Image name: " + name);
        return name;
    }
}
