package demo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter @Setter @ToString
public class OrderEvent implements Serializable {

    @Id @GeneratedValue
    private Long id;
    @Enumerated(EnumType.ORDINAL)
    private OrderEventType type;
    private Long orderId;

    public OrderEvent() { }

    public OrderEvent(Order order) {
        this.orderId = order.getId();
        this.type = OrderEventType.PURCHASED;
    }

    public OrderEvent(OrderEventType type) {
        this.type = type;
    }

    public enum OrderEventType {
        PURCHASED,
        CREATED,
        ORDERED,
        SHIPPED,
        DELIVERED
    }

}
