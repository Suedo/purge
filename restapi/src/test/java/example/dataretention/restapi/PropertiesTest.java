package example.dataretention.restapi;

import example.dataretention.restapi.config.PurgeDetails;
import example.dataretention.shared.domain.Tenant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PropertiesTest {

    @Autowired
    PurgeDetails purgeDetails;

    @Test
    void shouldListTenants() {
        final List<Tenant> tenants = purgeDetails.getTenants();
        System.out.println(tenants.size());
    }

}
