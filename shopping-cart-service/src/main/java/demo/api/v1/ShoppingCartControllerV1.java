package demo.api.v1;

import demo.cart.CartEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Optional.ofNullable;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(path = "/v1")
public class ShoppingCartControllerV1 {

    @Autowired
    private ShoppingCartServiceV1 shoppingCartService;

    @RequestMapping(path = "/events", method = POST)
    public ResponseEntity addCartEvent(@RequestBody CartEvent cartEvent) throws Exception {
        return ofNullable(shoppingCartService.addCartEvent(cartEvent))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new Exception("Could not find shopping cart"));
    }

    @RequestMapping(path = "/checkout", method = POST)
    public ResponseEntity checkoutCart() throws Exception {
        return ofNullable(shoppingCartService.checkout())
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new Exception("Could not checkout"));
    }

    @RequestMapping(path = "/checkout/orchestrated", method = POST)
    public ResponseEntity checkoutCartOrchestrated() throws Exception {
        return ofNullable(shoppingCartService.checkoutOrchestrated())
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new Exception("Could not checkout"));
    }

    @RequestMapping(path = "/cart", method = GET)
    public ResponseEntity getCart() throws Exception {
        return ofNullable(shoppingCartService.getShoppingCart())
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new Exception("Could not find shopping cart"));
    }

    @RequestMapping(path = "/cart/orchestrated", method = GET)
    public ResponseEntity getCartOrchestrated() throws Exception {
        return ofNullable(shoppingCartService.getShoppingCartOrchestrated())
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new Exception("Could not find shopping cart"));
    }

    @RequestMapping(path = "/cart/clear/orchestrated", method = POST)
    public ResponseEntity clearShoppingCart() throws Exception {
        return ofNullable(shoppingCartService.clearShoppingCart())
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new Exception("Could not clear shopping cart"));
    }

}
