package example.calc.restapi;


import example.demo.shared.domain.TenantProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/test")
public class PurgeController {

    private final PurgeService purgeService;
    private final TenantProperties tenantProperties;

    public PurgeController(PurgeService purgeService, TenantProperties tenantProperties) {
        this.purgeService = purgeService;
        this.tenantProperties = tenantProperties;
    }

    @GetMapping
    public ResponseEntity<String> testFlow() {
        String result = purgeService.testFlow();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/tenants")
    public TenantProperties getTenantProperties() {
        return tenantProperties;
    }
}
