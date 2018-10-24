package demo;

import demo.domain.CatalogInfo;
import demo.repository.CatalogInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Initialize and populate the local database.
 *
 * @author Davi Monteiro
 */
@Service
public class DatabaseInitializer {

    @Autowired
    private CatalogInfoRepository catalogInfoRepository;

    @Transactional
    public void populate() {
        CatalogInfo catalogInfo = new CatalogInfo();
        catalogInfo.setId(0L);
        catalogInfo.setCatalogId(0L);
        catalogInfo.setActive(Boolean.TRUE);
        catalogInfoRepository.save(catalogInfo);
    }

}
