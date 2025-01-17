package example.dataretention.restapi;

import example.dataretention.shared.config.TenantProperties;
import example.dataretention.shared.domain.Tenant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PropertiesTest {

    @Autowired
    TenantProperties tenantProperties;

    @Test
    void shouldListTenants() {
        final List<Tenant> tenants = tenantProperties.tenants();
        System.out.println(tenants.size());
    }

}
