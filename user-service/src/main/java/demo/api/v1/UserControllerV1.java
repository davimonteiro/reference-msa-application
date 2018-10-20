package demo.api.v1;

import demo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

import static java.util.Optional.*;

@RestController
@RequestMapping(path = "/v1")
public class UserControllerV1 {

    @Autowired
    private UserServiceV1 userService;

    @RequestMapping(path = "/me")
    public ResponseEntity me(Principal principal) {
        User user = null;
        if (principal != null) {
            user = userService.getUserByUsername(principal.getName());
        }
        return ofNullable(user)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found."));
    }

}
