package demo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

/**
 * A simple domain class for the {@link Invoice} concept of the order context.
 *
 * @author Kenny Bastani
 * @author Josh Long
 */
@Entity
@Getter @Setter @ToString
public class Invoice {

    @Id @GeneratedValue
    private Long id;
    private String invoiceId;
    private String customerId;

    @OneToMany//(cascade = ALL)
    private List<Order> orders = new ArrayList<>();

    @OneToOne(cascade = ALL)
    private Address billingAddress;

    @Enumerated(EnumType.ORDINAL)
    private InvoiceStatus invoiceStatus;

    public Invoice(String customerId, Address billingAddress) {
        this.customerId = customerId;
        this.billingAddress = billingAddress;
        this.billingAddress.setAddressType(Address.AddressType.BILLING);
        this.invoiceStatus = InvoiceStatus.CREATED;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    /**
     * Describes the state of an {@link Invoice}.
     *
     * @author Kenny Bastani
     * @author Josh Long
     */
    public enum InvoiceStatus {
        CREATED,
        SENT,
        PAID
    }
}
