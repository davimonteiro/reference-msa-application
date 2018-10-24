package demo.domain;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * The {@link Catalog} class is responsible for storing information about a specific catalog.
 *
 * @author Kenny Bastani
 * @author Josh Long
 * @author Davi Monteiro
 */
@Data
public class Catalog {

    private Long id;
    private Long catalogNumber;
    private Set<Product> products = new HashSet<>();
    private String name;

    public Catalog() { }

    public Catalog(String name) {
        this.name = name;
    }

}
