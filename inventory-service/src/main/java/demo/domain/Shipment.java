package demo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * A simple domain class for the {@link Shipment}
 *
 * @author Kenny Bastani
 * @author Josh Long
 * @author Davi Monteiro
 */
@Entity
@Getter @Setter @ToString
public class Shipment {

    @Id @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "shipment")
    private Set<Inventory> inventories = new HashSet<>();

    @OneToOne
    private Address deliveryAddress;

    @OneToOne
    private Warehouse fromWarehouse;

    @Enumerated(EnumType.ORDINAL)
    private ShipmentStatus shipmentStatus;

    public Shipment() {
    }

    public Shipment(Set<Inventory> inventories, Address deliveryAddress, Warehouse fromWarehouse, ShipmentStatus shipmentStatus) {
        this.inventories = inventories;
        this.deliveryAddress = deliveryAddress;
        this.fromWarehouse = fromWarehouse;
        this.shipmentStatus = shipmentStatus;
    }

    public enum ShipmentStatus {
        PENDING,
        SHIPPED,
        DELIVERED
    }
}
