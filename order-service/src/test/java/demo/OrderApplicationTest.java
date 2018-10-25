package demo;

import demo.api.v1.OrderServiceV1;
import demo.domain.*;
import demo.repository.InvoiceRepository;
import demo.repository.OrderRepository;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = OrderApplication.class)
@ActiveProfiles(profiles = "test")
public class OrderApplicationTest extends TestCase {

    private Logger log = LoggerFactory.getLogger(OrderApplicationTest.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private OrderServiceV1 orderServiceV1;

    @Test
    @Transactional
    public void orderTest() {

        // Create a new shipping address for the customer
        Address shippingAddress = new Address("1600 Pennsylvania Ave NW", null,
                "DC", "Washington", "United States", 20500);

        // Create a new order
        Order order = new Order("12345", shippingAddress);

        // Add line items
        order.addLineItem(new LineItem("Best. Cloud. Ever. (T-Shirt, Men's Large)",
                "SKU-24642", 1, 21.99, .06));

        order.addLineItem(new LineItem("Like a BOSH (T-Shirt, Women's Medium)",
                "SKU-34563", 3, 14.99, .06));

        order.addLineItem(new LineItem("We're gonna need a bigger VM (T-Shirt, Women's Small)",
                "SKU-12464", 4, 13.99, .06));

        order.addLineItem(new LineItem("cf push awesome (Hoodie, Men's Medium)",
                "SKU-64233", 2, 21.99, .06));

        // Save the order
        order = orderRepository.save(order);

        // Log the result
        log.info(order.toString());

        // The lastModified and createdAt timestamps should now be different
        log.info(orderRepository.save(order).toString());

        // Create a new billing address
        Address billingAddress = new Address("875 Howard St", null,
                "CA", "San Francisco", "United States", 94103);

        // Create a new invoice
        Invoice invoice = new Invoice("918273465", billingAddress);

        // Add the order to the invoice
        invoice.addOrder(order);

        // Save the invoice
        invoiceRepository.save(invoice);

        // Lookup orders by account number
        List<Order> orders = orderRepository.findByAccountNumber("918273465");

        log.info(orders.toString());

        try {
            OrderEvent orderEvent = new OrderEvent();
            orderEvent.setType(OrderEvent.OrderEventType.CREATED);
            orderEvent.setOrderId(order.getId());
            orderServiceV1.addOrderEvent(orderEvent, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Order orderPending = orderServiceV1.getOrder(order.getId(), false);

        Assert.assertNotNull(orderPending);
        Assert.assertEquals(orderPending.getOrderStatus(), Order.OrderStatus.PENDING);
    }

}
