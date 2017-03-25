package jmp.resources;

import jmp.exceptions.ServiceException;
import jmp.services.UserService;
import jmp.services.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by alex on 25.03.17.
 */
@RestController
@RequestMapping("/api/user")
public class UserResourceImpl implements UserResource {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    @Override
    public
    @ResponseBody
    User createUser(@RequestBody final User user) throws ServiceException {
        return userService.addUser(user);
    }

    @RequestMapping(value = "/byId", method = RequestMethod.GET)
    @Override
    public
    @ResponseBody
    User getUserById(@RequestParam("id") final Long id) throws ServiceException {
        return userService.getUserById(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    @Override
    public
    @ResponseBody
    List<User> getAllUsers() throws ServiceException {
        return userService.getAllUsers();
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @Override
    public void deleteUser(@RequestParam("id") final Long id) throws ServiceException {
        userService.remove(id);
    }
}
