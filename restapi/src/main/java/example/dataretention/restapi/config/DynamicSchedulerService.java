package example.dataretention.restapi.config;

import example.dataretention.restapi.MultiTenantRecordCleanerService;
import example.dataretention.shared.domain.Table;
import example.dataretention.shared.domain.Tenant;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DynamicSchedulerService {
    private final ThreadPoolTaskScheduler taskScheduler;
    private final MultiTenantRecordCleanerService cleanerService;
    private final PurgeDetails purgeDetails;

    public DynamicSchedulerService(ThreadPoolTaskScheduler taskScheduler,
                                   MultiTenantRecordCleanerService cleanerService,
                                   PurgeDetails purgeDetails) {
        this.taskScheduler = taskScheduler;
        this.cleanerService = cleanerService;
        this.purgeDetails = purgeDetails;
    }

    @PostConstruct
    public void scheduleTasks() {
        log.info("Initializing dynamic schedulers for tenants...");
        purgeDetails.getTenants().stream()
                .filter(Tenant::purge)  // purge is enabled
                .forEach(tenant -> {
                    String cron = tenant.cron();
                    String tenantName = tenant.tenantName();
                    taskScheduler.schedule(() -> triggerCleaningTask(tenantName, purgeDetails.getTables()), new CronTrigger(cron));
                    log.info("Scheduled cleaning task for tenant {} with cron {}", tenantName, cron);
                });
    }

    private void triggerCleaningTask(String tenantName, List<Table> tables) {
        log.info("Triggered cleaning task for tenant {}", tenantName);
        cleanerService.cleanOldRecords(tenantName, tables);
    }
}
