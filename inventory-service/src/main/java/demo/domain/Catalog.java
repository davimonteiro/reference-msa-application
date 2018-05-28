package demo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;

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
