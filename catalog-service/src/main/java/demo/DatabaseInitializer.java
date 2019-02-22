package demo;

import demo.domain.Catalog;
import demo.domain.CatalogInfo;
import demo.domain.Product;
import demo.repository.CatalogInfoRepository;
import demo.repository.CatalogRepository;
import demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Initialize and populate the local database.
 *
 * @author Davi Monteiro
 */
@Service
public class DatabaseInitializer {

    @Autowired
    private CatalogInfoRepository catalogInfoRepository;
    @Autowired
    private CatalogRepository catalogRepository;
    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public void populate() {
        // Clear existing data
        clearExistingData();

        // Create the products
        List<Product> products = getProducts();

        // Create the catalog
        Catalog catalog = getCatalog(products);

        // Create the catalog info
        CatalogInfo catalogInfo = getCatalogInfo();

        productRepository.saveAll(products);
        catalogRepository.save(catalog);
        catalogInfoRepository.save(catalogInfo);
    }

    private CatalogInfo getCatalogInfo() {
        CatalogInfo catalogInfo = new CatalogInfo();
        catalogInfo.setId(0L);
        catalogInfo.setCatalogId(0L);
        catalogInfo.setActive(Boolean.TRUE);
        return catalogInfo;
    }

    private Catalog getCatalog(List<Product> products) {
        Catalog catalog = new Catalog();
        catalog.setName("Fall Catalog");
        catalog.setCatalogNumber(0L);
        catalog.setProducts(products);

        return catalog;
    }

    private void clearExistingData() {
        catalogInfoRepository.deleteAll();
        catalogRepository.deleteAll();
        productRepository.deleteAll();
    }

    private List<Product> getProducts() {
        return new ArrayList<>(Arrays.asList(

                new Product("Best. Cloud. Ever. (T-Shirt, Men's Large)", "SKU-24642", "<p>Do you love your cloud platform? " +
                        "Do you push code continuously into production on a daily basis? " +
                        "Are you living the cloud native microservice dream? Then rain or shine, this T-Shirt is for you. " +
                        "Show the world you're a stylish cloud platform architect with this cute yet casual tee. " +
                        "<br /><br />&nbsp; <strong>Cloud Native Tee Collection</strong><br />" +
                        "&nbsp; 110% cloud stuff, 5% spandex<br />&nbsp; Rain wash only<br />&nbsp; " +
                        "Four nines of <em>stylability</em></p>", 21.99),

                new Product("Like a BOSH (T-Shirt, Women's Medium)", "SKU-34563", "<p>The BOSH Outer Shell (<strong>BOSH</strong>) " +
                        "is an elegant release engineering tool for a more virtualized cloud-native age. " +
                        "The feeling of spinning up a highly available distributed system of VMs is second only to the " +
                        "feeling of frequently pushing code to production. Show the cloud <em>who's BOSH</em> with " +
                        "this stylish cloud native ops tee.<br /><br />&nbsp; <strong>Cloud Native Tee Collection</strong><br />&nbsp; " +
                        "99% YAML, 11% CLI<br />&nbsp; BOSH CCK <span style='text-decoration: underline;'><em>recommended</em></span><br />&nbsp; " +
                        "4 nines of <em>re-washability</em></p>", 14.99),

                new Product("We're gonna need a bigger VM (T-Shirt, Women's Small)", "SKU-12464", "<i>\"Mr. Vaughn, what we are dealing with here is " +
                        "a perfect engine, an eating machine. It's really a miracle of evolution. All this machine does is swim and eat and make " +
                        "little containers, and that's all.\"</i>", 13.99),

                new Product("cf push awesome (Hoodie, Men's Medium)", "SKU-64233",
                        "<p>One of the great natives of the cloud once said \"<em>" +
                                "Production is the happiest place on earth for us - it's better than Disneyland</em>\". " +
                                "With this stylish Cloud Foundry hoodie you can push code to the cloud all day while staying " +
                                "comfortable and casual. <br /><br />&nbsp; <strong>Cloud Native PaaS Collection</strong><br />" +
                                "&nbsp; 10% cloud stuff, 90% platform nylon<br />&nbsp; Cloud wash safe<br />" +
                                "&nbsp; Five nines of <em>comfortability</em></p>", 21.99)));
    }

}
