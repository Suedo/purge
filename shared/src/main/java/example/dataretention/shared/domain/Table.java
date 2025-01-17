package example.dataretention.shared.domain;

public record Table(
        String tableName,
        String retentionPeriod,
        String clause,
        int group,
        int subGroup
) {
}