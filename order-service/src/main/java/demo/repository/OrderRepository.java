package demo.repository;

import demo.domain.Order;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {
    List<Order> findByAccountNumber(String accountNumber);
}
