package demo.api.v1;

import demo.beethoven.BeethovenOperation;
import demo.beethoven.ContextualInput;
import demo.beethoven.WorkflowInstance;
import demo.cart.CartEvent;
import demo.cart.ShoppingCart;
import demo.domain.*;
import demo.order.Order;
import demo.order.OrderEvent;
import demo.repository.CartEventRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.google.common.collect.Sets.newHashSet;
import static demo.beethoven.BeethovenOperation.Operation.SCHEDULE;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

/**
 * The {@link ShoppingCartServiceV1} implements business logic for aggregating the state of
 * a user's actions represented by a sequence of {@link CartEvent}. The generated aggregate
 * uses event sourcing to produce a {@link ShoppingCart} containing a collection of
 * {@link LineItem}.
 *
 * @author Ben Hale
 * @author Kenny Bastani
 */
@Service
public class ShoppingCartServiceV1 {

    public static final double TAX = .06;
    private final Log log = LogFactory.getLog(ShoppingCartServiceV1.class);

    private OAuth2RestTemplate oAuth2RestTemplate;
    private CartEventRepository cartEventRepository;
    private RestTemplate restTemplate;

    // TODO Remove
    private static final String REPORT_CSV_FILE = "/Users/davimonteiro/Desktop/report_original.csv";
    private final AtomicInteger count = new AtomicInteger();

    @Autowired
    public ShoppingCartServiceV1(CartEventRepository cartEventRepository,
                                 @LoadBalanced OAuth2RestTemplate oAuth2RestTemplate,
                                 @LoadBalanced RestTemplate normalRestTemplate) {
        this.cartEventRepository = cartEventRepository;
        this.oAuth2RestTemplate = oAuth2RestTemplate;
        this.restTemplate = normalRestTemplate;
    }

    /**
     * Get the authenticated user from the user service
     *
     * @return the currently authenticated user
     */
    public User getAuthenticatedUser() {
        return oAuth2RestTemplate.getForObject("http://user-service/uaa/v1/me", User.class);
    }

    /**
     * Adds a shopping cart event for the authenticated user
     *
     * @param cartEvent is the event detailing the action performed by the user
     * @return a flag indicating whether the result was a success
     */
    public Boolean addCartEvent(CartEvent cartEvent) {
        User user = getAuthenticatedUser();
        if (user != null) {
            cartEvent.setUserId(user.getId());
            cartEventRepository.save(cartEvent);
        } else {
            return null;
        }
        return true;
    }

    public Boolean addCartEvent(CartEvent cartEvent, User user) {
        if (user != null) {
            cartEvent.setUserId(user.getId());
            cartEventRepository.save(cartEvent);
        } else {
            return null;
        }
        return true;
    }

    /**
     * Get the shopping cart for the currently authenticated user
     *
     * @return an aggregate object derived from events performed by the user
     * @throws Exception
     */
    public ShoppingCart getShoppingCart() throws Exception {
        User user = oAuth2RestTemplate.getForObject("http://user-service/uaa/v1/me", User.class);
        ShoppingCart shoppingCart = null;
        if (user != null) {
            System.err.println(user.toString());
            Catalog catalog = restTemplate.getForObject("http://catalog-service/v1/catalog", Catalog.class);
            shoppingCart = aggregateCartEvents(user, catalog);
        }
        return shoppingCart;
    }

    public ShoppingCart getShoppingCartOrchestrated() throws Exception {
        return getShoppingCart();
    }

    /**
     * Aggregate the cart events of a user and return a {@link ShoppingCart} object
     *
     * @param user    is the user to retrieve the shopping cart for
     * @param catalog is the catalog used to generate the shopping cart
     * @return a shopping cart representing the aggregate state of the user's cart
     * @throws Exception
     */
    @Transactional
    public ShoppingCart aggregateCartEvents(User user, Catalog catalog) throws Exception {
        Flux<CartEvent> cartEvents = Flux.fromStream((cartEventRepository.findByUserId(user.getId()).stream()));

        // Aggregate the state of the shopping cart
        ShoppingCart shoppingCart = cartEvents
                .takeWhile(cartEvent -> !ShoppingCart.isTerminal(cartEvent.getCartEventType()))
                .reduceWith(() -> new ShoppingCart(catalog), ShoppingCart::incorporate)
                .get();

        shoppingCart.getLineItems();

        return shoppingCart;
    }

    public Boolean clearShoppingCart() {
        User user = oAuth2RestTemplate.getForObject("http://user-service/uaa/v1/me", User.class);
        return addCartEvent(new CartEvent(CartEvent.CartEventType.CHECKOUT, user.getId()), user);
    }

