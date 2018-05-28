package demo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

/**
 * A simple domain class for the {@link Order} concept in the order context.
 *
 * @author Kenny Bastani
 */
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "order_table")
@Getter @Setter @ToString
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    private String accountNumber;

    @Transient
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "orderTable", cascade = ALL)
    private List<LineItem> lineItems = new ArrayList<>();

    @OneToOne(cascade = ALL)
    private Address shippingAddress;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "invoice")
    private Invoice invoice;

    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Date createdAt;

    @LastModifiedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Date lastModified;

    public Order() {
        this.orderStatus = OrderStatus.PURCHASED;
    }

    public Order(String accountNumber, Address shippingAddress) {
        this();
        this.accountNumber = accountNumber;
        this.shippingAddress = shippingAddress;
        if (shippingAddress.getAddressType() == null)
            this.shippingAddress.setAddressType(Address.AddressType.SHIPPING);
    }

    public void addLineItem(LineItem lineItem) {
        lineItems.add(lineItem);
    }

    /**
     * The incorporate method uses a simple state machine for an order's status to generate
     * the current state of an order using event sourcing and aggregation.
     * <p>
     * The event diagram below represents how events are incorporated into generating the
     * order status. The event log for the order status can be used to rollback the state
     * of the order in the case of a failed distributed transaction.
     * <p>
     * Events:   +<--PURCHASED+  +<--CREATED+  +<---ORDERED+  +<----SHIPPED+
     * *         |            |  |          |  |           |  |            |
     * Status    +PURCHASED---+PENDING------+CONFIRMED-----+SHIPPED--------+DELIVERED
     * *         |            |  |          |  |           |  |            |
     * Events:   +CREATED---->+  +ORDERED-->+  +SHIPPED--->+  +DELIVERED-->+
     *
     * @param orderEvent is the event to incorporate into the state machine
     * @return the aggregate {@link Order} with the aggregated order status
     */
    public Order incorporate(OrderEvent orderEvent) {

        if (orderStatus == null)
            orderStatus = OrderStatus.PURCHASED;

        switch (orderStatus) {
            case PURCHASED:
                if (orderEvent.getType() == OrderEvent.OrderEventType.CREATED)
                    orderStatus = OrderStatus.PENDING;
                break;
            case PENDING:
                if (orderEvent.getType() == OrderEvent.OrderEventType.ORDERED) {
                    orderStatus = OrderStatus.CONFIRMED;
                } else if (orderEvent.getType() == OrderEvent.OrderEventType.PURCHASED) {
                    orderStatus = OrderStatus.PURCHASED;
                }
                break;
            case CONFIRMED:
                if (orderEvent.getType() == OrderEvent.OrderEventType.SHIPPED) {
                    orderStatus = OrderStatus.SHIPPED;
                } else if (orderEvent.getType() == OrderEvent.OrderEventType.CREATED) {
                    orderStatus = OrderStatus.PENDING;
                }
                break;
            case SHIPPED:
                if (orderEvent.getType() == OrderEvent.OrderEventType.DELIVERED) {
                    orderStatus = OrderStatus.DELIVERED;
                } else if (orderEvent.getType() == OrderEvent.OrderEventType.ORDERED) {
                    orderStatus = OrderStatus.CONFIRMED;
                }
                break;
            case DELIVERED:
                if (orderEvent.getType() == OrderEvent.OrderEventType.SHIPPED)
                    orderStatus = OrderStatus.SHIPPED;
                break;
            default:
                // Invalid event type with regards to the order status
                break;
        }

        return this;
    }

    /**
     * Describes the state of an {@link Order}.
     *
     * @author Kenny Bastani
     */
    public enum OrderStatus {
        PURCHASED,
        PENDING,
        CONFIRMED,
        SHIPPED,
        DELIVERED
    }
}
