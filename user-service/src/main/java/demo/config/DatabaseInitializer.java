package demo.config;

import demo.domain.User;
import demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Profile({"development"})
public class DatabaseInitializer {

    @Autowired
    private UserRepository userRepository;

    public void populate() throws Exception {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("user");
        user.setEmail("john.doe@example.com");

        userRepository.save(user);
    }

}
