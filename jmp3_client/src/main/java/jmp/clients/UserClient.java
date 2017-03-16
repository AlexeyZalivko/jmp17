package jmp.clients;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import jmp.constants.Constants;
import jmp.data.User;
import org.apache.log4j.Logger;

import javax.ws.rs.core.MediaType;

/**
 * Created by alex on 16.03.17.
 */
public class UserClient {
    private static Logger log = Logger.getLogger(UserClient.class.getName());

    private WebResource init(final String url) {
        final Client client = Client.create();

        return client.resource(url);
    }

    public User addUser(final User user) throws Exception {
        final WebResource resource = init(Constants.USER_URL);

        final ClientResponse response = resource.accept(MediaType.APPLICATION_XML)
                .post(ClientResponse.class, user);

        return processResponse(response);
    }

    public User updateUser(final User user) throws Exception {
        final WebResource resource = init(Constants.USER_URL);

        final Gson gson = new Gson();

        final String userJSON = gson.toJson(user);
        final ClientResponse response = resource.accept(MediaType.APPLICATION_JSON)
                .put(ClientResponse.class, user);

        return processResponse(response);
    }

    private User processResponse(final ClientResponse response) throws Exception {

        final int status = response.getStatus();
        log.info("Response status: " + status);

        if (status != 200) {
            throw new Exception("Service is not working correctly. Response code " + status);
        }

        return response.getEntity(User.class);
    }

}
