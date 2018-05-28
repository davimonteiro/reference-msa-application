package demo.cart;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter @Setter @ToString
@Entity
public class CartEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    private CartEventType cartEventType;
    private Long userId;
    private String productId;
    private Integer quantity;

    public CartEvent() {
    }

    public CartEvent(CartEventType cartEventType, Long userId) {
        this.cartEventType = cartEventType;
        this.userId = userId;
    }

    public CartEvent(CartEventType cartEventType, Long userId, String productId, Integer quantity) {
        this.cartEventType = cartEventType;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public enum CartEventType {
        ADD_ITEM,
        REMOVE_ITEM,
        CLEAR_CART,
        CHECKOUT
    }
}
