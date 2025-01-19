package example.dataretention.restapi;


import example.dataretention.restapi.config.MultiTenantProperties;
import example.dataretention.restapi.config.PurgeDetails;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Service
public class MultiTenantRecordCleanerService {
    private final RedissonClient redissonClient;
    private final JdbcTemplate jdbcTemplate;
    private final MultiTenantProperties multiTenantProperties;
    private final PurgeDetails purgeDetails;

    public MultiTenantRecordCleanerService(RedissonClient redissonClient, JdbcTemplate jdbcTemplate, MultiTenantProperties multiTenantProperties, PurgeDetails purgeDetails) {
        this.redissonClient = redissonClient;
        this.jdbcTemplate = jdbcTemplate;
        this.multiTenantProperties = multiTenantProperties;
        this.purgeDetails = purgeDetails;
    }

    @Scheduled(cron = "0 */2 * * * *") // Every 5 minutes
    public void cleanOldRecords() {
        log.info("CRON triggered");
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
        log.info("Trying to delete table: {}, using lock: {}", tableName, lockKey);

        RLock lock = redissonClient.getLock(lockKey);

        long startTime = System.currentTimeMillis(); // Start time for measuring lock wait

        try {
            if (lock.tryLock(10, 60, TimeUnit.SECONDS)) {
                long acquiredTime = System.currentTimeMillis(); // Time when lock was acquired
                log.info("Lock acquired for table: {} after waiting {} ms", tableName, (acquiredTime - startTime));

                var rowsDeleted = deleteOldRecords(tableName);
                log.info("Deleted {} rows from {}", rowsDeleted, tableName);
            } else {
                log.warn("Could not acquire lock for table: {} after waiting 10 seconds", tableName);
                throw new RuntimeException("Could not acquire lock for table: " + tableName);
            }
        } catch (InterruptedException e) {
            log.error("Interrupted while waiting for lock on table: {}", tableName, e);
            throw new RuntimeException(e);
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
                log.info("Lock released for table: {}", tableName);
            }
        }
    }


    private int deleteOldRecords(String tableName) throws InterruptedException {
        String deleteQuery = """
                    DELETE FROM %s 
                    WHERE reservation_date < NOW() - INTERVAL %d DAY
                    AND status NOT IN ('In Progress', 'Approved');
                """.formatted(tableName, multiTenantProperties.getDeletion().interval().getDays());

        log.info("deleteOldRecords: deleteQuery: {}", deleteQuery);

        var count = jdbcTemplate.update(deleteQuery);
        Thread.sleep(2000);
        return count;
    }
}
