package example.dataretention.restapi.config.properties;

import java.time.Period;

public record Deletion(Period interval) {
}