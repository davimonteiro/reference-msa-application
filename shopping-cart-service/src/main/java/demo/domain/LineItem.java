package demo.domain;

import lombok.Data;

@Data
public class LineItem {

    private String productId;
    private Product product;
    private Integer quantity;

    public LineItem(String productId, Product product, Integer quantity) {
        this.productId = productId;
        this.product = product;
        this.quantity = quantity;
    }

}
