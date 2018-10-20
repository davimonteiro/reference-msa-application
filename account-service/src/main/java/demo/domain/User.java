package demo.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * The {@link User} class is a POJO class representing a particular user.
 *
 * @author Kenny Bastani
 * @author Josh Long
 * @author Davi Monteiro
 */
@Data
public class User implements Serializable {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;

}