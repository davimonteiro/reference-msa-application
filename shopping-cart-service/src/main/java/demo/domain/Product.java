package demo.domain;

import lombok.Data;

/**
 * A simple domain class for the {@link Product} concept in the order context.
 *
 * @author Kenny Bastani
 * @author Josh Long
 */
@Data
public class Product {

    private Long id;
    private String name, productId, description;
    private Double unitPrice;

    public Product() {
    }

    public Product(String name, String productId, Double unitPrice) {
        this.name = name;
        this.productId = productId;
        this.unitPrice = unitPrice;
    }

    public Product(String name, String productId, String description, Double unitPrice) {
        this.name = name;
        this.productId = productId;
        this.description = description;
        this.unitPrice = unitPrice;
    }

}
