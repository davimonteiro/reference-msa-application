package demo.api;

import demo.domain.ShoppingCart;
import demo.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static java.util.Optional.ofNullable;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.notFound;


/**
 * This REST controller provides APIs to retrieve products and verify available inventory.
 *
 * @author Kenny Bastani
 * @author Josh Long
 * @author Davi Monteiro
 */
@RestController
@RequestMapping("/inventories")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping(path = "/")
    public ResponseEntity getInventoryByProductIds(@RequestParam("productIds") String productIds) {
        return ofNullable(inventoryService.getInventoryByShoppingCart(productIds))
                .map(ResponseEntity::ok)
                .orElse(notFound().build());
    }

    @GetMapping(path = "/orchestrated")
    public ResponseEntity getInventoryByShoppingCart(@RequestParam("shoppingCart") ShoppingCart shoppingCart) {
        return ofNullable(inventoryService.getInventoryByShoppingCart(shoppingCart))
                .map(ResponseEntity::ok)
                .orElse(notFound().build());
    }

    @PostMapping(path = "/availability", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity checkAvailability(@RequestBody ShoppingCart shoppingCart) throws Exception {
        return ofNullable(inventoryService.checkAvailability(shoppingCart))
                .map(ResponseEntity::ok)
                .orElse(notFound().build());
    }

}
