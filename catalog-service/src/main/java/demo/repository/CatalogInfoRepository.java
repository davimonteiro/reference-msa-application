package demo.repository;

import demo.domain.CatalogInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * A {@link JpaRepository} for the {@link CatalogInfo} domain class that provides
 * basic data management capabilities that include paging and sorting results.
 *
 * @author Kenny Bastani
 * @author Josh Long
 * @author Davi Monteiro
 */
public interface CatalogInfoRepository extends JpaRepository<CatalogInfo, Long> {

    CatalogInfo findCatalogByActive(@Param("active") Boolean active);

}