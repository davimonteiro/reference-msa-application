package demo.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class CatalogInfo {

    @Id @GeneratedValue
    private Long id;
    private Long catalogId;
    private Boolean active;

}
