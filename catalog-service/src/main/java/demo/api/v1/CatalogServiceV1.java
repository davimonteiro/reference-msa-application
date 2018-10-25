package demo.api.v1;


import demo.domain.Catalog;
import demo.domain.CatalogInfo;
import demo.domain.Product;
import demo.repository.CatalogInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


/**
 * The {@link CatalogServiceV1} class provides a service to retrieve catalogs and products.
 *
 * @author Kenny Bastani
 * @author Josh Long
 * @author Davi Monteiro
 */
@Service
public class CatalogServiceV1 {

    @Autowired
    private CatalogInfoRepository catalogInfoRepository;

    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;

    public Catalog getCatalog() {
        CatalogInfo activeCatalog = catalogInfoRepository.findCatalogByActive(true);

        Catalog catalog = restTemplate.getForObject(
                String.format("http://inventory-service/v1/catalogs/%s",
                activeCatalog.getCatalogId()),
                Catalog.class);

        return catalog;
    }

    public Product getProduct(String productId) {
        return restTemplate.getForObject(
                String.format("http://inventory-service/v1/products/%s",
                productId),
                Product.class);
    }

}
