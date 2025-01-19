package example.dataretention.restapi.config.properties;

import java.time.Duration;

public record Schedule(Duration rate) {
}