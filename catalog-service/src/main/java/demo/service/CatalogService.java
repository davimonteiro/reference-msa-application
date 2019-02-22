package demo.service;


import demo.domain.Catalog;
import demo.domain.CatalogInfo;
import demo.domain.Product;
import demo.repository.CatalogInfoRepository;
import demo.repository.CatalogRepository;
import demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * The {@link CatalogService} class provides a service to retrieve catalogs and products.
 *
 * @author Kenny Bastani
 * @author Josh Long
 * @author Davi Monteiro
 */
@Service
public class CatalogService {

    @Autowired
    private CatalogInfoRepository catalogInfoRepository;
    @Autowired
    private CatalogRepository catalogRepository;
    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public Catalog getCatalog() {
        CatalogInfo activeCatalog = catalogInfoRepository.findCatalogByActive(true);
        Catalog catalog = catalogRepository.findCatalogByCatalogNumber(activeCatalog.getCatalogId());
        catalog.setProducts(productRepository.findAll());
        return catalog;
    }

    public Product getProduct(String productId) {
        return productRepository.findOneByProductId(productId);
    }

    @Transactional(readOnly = true)
    public Catalog findCatalogByCatalogNumber(Long catalogNumber) {
        Catalog catalog = catalogRepository.findCatalogByCatalogNumber(catalogNumber);
        catalog.setProducts(productRepository.findAll());
        return catalog;
    }

}
