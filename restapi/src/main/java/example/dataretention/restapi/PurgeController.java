package example.dataretention.restapi;


import example.dataretention.restapi.config.PurgeDetails;
import example.dataretention.shared.domain.Table;
import example.dataretention.shared.domain.Tenant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api")
public class PurgeController {

    private final PurgeDetails purgeDetails;

    public PurgeController(PurgeDetails purgeDetails) {
        this.purgeDetails = purgeDetails;
    }

    @GetMapping("/tenants")
    public List<Tenant> getTenantProperties() {
        return purgeDetails.getTenants();
    }

    @GetMapping("/tables")
    public List<Table> getTableProperties() {
        return purgeDetails.getTables();
    }
}