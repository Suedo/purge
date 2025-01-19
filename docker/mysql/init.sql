-- Create the fleet_management database if it doesn't exist
CREATE DATABASE IF NOT EXISTS fleet_management;

-- Use the fleet_management database
USE fleet_management;

-- Create schema for client-specific tables
CREATE TABLE JPMC_Employees (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE JPMC_Transport (
    id INT AUTO_INCREMENT PRIMARY KEY,
    vehicle_type VARCHAR(50),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE TCS_Employees (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE TCS_Transport (
    id INT AUTO_INCREMENT PRIMARY KEY,
    vehicle_type VARCHAR(50),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Insert initial data
INSERT INTO JPMC_Employees (name) VALUES ('John Doe'), ('Jane Smith');
INSERT INTO JPMC_Transport (vehicle_type) VALUES ('Bus'), ('Car');
INSERT INTO TCS_Employees (name) VALUES ('Alice'), ('Bob');
INSERT INTO TCS_Transport (vehicle_type) VALUES ('Truck'), ('Van');
