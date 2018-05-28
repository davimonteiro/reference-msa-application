package demo.domain;

import lombok.Data;

@Data
public class Inventory {

    private Long id;
    private String inventoryNumber;
    private Product product;
    private Warehouse warehouse;
    private InventoryStatus status;


    public enum InventoryStatus {
        IN_STOCK,
        ORDERED,
        RESERVED,
        SHIPPED,
        DELIVERED
    }
}
