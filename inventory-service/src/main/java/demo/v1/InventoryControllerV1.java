package demo.v1;

import demo.domain.Catalog;
import demo.domain.LineItem;
import demo.domain.Product;
import demo.domain.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/v1")
public class InventoryControllerV1 {

    @Autowired
    private InventoryServiceV1 inventoryService;

    @RequestMapping(path = "/catalogs/{catalogNumber}", name = "findCatalogByCatalogNumber", method = GET)
    public ResponseEntity<Catalog> findCatalogByCatalogNumber(@PathVariable("catalogNumber") Long catalogNumber) {
        return ofNullable(inventoryService.findCatalogByCatalogNumber(catalogNumber))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @RequestMapping(path = "/products/{productId}", name = "getProduct", method = GET)
    public ResponseEntity<Product> getProduct(@PathVariable("productId") String productId) {
        return ofNullable(inventoryService.getProduct(productId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(path = "/inventory", name = "getAvailableInventoryForProductIds", method = GET)
    public ResponseEntity getAvailableInventoryForProductIds(@RequestParam("productIds") String productIds) {
        return ofNullable(inventoryService.getAvailableInventoryForProductIds(productIds))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(path = "/inventory/orchestrated", method = GET)
    public ResponseEntity getAvailableInventoryForProductIdsOrchestrated(@RequestParam("shoppingCart") ShoppingCart shoppingCart) {
        return ofNullable(inventoryService.getAvailableInventoryForProductIds(shoppingCart))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(path = "/inventory/checkavailable/orchestrated", method = POST)
    public ResponseEntity checkAvailableInventory(@RequestBody ShoppingCart shoppingCart) throws Exception {
        return ofNullable(inventoryService.checkAvailableInventory(shoppingCart))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity(HttpStatus.NOT_FOUND));
    }

}
