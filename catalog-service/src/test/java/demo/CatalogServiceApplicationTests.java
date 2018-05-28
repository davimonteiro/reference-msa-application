/*
package demo;


import demo.domain.CatalogInfo;
import demo.repository.CatalogInfoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
public class CatalogServiceApplicationTests {

    @Autowired
    private CatalogInfoRepository catalogInfoRepository;

    @Test
    public void createCatalogInfo() throws Exception {
        catalogInfoRepository.deleteAll();
        CatalogInfo catalogInfo = new CatalogInfo();
        catalogInfo.setCatalogId(0L);
        catalogInfo.setActive(true);
        catalogInfoRepository.save(catalogInfo);

        CatalogInfo actual = catalogInfoRepository.findCatalogByActive(true);

        assertEquals(catalogInfo, actual);
    }
}
*/
