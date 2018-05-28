package demo.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

/**
 * This entity stores information about a {@link Customer}'s {@link Account}.
 *
 * @author Kenny Bastani
 * @author Josh Long
 */
@Data
@Entity
public class Account {

    @Id
    @GeneratedValue
    private Long id;
    private Long userId;
    private String accountNumber;
    private Boolean defaultAccount;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<CreditCard> creditCards;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Address> addresses;

}
