package demo.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import demo.domain.Address;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple domain class for the {@link Order} concept in the order context.
 *
 * @author Kenny Bastani
 * @author Josh Long
 */
@Getter @Setter @ToString
public class Order implements Serializable {

    private Long id;
    private String accountNumber;
    private OrderStatus orderStatus;
    private List<LineItem> lineItems = new ArrayList<>();
    private Address shippingAddress;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Date createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Date lastModified;

    /**
     * Describes the state of an {@link Order}.
     *
     * @author Kenny Bastani
     * @author Josh Long
     */
    public enum OrderStatus {
        PURCHASED,
        PENDING,
        CONFIRMED,
        SHIPPED,
        DELIVERED
    }
}
