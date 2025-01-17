package example.dataretention.shared.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "app.tables")
public class TableProperties {
    private List<Table> tables;

    @Data
    public static class Table {
        private String tableName;
        private String retentionPeriod;
        private String clause;
        private int group;
        private int subGroup;
    }
}
