# Common Purge Platform

A common platform to delete (with optional archival) of data records past their retention period

Below is an overview of the architechture:

![alt text](Overview.png)

Legend:

- The blue region denotes the common framework
- The green region, denoting custom logic that can get invoked dynamically at runtime, based on config

## Onboarding

This section shows how config data is stored.

- We can have APIs that load these data in config tables
- Everytime new data is uploaded, it can trigger a validation event

## Framework Execution

Only when validation passes, the framework can run. Below is an overview:

![alt text](Flow.png)
