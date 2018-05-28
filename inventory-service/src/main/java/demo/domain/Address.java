package demo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * A simple {@link Address} entity for an order or invoice.
 *
 * @author Kenny Bastani
 * @author Josh Long
 */
@Entity
@Getter @Setter @ToString
public class Address {

    @Id @GeneratedValue
    private Long id;
    private String street1, street2, state, city, country;
    private Integer zipCode;

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

}
