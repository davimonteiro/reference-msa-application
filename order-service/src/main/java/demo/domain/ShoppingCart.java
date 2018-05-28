package demo.domain;


import lombok.Data;

import java.util.List;

@Data
public class ShoppingCart {

    private List<LineItem> lineItems;

}
