package demo.order;

import demo.domain.Order;
import demo.domain.OrderEvent;
import org.junit.Assert;
import org.junit.Test;

public class OrderTest {

    /**
     * Test aggregating an {@link Order} status
     */
    @Test
    public void aggregateOrderStatus() {
        Order order = new Order();

        // Test default order status
        Assert.assertEquals(order.getOrderStatus(), Order.OrderStatus.PURCHASED);

        // Test forward event conditions from PURCHASED-->DELIVERED

        // +PURCHASED---+PENDING------+CONFIRMED-----+SHIPPED--------+DELIVERED
        // |            |  |          |  |           |  |            |
        // +CREATED---->+  +ORDERED-->+  +SHIPPED--->+  +DELIVERED-->+

        order.incorporate(new OrderEvent(OrderEvent.OrderEventType.CREATED));
        Assert.assertEquals(order.getOrderStatus(), Order.OrderStatus.PENDING);

        order.incorporate(new OrderEvent(OrderEvent.OrderEventType.ORDERED));
        Assert.assertEquals(order.getOrderStatus(), Order.OrderStatus.CONFIRMED);

        order.incorporate(new OrderEvent(OrderEvent.OrderEventType.SHIPPED));
        Assert.assertEquals(order.getOrderStatus(), Order.OrderStatus.SHIPPED);

        order.incorporate(new OrderEvent(OrderEvent.OrderEventType.DELIVERED));
        Assert.assertEquals(order.getOrderStatus(), Order.OrderStatus.DELIVERED);

        // Test rollback event conditions from DELIVERED-->PURCHASED

        // +<--PURCHASED+  +<--CREATED+  +<---ORDERED+  +<----SHIPPED+
        // |            |  |          |  |           |  |            |
        // +PURCHASED---+PENDING------+CONFIRMED-----+SHIPPED--------+DELIVERED

        order.incorporate(new OrderEvent(OrderEvent.OrderEventType.SHIPPED));
        Assert.assertEquals(order.getOrderStatus(), Order.OrderStatus.SHIPPED);

        order.incorporate(new OrderEvent(OrderEvent.OrderEventType.ORDERED));
        Assert.assertEquals(order.getOrderStatus(), Order.OrderStatus.CONFIRMED);

        order.incorporate(new OrderEvent(OrderEvent.OrderEventType.CREATED));
        Assert.assertEquals(order.getOrderStatus(), Order.OrderStatus.PENDING);

        order.incorporate(new OrderEvent(OrderEvent.OrderEventType.PURCHASED));
        Assert.assertEquals(order.getOrderStatus(), Order.OrderStatus.PURCHASED);
    }
}