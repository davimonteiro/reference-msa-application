package demo.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
public class Catalog implements Serializable {

    private Long id;
    private Set<Product> products = new HashSet<>();
    private String name;

}
