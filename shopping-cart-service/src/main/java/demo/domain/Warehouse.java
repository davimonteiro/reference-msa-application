package demo.domain;

import lombok.Data;

@Data
public class Warehouse {

    private Long id;
    private String name;
    private Address address;

}
