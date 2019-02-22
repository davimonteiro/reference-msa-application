package demo.repository;

import demo.domain.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface CatalogRepository extends JpaRepository<Catalog, Long> {

    Catalog findCatalogByCatalogNumber(@Param("catalogNumber") Long catalogNumber);

}
