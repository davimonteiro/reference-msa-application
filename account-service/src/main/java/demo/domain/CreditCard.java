package demo.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * A {@link CreditCard} entity is used for processing payments and belongs
 * to an account.
 *
 * @author Kenny Bastani
 * @author Josh Long
 */
@Data
@Entity
public class CreditCard {

    @Id
    @GeneratedValue
    private Long id;
    private String number;
    @Enumerated(EnumType.STRING)
    private CreditCardType type;

    public enum CreditCardType {
        VISA,
        MASTERCARD,
        AMERICAN_EXPRESS
    }

}
