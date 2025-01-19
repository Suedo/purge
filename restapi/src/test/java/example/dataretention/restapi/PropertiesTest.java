package example.dataretention.restapi;

import example.dataretention.restapi.config.MultiTenantProperties;
import example.dataretention.restapi.config.PurgeDetails;
import example.dataretention.restapi.config.properties.Retry;
import example.dataretention.shared.domain.Tenant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("purge")
public class PropertiesTest {

    @Autowired
    PurgeDetails purgeDetails;
    @Autowired
    MultiTenantProperties multiTenantProperties;

    @Test
    void shouldListTenants() {
        final List<Tenant> tenants = purgeDetails.getTenants();
        System.out.println(tenants.size());
    }

    @Test
    void shouldListPurgeDetails() {
        final Retry retry = multiTenantProperties.getRetry();
        System.out.println(retry.backoff());
    }

}
