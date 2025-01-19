package example.dataretention.restapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class SchedulerConfig {
    @Bean
    public ThreadPoolTaskScheduler taskScheduler(PurgeDetails purgeDetails) {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(purgeDetails.getTenants().size());
        scheduler.setThreadNamePrefix("DynamicScheduler-");
        return scheduler;
    }
}
