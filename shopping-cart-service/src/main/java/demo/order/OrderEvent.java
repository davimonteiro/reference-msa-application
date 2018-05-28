package demo.order;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter @Setter @ToString
public class OrderEvent implements Serializable {

    private String id;
    private OrderEventType type;
    private Long orderId;

    public OrderEvent() { }

    public OrderEvent(OrderEventType type, Long orderId) {
        this.type = type;
        this.orderId = orderId;
    }

    public enum OrderEventType {
        CREATED,
        ORDERED,
        RESERVED,
        SHIPPED,
        DELIVERED
    }
}
