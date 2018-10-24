package demo.repository;

import demo.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * A {@link PagingAndSortingRepository} for the {@link Account} domain class that provides
 * basic data management capabilities that include paging and sorting results.
 *
 * @author Kenny Bastani
 * @author Josh Long
 * @author Davi Monteiro
 */
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findAccountsByUserId(Long userId);

}
