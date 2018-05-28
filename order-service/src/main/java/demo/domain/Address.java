package demo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter @ToString
public class Address implements Serializable {

    @Id @GeneratedValue
    private Long id;
    private String street1, street2, state, city, country;
    private Integer zipCode;
    private AddressType addressType;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "account")
    private Account account;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "invoice")
    private Invoice invoice;

    public Address() { }

    public Address(String street1, String street2, String state,
                   String city, String country, Integer zipCode) {
        this.street1 = street1;
        this.street2 = street2;
        this.state = state;
        this.city = city;
        this.country = country;
        this.zipCode = zipCode;
    }

    public enum AddressType {
        SHIPPING,
        BILLING
    }
}
