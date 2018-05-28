package demo.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;

    public User() { }

    public User(Long id) {
        this.id = id;
    }

}
