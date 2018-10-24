package demo.domain;

import lombok.Data;

/**
 * The {@link User} class stores information about a particular user.
 *
 * @author Kenny Bastani
 * @author Josh Long
 * @author Davi Monteiro
 */
@Data
public class User {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;

}