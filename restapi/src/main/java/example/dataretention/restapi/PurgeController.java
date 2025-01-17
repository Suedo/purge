package example.dataretention.restapi;


import example.dataretention.shared.config.TableProperties;
import example.dataretention.shared.config.TenantProperties;
import example.dataretention.shared.domain.Table;
import example.dataretention.shared.domain.Tenant;
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
    public List<Tenant> getTenantProperties() {
        return tenantProperties.tenants();
    }

    @GetMapping("/tables")
    public List<Table> getTableProperties() {
        return tableProperties.tables();
    }
}