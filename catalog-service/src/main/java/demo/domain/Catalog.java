package demo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

/**
 * A simple {@link Catalog} entity for a catalog.
 *
 * @author Kenny Bastani
 * @author Josh Long
 * @author Davi Monteiro
 */
@Entity
@Getter @Setter @ToString
public class Catalog {

    @Id @GeneratedValue
    private Long id;
    private String name;
    private Long catalogNumber;
    @OneToMany(mappedBy = "catalog", cascade = ALL)
    private List<Product> products = new ArrayList<>();

}
