package demo.api.v1;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import demo.repository.CatalogInfoRepository;
import demo.domain.Catalog;
import demo.domain.CatalogInfo;
import demo.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.stream.Collectors;

@Service
public class CatalogServiceV1 {

    @Autowired
    private CatalogInfoRepository catalogInfoRepository;


    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;

    @HystrixCommand
    public Catalog getCatalog() {
        CatalogInfo activeCatalog = catalogInfoRepository.findCatalogByActive(true);

        /*Catalog catalog = restTemplate.getForObject(String.format("http://inventory-service/api/catalogs/search/findCatalogByCatalogNumber?catalogNumber=%s",
                activeCatalog.getCatalogId()), Catalog.class);*/

        Catalog catalog = restTemplate.getForObject(String.format("http://inventory-service/v1/catalogs/%s",
                activeCatalog.getCatalogId()), Catalog.class);


        /*ProductsResource products = restTemplate.getForObject(String.format("http://inventory-service/api/catalogs/%s/products",
                catalog.getId()), ProductsResource.class);
        catalog.setProducts(products.getContent().stream().collect(Collectors.toSet()));*/

        return catalog;
    }

    @HystrixCommand
    public Product getProduct(String productId) {
        return restTemplate.getForObject(String.format("http://inventory-service/v1/products/%s",
                productId), Product.class);
    }
}
