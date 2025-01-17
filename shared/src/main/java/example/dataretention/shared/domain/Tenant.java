package example.dataretention.shared.domain;

public record Tenant(
        String tenantId,
        String tenantName,
        String cron,
        boolean purge
) {
}