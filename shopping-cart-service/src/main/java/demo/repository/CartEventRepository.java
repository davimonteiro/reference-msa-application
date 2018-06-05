package demo.repository;

import demo.cart.CartEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.stream.Stream;

@RepositoryRestResource
public interface CartEventRepository extends JpaRepository<CartEvent, Long> {

    Stream<CartEvent> findByUserId(Long userId);

}
