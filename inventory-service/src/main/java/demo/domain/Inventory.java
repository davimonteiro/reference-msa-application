package demo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * A simple {@link Inventory} entity for storing inventory's information.
 *
 * @author Kenny Bastani
 * @author Josh Long
 * @author Davi Monteiro
 */
@Entity
@Getter @Setter @ToString
public class Inventory {

    @Id @GeneratedValue
    private Long id;

    private String inventoryNumber;

    @OneToOne
    private Product product;

    @OneToOne
    private Warehouse warehouse;

    @Enumerated(EnumType.ORDINAL)
    private InventoryStatus status;

    @ManyToOne
    @JoinColumn(name = "shipment")
    private Shipment shipment;

    public Inventory() { }

    public Inventory(String inventoryNumber, Product product) {
        this.inventoryNumber = inventoryNumber;
        this.product = product;
    }

    public Inventory(String inventoryNumber, Product product, Warehouse warehouse, InventoryStatus status) {
        this.inventoryNumber = inventoryNumber;
        this.product = product;
        this.warehouse = warehouse;
        this.status = status;
    }

    public enum InventoryStatus {
        IN_STOCK,
        ORDERED,
        RESERVED,
        SHIPPED,
        DELIVERED
    }
}
