package example.dataretention.shared.config;

import example.dataretention.shared.domain.Table;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties("tables")
public record TableProperties(List<Table> tables) {
}
