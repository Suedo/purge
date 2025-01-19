package example.dataretention.restapi;


import example.dataretention.restapi.config.MultiTenantProperties;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class MultiTenantRecordCleanerService {
    private final RedissonClient redissonClient;
    private final JdbcTemplate jdbcTemplate;
    private final MultiTenantProperties multiTenantProperties;

    public MultiTenantRecordCleanerService(RedissonClient redissonClient, JdbcTemplate jdbcTemplate, MultiTenantProperties multiTenantProperties) {
        this.redissonClient = redissonClient;
        this.jdbcTemplate = jdbcTemplate;
        this.multiTenantProperties = multiTenantProperties;
    }

    @Scheduled(fixedRateString = "#{@multiTenantProperties.schedule.fixedRate.toMillis()}")
    public void cleanOldRecords() {
        // New logic: Dynamic list of clients and tables
        List<String> clients = List.of("INFOSYS", "TCS");
        List<String> tables = List.of("FleetData", "EmployeeRequests");

        for (String client : clients) {
            for (String table : tables) {
                String tableName = client + "_" + table;
                cleanTableTransactional(tableName);
            }
        }
    }

    @Retryable(
            maxAttemptsExpression = "#{@multiTenantProperties.retry.maxAttempts}",
            backoff = @Backoff(delayExpression = "#{@multiTenantProperties.retry.backoff.toMillis()}", multiplier = 2)
    )
    @Transactional
    public void cleanTableTransactional(String tableName) {
        String lockKey = "lock:" + tableName;
        RLock lock = redissonClient.getLock(lockKey);

        try {
            if (lock.tryLock(10, 60, TimeUnit.SECONDS)) {
                deleteOldRecords(tableName);
            } else {
                throw new RuntimeException("Could not acquire lock for table: " + tableName);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (lock.isHeldByCurrentThread()) lock.unlock();
        }
    }

    private void deleteOldRecords(String tableName) {
        String deleteQuery = """
                    DELETE FROM %s 
                    WHERE created_at < NOW() - INTERVAL %d DAY
                    AND status NOT IN ('In Progress', 'Approved');
                """.formatted(tableName, multiTenantProperties.getDeletion().interval().getDays());

        jdbcTemplate.update(deleteQuery);
    }
}
