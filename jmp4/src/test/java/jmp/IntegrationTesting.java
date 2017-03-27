package jmp;

import jmp.exceptions.ServiceException;
import jmp.resources.UserResource;
import jmp.services.data.User;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by alex on 26.03.17.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest
public class IntegrationTesting {

    @Autowired
    private UserResource service;

    public static final String LINUX_PNG = "linux.png";
    public static final String IMAGE_PNG = "image/png";

    private int userCount = 0;
    private static User user = new User();

    @BeforeClass
    public static void initStatic() throws URISyntaxException, IOException {
        user.setFirstName("First");
        user.setLastName("Last");
        user.setLogin("loginTest");
        user.setEmail("mail@mail.com");
    }

    @Before
    public void init() throws ServiceException {
        final List<User> users = service.getAllUsers();
        if (!CollectionUtils.isEmpty(users)) {
            userCount = users.size();
        }
    }

    @Test
    public void getUserById() throws ServiceException {
        final User user = service.getUserById(1L);
        assertThat(user).isNotNull();
    }

    @Test
    public void geAllUsers() throws ServiceException {
        final List<User> users = service.getAllUsers();
        assertThat(users).isNotEmpty().hasSize(userCount);
    }

    @Test
    public void addNewUser() throws ServiceException {
        user.setLogin("createUser");
        final User newUser = service.createUser(user);
        assertThat(newUser).isNotNull();
        assertThat(newUser.getId()).isNotNull();

        user.setId(newUser.getId());
    }

    @Test
    public void addLogo() throws ServiceException, URISyntaxException, IOException {
        User testUser = null;
        if(user.getId() != null){
            testUser = user;
        }else{
            user.setLogin("logoUser");
            testUser = service.createUser(user);
        }

        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final byte[] logo = IOUtils.toByteArray(
                classLoader.getResourceAsStream(LINUX_PNG)
        );

        final MultipartFile image = new MockMultipartFile(LINUX_PNG, LINUX_PNG, IMAGE_PNG, logo);

        final User userWithLogo = service.uploadUserLogo(testUser.getId(), image);
        assertThat(userWithLogo).isNotNull();
        assertThat(userWithLogo.getLogo()).isNotEmpty().isEqualTo(logo);
    }
}
