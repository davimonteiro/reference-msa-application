package demo.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


/**
 * This entity stores information about a {@link CatalogInfo}.
 *
 * @author Kenny Bastani
 * @author Josh Long
 * @author Davi Monteiro
 */
@Data
@Entity
public class CatalogInfo {

    @Id
    @GeneratedValue
    private Long id;
    private Long catalogId;
    private Boolean active;

}
