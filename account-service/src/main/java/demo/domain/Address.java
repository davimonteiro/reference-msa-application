package demo.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * A simple {@link Address} entity for an account.
 *
 * @author Kenny Bastani
 * @author Josh Long
 */
@Data
@Entity
public class Address {

    @Id
    @GeneratedValue
    private Long id;
    private String street1;
    private String street2;
    private String state;
    private String city;
    private String country;
    private Integer zipCode;
    @Enumerated(EnumType.STRING)
    private AddressType addressType;

    public enum AddressType {
        SHIPPING,
        BILLING
    }

}
