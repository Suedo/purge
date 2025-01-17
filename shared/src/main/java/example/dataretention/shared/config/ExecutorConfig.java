package example.dataretention.shared.config;

import io.micrometer.context.ContextExecutorService;
import io.micrometer.context.ContextSnapshot;
import io.micrometer.context.ContextSnapshotFactory;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.jvm.ExecutorServiceMetrics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnThreading;
import org.springframework.boot.autoconfigure.thread.Threading;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Slf4j
@Configuration
public class ExecutorConfig {
    private ExecutorService executorService;

    @Bean
    @ConditionalOnThreading(Threading.VIRTUAL)
    public ExecutorService virtualThreadExecutor(MeterRegistry registry) {

        // https://stackoverflow.com/a/78765658/2715083
        // automatic context propagation via micrometer context propagation support
        log.info("Creating Virtual Thread Executor");
        this.executorService = ContextExecutorService.wrap(
                Executors.newVirtualThreadPerTaskExecutor(),
                (Supplier<ContextSnapshot>) () -> ContextSnapshotFactory.builder().build().captureAll()
        );
        ExecutorServiceMetrics.monitor(registry, executorService, "custom.executor");
        return this.executorService;
    }

    @Bean
    @ConditionalOnThreading(Threading.PLATFORM)
    public ExecutorService platformThreadExecutor(MeterRegistry registry) {
        log.info("Creating Platform Thread Executor");
        this.executorService = Executors.newCachedThreadPool();
        ExecutorServiceMetrics.monitor(registry, executorService, "custom.executor");
        return this.executorService;
    }

    @PreDestroy
    public void shutdownExecutor() {
        if (executorService != null) {
            executorService.shutdown(); // Initiates an orderly shutdown
            try {
                if (!executorService.awaitTermination(30, TimeUnit.SECONDS)) {
                    executorService.shutdownNow(); // Force shutdown if tasks don't finish
                }
            } catch (InterruptedException e) {
                executorService.shutdownNow();
                Thread.currentThread().interrupt(); // Restore interrupt status
            }
        }
    }
}
