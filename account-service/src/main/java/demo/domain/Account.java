package demo.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

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
    @OneToMany(cascade = ALL, fetch = EAGER)
    private Set<CreditCard> creditCards;
    @OneToMany(cascade = ALL, fetch = EAGER)
    private Set<Address> addresses;

}
