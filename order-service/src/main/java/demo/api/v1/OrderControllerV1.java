package demo.api.v1;

import demo.domain.LineItem;
import demo.domain.Order;
import demo.domain.OrderEvent;
import demo.domain.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static java.util.Optional.ofNullable;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/v1")
public class OrderControllerV1 {

    @Autowired
    private OrderServiceV1 orderService;

    @GetMapping(path = "/accounts/{accountNumber}/orders")
    public ResponseEntity getOrders(@PathVariable("accountNumber") String accountNumber) throws Exception {
        return ofNullable(orderService.getOrdersForAccount(accountNumber))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new Exception("Accounts for user do not exist"));
    }

    @PostMapping(path = "/orders/{orderId}/events")
    public ResponseEntity addOrderEvent(@RequestBody OrderEvent orderEvent,
                                        @PathVariable("orderId") String orderId) throws Exception {
        assert orderEvent != null;
        assert orderId != null;
        assert !Objects.equals(orderId, orderEvent.getOrderId());
        return ofNullable(orderService.addOrderEvent(orderEvent, true))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new Exception("Order event could not be applied to order"));
    }

    @GetMapping(path = "/orders/{orderId}")
    public ResponseEntity getOrder(@PathVariable("orderId") Long orderId) throws Exception {
        assert orderId != null;
        return ofNullable(orderService.getOrder(orderId, true))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new Exception("Could not retrieve order"));
    }

    @PostMapping(path = "/orders")
    public ResponseEntity createOrder(@RequestBody List<LineItem> lineItems) throws Exception {
        assert lineItems != null;
        assert lineItems.size() > 0;
        return ofNullable(orderService.createOrder(lineItems))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new Exception("Could not create the order"));
    }


    @PostMapping(path = "/orders/orchestrated", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity createOrderOrchestrated(@RequestBody ShoppingCart shoppingCart) throws Exception {
        assert shoppingCart.getLineItems() != null;
        assert shoppingCart.getLineItems().size() > 0;
        return ofNullable(orderService.createOrder(shoppingCart.getLineItems()))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new Exception("Could not create the order"));
    }

    @PostMapping(path = "/orders/events/orchestrated", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity addOrderEventOrchestrated(@RequestBody Order order) throws Exception {
        return ofNullable(orderService.addOrderEvent(order, true))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new Exception("Order event could not be applied to order"));
    }

}
