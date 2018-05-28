package demo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

/**
 * A simple domain class for the {@link LineItem} concept in the order context.
 *
 * @author Kenny Bastani
 */
@Entity
@Table(name = "line_item")
@Getter @Setter @ToString
public class LineItem {

    @Id @GeneratedValue
    private Long id;
    private String name;
    private String productId;
    private Integer quantity;
    private Double price;
    private Double tax;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "orderTable")
    private Order orderTable;

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
