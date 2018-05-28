package demo;

import demo.domain.CatalogInfo;
import demo.repository.CatalogInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"development"})
public class DatabaseInitializer {

    @Autowired
    private CatalogInfoRepository catalogInfoRepository;

    public void populate() throws Exception {
        CatalogInfo catalogInfo = new CatalogInfo();
        catalogInfo.setId(0L);
        catalogInfo.setCatalogId(0L);
        catalogInfo.setActive(Boolean.TRUE);
        catalogInfoRepository.save(catalogInfo);
    }

}
