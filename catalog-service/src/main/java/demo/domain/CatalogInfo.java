package demo.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
public class CatalogInfo implements Serializable {

    @Id
    private Long id;
    private Long catalogId;
    private Boolean active;

}
