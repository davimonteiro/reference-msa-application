package demo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter @ToString
public class CreditCard implements Serializable {

    @Id @GeneratedValue
    private Long id;
    private String number;
    @Enumerated(EnumType.ORDINAL)
    private CreditCardType type;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "account")
    private Account account;

    public enum CreditCardType {
        VISA,
        MASTERCARD,
        AMERICAN_EXPRESS
    }
}
