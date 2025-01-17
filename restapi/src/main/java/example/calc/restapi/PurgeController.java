package example.calc.restapi;


import example.demo.shared.domain.TableProperties;
import example.demo.shared.domain.TenantProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api")
public class PurgeController {

    private final TenantProperties tenantProperties;
    private final TableProperties tableProperties;

    public PurgeController(TenantProperties tenantProperties, TableProperties tableProperties) {
        this.tenantProperties = tenantProperties;
        this.tableProperties = tableProperties;
    }

    @GetMapping("/tenants")
    public List<TenantProperties.Tenant> getTenantProperties() {
        return tenantProperties.getTenants();
    }

    @GetMapping("/tables")
    public List<TableProperties.Table> getTableProperties() {
        return tableProperties.getTables();
    }
}