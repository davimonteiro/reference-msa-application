package demo.domain;

import demo.order.Order;
import lombok.Data;

import java.io.Serializable;

@Data
public class CheckoutResult implements Serializable {

    private String resultMessage;
    private Order order;

}
