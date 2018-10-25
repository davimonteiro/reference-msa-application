package demo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * A simple domain class for the {@link Product} concept in the order context.
 *
 * @author Kenny Bastani
 * @author Josh Long
 * @author Davi Monteiro
 */
@Entity
@Getter @Setter @ToString
public class Product {

    @Id @GeneratedValue
    private Long id;

    private String name;

    private String productId;

    @Lob
    private String description;

    private Double unitPrice;

    @ManyToOne
    @JoinColumn(name = "catalog")
    private Catalog catalog;

    @Transient
    private Boolean inStock;

    public Product() { }

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
