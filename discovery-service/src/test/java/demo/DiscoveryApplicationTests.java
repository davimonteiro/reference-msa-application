package demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DiscoveryApplication.class)
@ActiveProfiles(profiles = "test")
public class DiscoveryApplicationTests {

    @Test
    public void contextLoads() {
    }

}