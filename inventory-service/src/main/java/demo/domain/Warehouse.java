package demo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * A simple domain class for the {@link Warehouse}
 *
 * @author Kenny Bastani
 * @author Josh Long
 */
@Entity
@Getter @Setter @ToString
public class Warehouse {

    @Id @GeneratedValue
    private Long id;

    private String name;

    @OneToOne
    private Address address;

    public Warehouse() { }

    public Warehouse(String name) {
        this.name = name;
    }

}
