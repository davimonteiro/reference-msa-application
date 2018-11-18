package demo;

import demo.domain.Authority;
import demo.domain.User;
import demo.repository.AuthorityRepository;
import demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Initialize and populate the local database.
 *
 * @author Davi Monteiro
 */
@Service
public class DatabaseInitializer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void populate() {
        if (authorityRepository.count() == 0) populateAuthorities();
        if (userRepository.count() == 0) populateUsers();
    }

    @Transactional
    public void populateUsers() {
        String password = passwordEncoder.encode("password");
        Authority roleUser = authorityRepository.findById("ROLE_USER").get();
        for (long i = 0; i < 1000; i++) {
            User user = new User();
            user.setFirstName("John" + i);
            user.setLastName("Doe" + i);
            user.setUsername("user" + i);
            user.setPassword(password);
            user.setEmail("john.doe" + i + "@example.com");
            user.getAuthorities().add(roleUser);
            userRepository.save(user);
        }
    }

    @Transactional
    public void populateAuthorities() {
        Authority userAuthority = new Authority();
        userAuthority.setAuthority("ROLE_USER");

        Authority adminAuthority = new Authority();
        adminAuthority.setAuthority("ROLE_ADMIN");

        authorityRepository.save(userAuthority);
        authorityRepository.save(adminAuthority);
    }

}