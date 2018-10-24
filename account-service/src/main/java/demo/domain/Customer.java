package demo.domain;

import lombok.Data;

import javax.persistence.*;

import static javax.persistence.CascadeType.*;

/**
 * The {@link Customer} entity is a root object in the customer bounded context.
 *
 * @author Kenny Bastani
 * @author Josh Long
 * @author Davi Monteiro
 */
@Data
@Entity
public class Customer {

    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    @OneToOne(cascade = ALL)
    private Account account;

}
