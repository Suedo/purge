## **High-Level Requirement Specification: Multi-Database Table Purging Application**

---

### **1. Objective**

Develop a reusable Spring Boot application for dynamic and efficient purging of database tables across multiple databases. The application will allow teams to specify purge logic and details dynamically, while ensuring safety, flexibility, and scalability.

---

### **2. Functional Requirements**

#### **2.1 Dynamic Database Connectivity**

- The application should support connecting to multiple databases using provided connection parameters.
- Each team provides the connection details for their databases (e.g., hostname, port, username, password) and the tables to purge.

#### **2.2 Table-Specific Logic Integration**

- Teams will specify fine-grained details for each table, such as:
  - Partitioning details (e.g., column names, intervals).
  - Custom purge conditions (e.g., age of data).
- The application will dynamically integrate these details into SQL queries executed via JDBC.

#### **2.3 Distributed Locking**

- Implement distributed locking to prevent concurrent execution of purging tasks on the same table or database.
- Use the common ShedLock feature of Spring Boot for distributed locking to ensure only one instance of the application purges a specific table at a time. If ShedLock is not compatible, consider alternative distributed locking solutions, such as **Redis**, **ZooKeeper**, or **database-level locks**.

#### **2.4 Data Archival (Optional)**

- Provide an **opt-in** feature to archive data before deletion.
- Support integration with common backup solutions, such as:
  - Cloud storage (e.g., AWS S3, Azure Blob Storage, GCP Storage).
  - File system or on-premises storage solutions.

#### **2.5 Dry-Run Capability**

- Include a dry-run mode to preview the impact of purging:
  - Show the number of rows that would be deleted.
  - Highlight partitions or tables targeted for deletion.

#### **2.6 High Availability (HA)**

- Ensure the application supports deployment in HA mode without conflicts:
  - Use distributed locks to synchronize purging tasks.
  - Log and monitor tasks across instances.

---

### **3. Non-Functional Requirements**

#### **3.1 Scalability**

- Support concurrent purging for multiple databases and tables.
- Ensure the application can handle high throughput for large datasets.

#### **3.2 Security**

- Protect sensitive database credentials provided by teams.
- Use secure storage mechanisms (e.g., environment variables, secrets managers like AWS Secrets Manager or HashiCorp Vault).

#### **3.3 Logging and Monitoring**

- Provide detailed logs for:
  - Connected databases and tables.
  - Partitions and rows purged.
  - Errors encountered during purging.
- Integrate with monitoring tools (e.g., Prometheus, Grafana) to track purge performance.

#### **3.4 Configurability**

- Allow teams to:
  - Define table-specific purge conditions and partitioning details.
  - Configure archival options dynamically.
  - Schedule purge tasks (e.g., cron expressions).

---

### **4. Workflow**

#### **4.1 Initialization**

- Teams provide:
  - Database connection parameters.
  - Table-specific purge configurations (e.g., partition columns, conditions).
  - Archival preferences (if applicable).

#### **4.2 Execution**

1. Connect to the specified database using JDBC.
2. For each table:
   - Apply distributed locking to ensure no concurrent purging.
   - Execute dry-run if requested, logging statistics.
   - Archive data if the archival option is enabled.
   - Perform the purge by:
     - Querying partitions or rows to delete based on provided logic.
     - Executing `DROP PARTITION` or `DELETE` commands.
3. Log the results (rows deleted, errors, time taken).

#### **4.3 High Availability Mode**

- Ensure distributed locks prevent overlapping purges in HA deployments.
- Maintain a central state repository for tracking tasks (e.g., Redis or a database table).

---

### **5. Optional Features**

- **Notification Integration**: Notify teams of completed purges via email or messaging tools (e.g., Slack).
- **Retry Mechanism**: Retry failed purges with exponential backoff.
- **Metrics Export**: Export purge statistics for monitoring tools (e.g., Prometheus).

---

This specification ensures the application is flexible, robust, and reusable across teams and use cases, while maintaining operational safety and scalability.
