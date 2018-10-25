package demo.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Authority implements GrantedAuthority {

    @Id
    private String authority;

}
