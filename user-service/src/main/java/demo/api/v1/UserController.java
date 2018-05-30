package demo.api.v1;

import demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/user", method = GET)
    public Principal user(Principal user) {
        return user;
    }

    @RequestMapping(value = "api/users", method = GET)
    public ResponseEntity listUsers() {
        return ok(userRepository.findAll());
    }

}
