package demo.service;


import demo.domain.*;
import demo.repository.InventoryRepository;
import demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;


/**
 * The {@link InventoryService} class provides services to retrieve products and verify available inventory.
 *
 * @author Kenny Bastani
 * @author Josh Long
 * @author Davi Monteiro
 */
@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public Product getProduct(String productId) {
        Product product = productRepository.findOneByProductId(productId);

        if (product != null) {
            Stream<Inventory> availableInventory = inventoryRepository.findByProduct_Id(product.getId()).stream();
            product.setInStock(availableInventory.findAny().isPresent());
        }

        return product;
    }

    @Transactional(readOnly = true)
    public ShoppingCart checkAvailability(ShoppingCart currentCart) throws Exception {
        List<Inventory> inventoryList = getInventoryByShoppingCart(currentCart);

        Map<String, Long> inventoryItems = inventoryList
                .stream()
                .map(inv -> inv.getProduct().getProductId())
                .collect(groupingBy(Function.identity(), counting()));

        List<LineItem> inventoryAvailable = currentCart.getLineItems()
                .stream()
                .filter(item -> inventoryItems.get(item.getProductId()) - item.getQuantity() < 0)
                .collect(Collectors.toList());

        if (inventoryAvailable.size() > 0) {
            String productIdList = inventoryAvailable
                    .stream()
                    .map(LineItem::getProductId)
                    .collect(Collectors.joining(", "));
            throw new Exception(String.format("Insufficient inventory available for %s. " +
                    "Lower the quantity of these products and try again.", productIdList));
        }

        return currentCart;
    }

    public List<Inventory> getInventoryByShoppingCart(ShoppingCart shoppingCart) {
        List<String> ids = shoppingCart.getLineItems().stream()
                .map(item -> item.getProductId())
                .collect(Collectors.toList());
        return getInventories(ids);
    }

    public List<Inventory> getInventoryByShoppingCart(String productIds) {
        String[] ids = productIds.split(",");
        return getInventories(Arrays.asList(ids));
    }

    private List<Inventory> getInventories(List<String> productIds) {
        List<Inventory> inventoryList = new ArrayList<>();

        for (String id : productIds) {
            Product product = productRepository.findOneByProductId(id);
            List<Inventory> list = inventoryRepository.findByProduct_Id(product.getId());
            if (list != null && !list.isEmpty()) {
                inventoryList.addAll(list);
            }
        }

        return inventoryList;
    }

}
