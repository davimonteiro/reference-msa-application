package demo.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class Catalog implements Serializable {

    private Long id;
    private Long catalogNumber;
    private Set<Product> products = new HashSet<>();
    private String name;

    public Catalog() { }

    public Catalog(String name) {
        this.name = name;
    }

}
