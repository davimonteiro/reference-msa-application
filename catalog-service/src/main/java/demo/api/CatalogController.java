package demo.api;

import demo.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ResponseEntity.notFound;


/**
 * This REST controller provides APIs to retrieve catalogs and products.
 *
 * @author Kenny Bastani
 * @author Josh Long
 * @author Davi Monteiro
 */
@RestController
    @RequestMapping("/api/catalogs")
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    @GetMapping(path = "/")
    public ResponseEntity getCatalog() {
        return ofNullable(catalogService.getCatalog())
                .map(ResponseEntity::ok)
                .orElse(notFound().build());
    }

    @GetMapping(path = "/{catalogNumber}")
    public ResponseEntity findCatalogByCatalogNumber(@PathVariable("catalogNumber") Long catalogNumber) {
        return ofNullable(catalogService.findCatalogByCatalogNumber(catalogNumber))
                .map(ResponseEntity::ok)
                .orElse(notFound().build());
    }

    @GetMapping(path = "/products/{productId}")
    public ResponseEntity getProduct(@PathVariable("productId") String productId) {
        return ofNullable(catalogService.getProduct(productId))
                .map(ResponseEntity::ok)
                .orElse(notFound().build());
    }

}
