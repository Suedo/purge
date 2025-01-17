package example.demo.shared.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
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
