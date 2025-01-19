package example.dataretention.restapi.config.properties;

import java.time.Duration;
import java.util.List;

public record Retry(int maxAttempts, Duration backoff, List<Class<? extends Throwable>> retryableExceptions) {
    public Retry {
        if (maxAttempts <= 0) throw new IllegalArgumentException("maxAttempts must be greater than 0");
    }
}