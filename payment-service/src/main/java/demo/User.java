package demo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.UUID;

/**
 * This model represents a user.
 *
 * @author Kenny Bastani
 * @author Josh Long
 */
@Data
@Entity
public class User implements Serializable {

    @Id
    private String id;
    private String firstName;
    private String lastName;

    public User() {
        id = UUID.randomUUID().toString();
    }

    public User(String firstName, String lastName) {
        id = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
