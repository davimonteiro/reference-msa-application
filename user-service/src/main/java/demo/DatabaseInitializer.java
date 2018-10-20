package demo;

import demo.domain.User;
import demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class DatabaseInitializer {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void populate() {
        for (long i = 0; i < 1000; i++) {
            User user = new User();
            user.setFirstName("John" + i);
            user.setLastName("Doe" + i);
            user.setUsername("user" + i);
            user.setPassword("password");
            user.setEmail("john.doe" + i + "@example.com");
            userRepository.save(user);
        }

        userRepository.findAll().forEach(user -> System.err.println(user.toString()));
    }

}