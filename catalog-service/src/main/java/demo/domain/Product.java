package demo.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Product implements Serializable {

    private Long id;
    private String name, productId, description;
    private Double unitPrice;
    private Boolean inStock;

}
