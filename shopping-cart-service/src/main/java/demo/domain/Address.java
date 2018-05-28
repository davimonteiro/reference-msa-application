package demo.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Address implements Serializable {

    private String street1, street2, state, city, country;
    private Integer zipCode;
    private AddressType addressType;

    public Address() {
    }

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
