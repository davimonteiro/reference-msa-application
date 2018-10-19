package demo.api.v1;

import demo.domain.*;
import demo.repository.OrderEventRepository;
import demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class OrderServiceV1 {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderEventRepository orderEventRepository;

    @Autowired
    @LoadBalanced
    private OAuth2RestTemplate oAuth2RestTemplate;

    @Transactional
    public Order createOrder(List<LineItem> lineItems) {
        Account[] accounts = oAuth2RestTemplate.getForObject("http://account-service/v1/accounts", Account[].class);

        Account defaultAccount = Arrays.asList(accounts).stream()
                .filter(Account::getDefaultAccount)
                .findFirst().orElse(null);

        if (defaultAccount == null) {
            return null;
        }

        Address shippingAddress = defaultAccount.getAddresses().stream()
                .filter(address -> address.getAddressType() == Address.AddressType.SHIPPING)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Default account does not have a shipping address"));


        Order newOrder = new Order();
        newOrder.setLineItems(lineItems);
        newOrder.setAccountNumber(defaultAccount.getAccountNumber());
        shippingAddress.setId(null);
        newOrder.setShippingAddress(shippingAddress);

        newOrder =  orderRepository.save(newOrder);
        return newOrder;
    }

    @Transactional
    public OrderEvent addOrderEvent(OrderEvent orderEvent, Boolean validate) throws Exception {
        // Get the order for the event
        Order order = orderRepository.findById(orderEvent.getOrderId()).get();

        if (validate) {
            // Validate the account number of the event's order belongs to the user
            validateAccountNumber(order.getAccountNumber());
        }

        // Save the order event
        return orderEventRepository.save(orderEvent);
    }

    @Transactional
    public OrderEvent addOrderEvent(Order order, Boolean validate) throws Exception {
        // Get the order for the event

        if (validate) {
            // Validate the account number of the event's order belongs to the user
            validateAccountNumber(order.getAccountNumber());
        }

        // Save the order event
        OrderEvent orderEvent = new OrderEvent(order);
        return orderEventRepository.save(orderEvent);
    }

    public Order getOrder(Long orderId, Boolean validate) {
        // Get the order for the event
        Order order = orderRepository.findById(orderId).get();

        if (validate) {
            try {
                // Validate the account number of the event's order belongs to the user
                validateAccountNumber(order.getAccountNumber());
            } catch (Exception ex) {
                return null;
            }
        }

        Flux<OrderEvent> orderEvents =
                Flux.fromStream(orderEventRepository.findOrderEventsByOrderId(order.getId()));

        // Aggregate the state of order
        return orderEvents
                .takeWhile(orderEvent -> orderEvent.getType() != OrderEvent.OrderEventType.DELIVERED)
                .reduceWith(() -> order, Order::incorporate)
                .get();
    }

    @Transactional(readOnly = true)
    public List<Order> getOrdersForAccount(String accountNumber) throws Exception {
        List<Order> orders;
        validateAccountNumber(accountNumber);

        orders = orderRepository.findByAccountNumber(accountNumber);

        return orders.stream()
                .map(order -> getOrder(order.getId(), true))
                .filter(order -> order != null)
                .collect(Collectors.toList());
    }

    public boolean validateAccountNumber(String accountNumber) throws Exception {
        Account[] accounts = oAuth2RestTemplate.getForObject("http://account-service/v1/accounts", Account[].class);

        // Ensure account number is owned by the authenticated user
        if (accounts != null &&
                !Arrays.asList(accounts).stream().anyMatch(acct ->
                        Objects.equals(acct.getAccountNumber(), accountNumber))) {
            throw new Exception("Account number invalid");
        }

        return true;
    }
}
