package demo.domain;

import lombok.Data;

/**
 * A simple {@link LineItem} class for storing inventory's information.
 *
 * @author Kenny Bastani
 * @author Josh Long
 * @author Davi Monteiro
 */
@Data
public class LineItem {

    private String productId;
    private Integer quantity;

}
