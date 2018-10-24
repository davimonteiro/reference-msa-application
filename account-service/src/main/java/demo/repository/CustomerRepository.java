package demo.repository;

import demo.domain.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * A {@link PagingAndSortingRepository} for the {@link Customer} domain class that provides
 * basic data management capabilities that include paging and sorting results.
 *
 * @author Kenny Bastani
 * @author Josh Long
 * @author Davi Monteiro
 */
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {

}
