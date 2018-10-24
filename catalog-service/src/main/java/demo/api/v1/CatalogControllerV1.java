package demo.api.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static org.springframework.http.ResponseEntity.notFound;


/**
 * This REST controller provides APIs to retrieve catalogs and products.
 *
 * @author Kenny Bastani
 * @author Josh Long
 * @author Davi Monteiro
 */
@RestController
@RequestMapping("/v1")
public class CatalogControllerV1 {

    @Autowired
    private CatalogServiceV1 catalogService;

    @GetMapping(path = "/catalog", name = "getCatalog")
    public ResponseEntity getCatalog() {
        return Optional.ofNullable(catalogService.getCatalog())
                .map(ResponseEntity::ok)
                .orElse(notFound().build());
    }

    @GetMapping(path = "/products/{productId}", name = "getProduct")
    public ResponseEntity getProduct(@PathVariable("productId") String productId) {
        return Optional.ofNullable(catalogService.getProduct(productId))
                .map(ResponseEntity::ok)
                .orElse(notFound().build());
    }

}
