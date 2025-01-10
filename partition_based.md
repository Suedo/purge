## **Wiki: Interval-Based Composite Partitioning and Purging Strategy**

---

### **1. Introduction**

Managing large volumes of transaction data requires efficient purging to maintain performance. This strategy uses **Interval-Based Composite Partitioning** to:

- Automatically create monthly partitions for new data.
- Enable quick and efficient purging of old data at the partition level.

We integrate the purging logic into a **Spring Boot application** using JDBC and a scheduler, ensuring:

- **Version-controlled logic** for maintainability.
- Flexibility to add features like **archiving data to the cloud** before purging.

---

### **2. Database Setup**

We partition the `transactions` table by `transaction_date` on a monthly basis and sub-partition it by `customer_id` for uniform distribution.

**SQL Schema**:

```sql
CREATE TABLE transactions (
    transaction_id NUMBER,
    transaction_date DATE NOT NULL,
    customer_id NUMBER,
    amount NUMBER
)
PARTITION BY RANGE (transaction_date)
INTERVAL (NUMTOYMINTERVAL(1, 'MONTH')) -- The `NUMTOYMINTERVAL` function converts the number `1` and the unit `'MONTH'` into a time interval, used here to define monthly partition boundaries dynamically.
SUBPARTITION BY HASH (customer_id) SUBPARTITIONS 4 (
    PARTITION p_start VALUES LESS THAN (TO_DATE('2023-01-01', 'YYYY-MM-DD'))
);
```

**Key Features**:

- **Primary Partitioning**: Monthly intervals for `transaction_date`.
- **Sub-partitioning**: Hashing on `customer_id` ensures even data distribution.
- **Local Indexes**: Enhance query performance by targeting specific partitions.

**Future Partition Naming**: New partitions created automatically by the interval partitioning mechanism are named using Oracle's system-generated format, such as `SYS_P123`. These names can be customized for clarity using the `ALTER TABLE ... RENAME PARTITION` command if required.

---

### **3. Identifying and Purging Old Partitions**

**Step 1: Query Old Partitions**
To identify partitions older than 5 months:

`high_value` represents the upper boundary of the range for each partition. For example, if a partition is defined for June 2023, its `high_value` would be the first day of the next interval, i.e., July 1, 2023. This boundary helps determine if the partition contains data older than the specified time frame.

```sql
SELECT
    partition_name,
    TO_DATE(high_value, 'SYYYY-MM-DD HH24:MI:SS') AS high_date
FROM
    user_tab_partitions
WHERE
    table_name = 'TRANSACTIONS'
    AND TO_DATE(high_value, 'SYYYY-MM-DD HH24:MI:SS') < ADD_MONTHS(SYSDATE, -5);
```

**Step 2: Drop Old Partitions**
To drop a specific partition:

```sql
ALTER TABLE transactions DROP PARTITION SYS_P123;
```

---

### **4. Automating with Spring Boot**

We use a **Spring Boot scheduler** to periodically identify and purge old partitions.

**Scheduler Code**:

```java
@Component
public class PartitionPurger {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Scheduled(cron = "0 0 1 1 * ?") // Runs at 1 AM on the first day of every month
    public void purgeOldPartitions() {
        // Query partitions older than 5 months
        String fetchPartitionsSql = """
            SELECT partition_name
            FROM user_tab_partitions
            WHERE table_name = 'TRANSACTIONS'
              AND TO_DATE(high_value, 'SYYYY-MM-DD HH24:MI:SS') < ADD_MONTHS(SYSDATE, -5)
        """;

        List<String> partitionsToDrop = jdbcTemplate.query(fetchPartitionsSql,
            (rs, rowNum) -> rs.getString("partition_name"));

        // Drop each partition
        for (String partition : partitionsToDrop) {
            String dropPartitionSql = "ALTER TABLE transactions DROP PARTITION " + partition;
            jdbcTemplate.execute(dropPartitionSql);
            System.out.println("Dropped partition: " + partition);
        }
    }
}
```

**How It Works**:

1. **Fetch Partitions**: Queries `USER_TAB_PARTITIONS` for partitions older than 5 months.
2. **Drop Partitions**: Executes `DROP PARTITION` commands for identified partitions.
3. **Logging**: Logs dropped partitions for auditing.

---

### **5. Best Practices and Summary**

- **Benefits of the Approach**:

  - **Efficient Purging**: Partition-level deletes are faster than row-level deletes.
  - **Automated Management**: Interval partitioning dynamically creates monthly partitions.
  - **Flexible Integration**: Centralized in a Spring Boot app, the logic is version-controlled and extendable for features like archiving.

- **Best Practices**:
  - Ensure the application user has sufficient database permissions.
  - Log and monitor the purging process.
  - Regularly gather partition statistics for optimal query performance:
    ```sql
    BEGIN
        DBMS_STATS.GATHER_TABLE_STATS(ownname => 'YOUR_SCHEMA', tabname => 'TRANSACTIONS');
    END;
    ```

By automating the purging process with a combination of **Interval Partitioning** and **Spring Boot scheduling**, this approach keeps the database performant while reducing manual intervention.
