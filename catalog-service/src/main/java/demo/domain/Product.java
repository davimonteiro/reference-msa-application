package demo.domain;

import lombok.Data;

/**
 * The {@link Product} class is responsible for storing information about a specific product.
 *
 * @author Kenny Bastani
 * @author Josh Long
 * @author Davi Monteiro
 */
@Data
public class Product {

    private Long id;
    private String name;
    private String productId;
    private String description;
    private Double unitPrice;
    private Boolean inStock;

}