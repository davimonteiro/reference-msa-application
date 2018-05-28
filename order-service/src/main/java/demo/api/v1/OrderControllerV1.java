package demo.api.v1;

import demo.domain.LineItem;
import demo.domain.OrderEvent;
import demo.domain.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(path = "/v1")
public class OrderControllerV1 {

    @Autowired
    private OrderServiceV1 orderService;

    @RequestMapping(path = "/accounts/{accountNumber}/orders")
    public ResponseEntity getOrders(@PathVariable("accountNumber") String accountNumber) throws Exception {
        return Optional.ofNullable(orderService.getOrdersForAccount(accountNumber))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new Exception("Accounts for user do not exist"));
    }

    @RequestMapping(path = "/orders/{orderId}/events", method = RequestMethod.POST)
    public ResponseEntity addOrderEvent(@RequestBody OrderEvent orderEvent,
                                        @PathVariable("orderId") String orderId) throws Exception {
        assert orderEvent != null;
        assert orderId != null;
        assert !Objects.equals(orderId, orderEvent.getOrderId());
        return Optional.ofNullable(orderService.addOrderEvent(orderEvent, true))
                .map(a -> new ResponseEntity<>(HttpStatus.NO_CONTENT))
                .orElseThrow(() -> new Exception("Order event could not be applied to order"));
    }

    @RequestMapping(path = "/orders/{orderId}")
    public ResponseEntity getOrder(@PathVariable("orderId") Long orderId) throws Exception {
        assert orderId != null;
        return Optional.ofNullable(orderService.getOrder(orderId, true))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new Exception("Could not retrieve order"));
    }

    @RequestMapping(path = "/orders", method = RequestMethod.POST)
    public ResponseEntity createOrder(@RequestBody List<LineItem> lineItems) throws Exception {
        assert lineItems != null;
        assert lineItems.size() > 0;
        return Optional.ofNullable(orderService.createOrder(lineItems))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new Exception("Could not create the order"));
    }


    @RequestMapping(path = "/orders/orchestrated", method = RequestMethod.POST)
    public ResponseEntity createOrderOrchestrated(@RequestBody ShoppingCart shoppingCart) throws Exception {
        assert shoppingCart.getLineItems() != null;
        assert shoppingCart.getLineItems().size() > 0;
        return Optional.ofNullable(orderService.createOrder(shoppingCart.getLineItems()))
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new Exception("Could not create the order"));
    }


}