    public CheckoutResult checkoutOrchestrated() {
        OAuth2AccessToken accessToken = oAuth2RestTemplate.getAccessToken();
        Set<ContextualInput> inputs = newHashSet(new ContextualInput(accessToken));

        BeethovenOperation beethovenOperation = new BeethovenOperation();
        beethovenOperation.setWorkflowName("checkoutProcess");
        beethovenOperation.setOperation(SCHEDULE.getId());
        beethovenOperation.setInputs(inputs);

        restTemplate.postForEntity(
                "http://beethoven-service/api/workflows/checkoutProcess/operations",
                beethovenOperation, BeethovenOperation.class);

        return new CheckoutResult();
    }

    /**
     * Checkout the user's current cart and create a new order after processing the payment
     *
     * @return the result of the checkout operation
     */
    public CheckoutResult checkout() throws Exception {
        WorkflowInstance workflowInstance = new WorkflowInstance();
        workflowInstance.setWorkflowName("checkoutProcess");
        workflowInstance.setWorkflowInstanceName("checkoutProcess-" + count.getAndIncrement());
        workflowInstance.setStartTime(LocalDateTime.now());

        CheckoutResult checkoutResult = new CheckoutResult();

        // Check available inventory
        ShoppingCart currentCart = null;
        try {
            currentCart = getShoppingCart();
        } catch (Exception e) {
            log.error("Could not retrieve shopping cart", e);
        }

        if (currentCart != null) {
            // Reconcile the current cart with the available inventory
            Inventory[] inventory =
                    oAuth2RestTemplate.getForObject(String.format("http://inventory-service/v1/inventory?productIds=%s", currentCart.getLineItems()
                            .stream()
                            .map(LineItem::getProductId)
                            .collect(Collectors.joining(","))), Inventory[].class);

            if (inventory != null) {
                Map<String, Long> inventoryItems = Arrays.asList(inventory)
                        .stream()
                        .map(inv -> inv.getProduct().getProductId())
                        .collect(groupingBy(Function.identity(), counting()));

                if (checkAvailableInventory(checkoutResult, currentCart, inventoryItems)) {
                    // Reserve the available inventory

                    // Create a new order
                    Order orderResponse = oAuth2RestTemplate.postForObject("http://order-service/v1/orders",
                            currentCart.getLineItems().stream()
                                    .map(prd ->
                                            new demo.order.LineItem(prd.getProduct().getName(),
                                                    prd.getProductId(), prd.getQuantity(),
                                                    prd.getProduct().getUnitPrice(), TAX))
                                    .collect(Collectors.toList()),
                            Order.class);

                    if (orderResponse != null) {
                        // Order creation successful
                        checkoutResult.setResultMessage("Order created");

                        // Add order event
                        oAuth2RestTemplate.postForEntity(String.format("http://order-service/v1/orders/%s/events", orderResponse.getId()),
                                new OrderEvent(OrderEvent.OrderEventType.CREATED, orderResponse.getId()), OrderEvent.class);

                        checkoutResult.setOrder(orderResponse);
                    }

                    User user = oAuth2RestTemplate.getForObject("http://user-service/uaa/v1/me", User.class);

                    // Clear the shopping cart
                    addCartEvent(new CartEvent(CartEvent.CartEventType.CHECKOUT, user.getId()), user);
                }
            }
        }

        workflowInstance.setEndTime(LocalDateTime.now());
        workflowInstance.setSuccessful(checkoutResult.getOrder() != null);
        if (workflowInstance.isSuccessful()) {
            reportToCsv(workflowInstance);
        }

        // Return errors with available inventory
        return checkoutResult;
    }

    public Boolean checkAvailableInventory(CheckoutResult checkoutResult, ShoppingCart currentCart,
                                           Map<String, Long> inventoryItems) {
        Boolean hasInventory = true;
        // Determine if inventory is available
        try {
            List<LineItem> inventoryAvailable = currentCart.getLineItems()
                    .stream()
                    .filter(item -> inventoryItems.get(item.getProductId()) - item.getQuantity() < 0)
                    .collect(Collectors.toList());
            if (inventoryAvailable.size() > 0) {
                String productIdList = inventoryAvailable
                        .stream()
                        .map(LineItem::getProductId)
                        .collect(Collectors.joining(", "));
                checkoutResult.setResultMessage(
                        String.format("Insufficient inventory available for %s. " +
                                "Lower the quantity of these products and try again.", productIdList));
                hasInventory = false;
            }
        } catch (Exception e) {
            log.error("Error checking for available inventory", e);
        }
        return hasInventory;
    }

    private void reportToCsv(WorkflowInstance workflowInstance) {
        try (
                BufferedWriter writer = Files.newBufferedWriter(
                        Paths.get(REPORT_CSV_FILE),
                        StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND);
                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)
        ) {
            csvPrinter.printRecord(Arrays.asList(
                    workflowInstance.getWorkflowName(),
                    workflowInstance.getWorkflowInstanceName(),
                    workflowInstance.getStartTime().toString(),
                    workflowInstance.getEndTime().toString(),
                    workflowInstance.elapsedTime().toMillis(),
                    workflowInstance.isSuccessful()));
            csvPrinter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
