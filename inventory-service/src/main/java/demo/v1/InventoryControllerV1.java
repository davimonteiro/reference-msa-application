package demo.v1;

import demo.domain.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


/**
 * This REST controller provides APIs to retrieve products and verify available inventory.
 *
 * @author Kenny Bastani
 * @author Josh Long
 * @author Davi Monteiro
 */
@RestController
@RequestMapping("/v1")
public class InventoryControllerV1 {

    @Autowired
    private InventoryServiceV1 inventoryService;

    @GetMapping(path = "/catalogs/{catalogNumber}", name = "findCatalogByCatalogNumber")
    public ResponseEntity findCatalogByCatalogNumber(@PathVariable("catalogNumber") Long catalogNumber) {
        return ofNullable(inventoryService.findCatalogByCatalogNumber(catalogNumber))
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity(NOT_FOUND));

    }

    @GetMapping(path = "/products/{productId}", name = "getProduct")
    public ResponseEntity getProduct(@PathVariable("productId") String productId) {
        return ofNullable(inventoryService.getProduct(productId))
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity(NOT_FOUND));
    }

    @GetMapping(path = "/inventory", name = "getAvailableInventoryForProductIds")
    public ResponseEntity getAvailableInventoryForProductIds(@RequestParam("productIds") String productIds) {
        return ofNullable(inventoryService.getAvailableInventoryForProductIds(productIds))
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity(NOT_FOUND));
    }

    @GetMapping(path = "/inventory/orchestrated")
    public ResponseEntity getAvailableInventoryForProductIdsOrchestrated(@RequestParam("shoppingCart") ShoppingCart shoppingCart) {
        return ofNullable(inventoryService.getAvailableInventoryForProductIds(shoppingCart))
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity(NOT_FOUND));
    }

    @PostMapping(path = "/inventory/checkavailable/orchestrated", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity checkAvailableInventory(@RequestBody ShoppingCart shoppingCart) throws Exception {
        return ofNullable(inventoryService.checkAvailableInventory(shoppingCart))
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity(NOT_FOUND));
    }

}
