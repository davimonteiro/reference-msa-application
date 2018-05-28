package demo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

@Entity
@Getter @Setter @ToString
public class Account {

    @Id @GeneratedValue
    private Long id;
    private String userId;
    private String accountNumber;
    private Boolean defaultAccount;
    @OneToMany(mappedBy = "account", cascade = ALL)
    private Set<CreditCard> creditCards;
    @OneToMany(mappedBy = "account", cascade = ALL)
    private Set<Address> addresses;

}
