package demo.config;

import demo.domain.Address;
import demo.domain.Invoice;
import demo.domain.LineItem;
import demo.domain.Order;
import demo.repository.InvoiceRepository;
import demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Initialize and populate the local database.
 *
 * @author Davi Monteiro
 */
@Service
public class DatabaseInitializer {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Transactional
    public void populate() {
        // Clear existing data
        orderRepository.deleteAll();
        invoiceRepository.deleteAll();

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

        // Create a new billing address
        Address billingAddress = new Address("875 Howard St", null,
                "CA", "San Francisco", "United States", 94103);

        // Create a new invoice
        Invoice invoice = new Invoice("918273465", billingAddress);

        // Add the order to the invoice
        invoice.addOrder(order);

        // Save the invoice
        invoiceRepository.save(invoice);
    }

}
