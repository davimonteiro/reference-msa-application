package demo.domain;


import lombok.Data;

import java.util.List;

/**
 * A simple domain class for the {@link ShoppingCart}.
 *
 * @author Kenny Bastani
 * @author Josh Long
 * @author Davi Monteiro
 */
@Data
public class ShoppingCart {

    private List<LineItem> lineItems;

}
