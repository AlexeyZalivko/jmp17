package jmp.clients;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;
import jmp.constants.Constants;
import jmp.data.User;
import org.apache.log4j.Logger;

import javax.ws.rs.core.MediaType;
import java.io.File;


/**
 * Created by alex on 18.03.17.
 */
public class ImageClient {
    public static final String UNDERLINE_SEPARATOR = "_";
    private static Logger log = Logger.getLogger(ImageClient.class.getName());

    private static final String PNG_EXTENSION = ".png";

    private WebResource init() {
        final Client client = Client.create();

        return client.resource(Constants.IMAGE_URL);
    }

    public void uploadImage(final User user, final String imageName) {
        final WebResource resource = init();

        final File file = new File(getClass().getClassLoader().getResource(imageName + PNG_EXTENSION).getFile());
        final String userLogoName = user.getLogin() + UNDERLINE_SEPARATOR + imageName;

        final FormDataMultiPart form = new FormDataMultiPart();
        form.bodyPart(new FileDataBodyPart("file", file, MediaType.APPLICATION_OCTET_STREAM_TYPE));
        form.field("login", user.getLogin());

        final ClientResponse response = resource.type(MediaType.MULTIPART_FORM_DATA_TYPE)
                .post(ClientResponse.class, form);

        log.info("Response: " + response.getClientResponseStatus());

        response.close();
    }
}
