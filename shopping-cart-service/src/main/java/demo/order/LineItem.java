package demo.order;

import lombok.Data;

/**
 * A simple domain class for the {@link LineItem} concept in the order context.
 *
 * @author Kenny Bastani
 * @author Josh Long
 */
@Data
public class LineItem {

    private String name, productId;
    private Integer quantity;
    private Double price, tax;

    public LineItem() { }

    public LineItem(String name, String productId, Integer quantity,
                    Double price, Double tax) {
        this.name = name;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.tax = tax;
    }

}
