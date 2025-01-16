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
  - **Custom SQL Where Clause**: Define additional filters to target specific rows for purging.
  - **Timestamp Column and Retention Period**: Provide a timestamp column to determine the age of rows and define how much historical data to retain (e.g., keep 1 month of data).
  - **Primary Key Columns**: Provide the primary key column(s) for identifying rows uniquely.
- The application will dynamically integrate these details into SQL queries executed via JDBC.

#### **2.3 Core Purging Logic**

- **Determine Rows to Purge**:

  1. Fetch rows that meet the conditions specified in both the user-defined `where clause` and the timestamp-based retention logic.
  2. Extract a list of primary keys for the rows identified for purging.

- **Archive Data**:

  - Use the extracted primary keys to retrieve the full row data.
  - Archive the rows to a backup layer (cloud storage or on-premises solution).

- **Delete Rows**:
  - Perform deletion row by row using the extracted primary keys to ensure precision and prevent accidental data loss.

#### **2.4 Distributed Locking**

- Use the common ShedLock feature of Spring Boot for distributed locking to ensure only one instance of the application purges a specific table at a time.
- If ShedLock is not compatible, consider alternative distributed locking solutions, such as **Redis**, **ZooKeeper**, or **database-level locks**.

#### **2.5 Data Archival (Optional)**

- Provide an **opt-in** feature to archive data before deletion.
- Support integration with common backup solutions, such as:
  - Cloud storage (e.g., AWS S3, Azure Blob Storage, GCP Storage).
  - File system or on-premises storage solutions.

#### **2.6 Dry-Run Capability**

- Include a dry-run mode to preview the impact of purging:
  - Show the number of rows that would be deleted.
  - Highlight rows or partitions targeted for deletion.

#### **2.7 High Availability (HA)**

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
  - Rows purged and archived.
  - Errors encountered during purging.
- Integrate with monitoring tools (e.g., Prometheus, Grafana) to track purge performance.

#### **3.4 Configurability**

- Allow teams to:
  - Define table-specific purge conditions and timestamp logic.
  - Configure archival options dynamically.
  - Schedule purge tasks (e.g., cron expressions).

---

### **4. Workflow**

#### **4.1 Initialization**

- Teams provide:
  - Database connection parameters.
  - Table-specific purge configurations (e.g., custom SQL where clause, timestamp column, retention period).
  - Archival preferences (if applicable).

#### **4.2 Execution**

1. Connect to the specified database using JDBC.
2. For each table:
   - Apply distributed locking to ensure no concurrent purging.
   - Execute dry-run if requested, logging statistics.
   - Determine rows to purge based on the custom SQL where clause and timestamp logic.
   - Extract primary keys of the rows to be purged.
   - Archive data using the primary keys (if archival is enabled).
   - Delete rows individually using the primary keys.
3. Log the results (rows deleted, rows archived, errors, time taken).

#### **4.3 High Availability Mode**

- Ensure distributed locks prevent overlapping purges in HA deployments.
- Maintain a central state repository for tracking tasks (e.g., Redis or a database table).

---

### **5. Optional Features**

- **Notification Integration**: Notify teams of completed purges via email or messaging tools (e.g., Slack).
- **Retry Mechanism**: Retry failed purges with exponential backoff.
- **Metrics Export**: Export purge statistics for monitoring tools (e.g., Prometheus).

---

This updated specification modifies the purging logic to dynamically calculate rows to delete based on user-defined SQL conditions and timestamp retention logic, while retaining the flexibility, robustness, and reusability of the original design.
