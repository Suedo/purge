-- Create the fleet_management database if it doesn't exist
CREATE DATABASE IF NOT EXISTS fleet_management;

-- Use the fleet_management database
USE fleet_management;


CREATE TABLE INFOSYS_FleetData (
    request_id BIGINT AUTO_INCREMENT PRIMARY KEY, -- Unique identifier for each fleet request
    request_type VARCHAR(50) NOT NULL,           -- Type of request (e.g., Maintenance, Fueling, Assignment)
    vehicle_type VARCHAR(50) NOT NULL,           -- Type of vehicle involved (e.g., Bus, Car, Truck)
    reservation_date DATE,                       -- Date of reservation, if applicable
    pickup_location VARCHAR(255),                -- Starting location for the request
    drop_location VARCHAR(255),                  -- Destination location for the request
    status ENUM('Pending', 'In Progress', 'Completed', 'Cancelled') DEFAULT 'Pending', -- Status of the request
    description TEXT,                            -- Detailed description of the fleet request
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP, -- Timestamp when the request was created
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- Timestamp of the last update
);

CREATE TABLE TCS_FleetData (
    request_id BIGINT AUTO_INCREMENT PRIMARY KEY, -- Unique identifier for each fleet request
    request_type VARCHAR(50) NOT NULL,           -- Type of request (e.g., Maintenance, Fueling, Assignment)
    vehicle_type VARCHAR(50) NOT NULL,           -- Type of vehicle involved (e.g., Bus, Car, Truck)
    reservation_date DATE,                       -- Date of reservation, if applicable
    pickup_location VARCHAR(255),                -- Starting location for the request
    drop_location VARCHAR(255),                  -- Destination location for the request
    status ENUM('Pending', 'In Progress', 'Completed', 'Cancelled') DEFAULT 'Pending', -- Status of the request
    description TEXT,                            -- Detailed description of the fleet request
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP, -- Timestamp when the request was created
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- Timestamp of the last update
);

CREATE TABLE INFOSYS_EmployeeRequests (
    request_id BIGINT AUTO_INCREMENT PRIMARY KEY, -- Unique identifier for each employee request
    employee_name VARCHAR(100) NOT NULL,          -- Name of the employee making the request
    request_type VARCHAR(50) NOT NULL,            -- Type of request (e.g., Booking, Cancellation, Rescheduling)
    vehicle_type VARCHAR(50) NOT NULL,            -- Type of vehicle requested (e.g., Car, Van, Bus)
    reservation_date DATE NOT NULL,               -- Date of reservation
    pickup_location VARCHAR(255) NOT NULL,        -- Starting location for the request
    drop_location VARCHAR(255) NOT NULL,          -- Destination location for the request
    status ENUM('Pending', 'Approved', 'Rejected', 'Cancelled') DEFAULT 'Pending', -- Status of the request
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP, -- Timestamp when the request was created
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- Timestamp of the last update
);

CREATE TABLE TCS_EmployeeRequests (
    request_id BIGINT AUTO_INCREMENT PRIMARY KEY, -- Unique identifier for each employee request
    employee_name VARCHAR(100) NOT NULL,          -- Name of the employee making the request
    request_type VARCHAR(50) NOT NULL,            -- Type of request (e.g., Booking, Cancellation, Rescheduling)
    vehicle_type VARCHAR(50) NOT NULL,            -- Type of vehicle requested (e.g., Car, Van, Bus)
    reservation_date DATE NOT NULL,               -- Date of reservation
    pickup_location VARCHAR(255) NOT NULL,        -- Starting location for the request
    drop_location VARCHAR(255) NOT NULL,          -- Destination location for the request
    status ENUM('Pending', 'Approved', 'Rejected', 'Cancelled') DEFAULT 'Pending', -- Status of the request
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP, -- Timestamp when the request was created
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- Timestamp of the last update
);



--
-- Insert sample data
--
INSERT INTO INFOSYS_FleetData (request_type, vehicle_type, reservation_date, pickup_location, drop_location, status, description)
VALUES 
('Maintenance', 'Car', '2024-01-10', 'Bangalore', 'Hyderabad', 'Completed', 'Routine checkup'),
('Fueling', 'Bus', '2024-01-11', 'Mumbai', 'Pune', 'In Progress', 'Fueling during transit'),
('Assignment', 'Truck', '2024-01-12', 'Delhi', 'Jaipur', 'Pending', 'Assigned for goods transport'),
('Maintenance', 'Van', '2024-01-09', 'Chennai', 'Bangalore', 'Completed', 'Oil change'),
('Fueling', 'Car', '2024-01-08', 'Kolkata', 'Patna', 'Cancelled', 'Fueling request cancelled'),
('Assignment', 'Car', '2024-01-13', 'Goa', 'Mumbai', 'In Progress', 'Assigned to employee'),
('Maintenance', 'Bus', '2024-01-07', 'Pune', 'Nagpur', 'Pending', 'Scheduled maintenance'),
('Fueling', 'Truck', '2024-01-06', 'Hyderabad', 'Chennai', 'Completed', 'Fueling done at station'),
('Assignment', 'Bus', '2024-01-05', 'Jaipur', 'Udaipur', 'Cancelled', 'Assignment cancelled by admin'),
('Maintenance', 'Car', '2024-01-04', 'Ahmedabad', 'Surat', 'Completed', 'Routine servicing');

INSERT INTO TCS_FleetData (request_type, vehicle_type, reservation_date, pickup_location, drop_location, status, description)
VALUES 
('Maintenance', 'Car', '2024-01-10', 'Bangalore', 'Hyderabad', 'Completed', 'Routine checkup'),
('Fueling', 'Bus', '2024-01-11', 'Mumbai', 'Pune', 'In Progress', 'Fueling during transit'),
('Assignment', 'Truck', '2024-01-12', 'Delhi', 'Jaipur', 'Pending', 'Assigned for goods transport'),
('Maintenance', 'Van', '2024-01-09', 'Chennai', 'Bangalore', 'Completed', 'Oil change'),
('Fueling', 'Car', '2024-01-08', 'Kolkata', 'Patna', 'Cancelled', 'Fueling request cancelled'),
('Assignment', 'Car', '2024-01-13', 'Goa', 'Mumbai', 'In Progress', 'Assigned to employee'),
('Maintenance', 'Bus', '2024-01-07', 'Pune', 'Nagpur', 'Pending', 'Scheduled maintenance'),
('Fueling', 'Truck', '2024-01-06', 'Hyderabad', 'Chennai', 'Completed', 'Fueling done at station'),
('Assignment', 'Bus', '2024-01-05', 'Jaipur', 'Udaipur', 'Cancelled', 'Assignment cancelled by admin'),
('Maintenance', 'Car', '2024-01-04', 'Ahmedabad', 'Surat', 'Completed', 'Routine servicing');

INSERT INTO INFOSYS_EmployeeRequests (employee_name, request_type, vehicle_type, reservation_date, pickup_location, drop_location, status)
VALUES 
('John Doe', 'Booking', 'Car', '2024-01-15', 'Chennai', 'Kolkata', 'Approved'),
('Jane Smith', 'Cancellation', 'Bus', '2024-01-14', 'Mumbai', 'Pune', 'Rejected'),
('Alice Johnson', 'Rescheduling', 'Van', '2024-01-13', 'Delhi', 'Jaipur', 'Pending'),
('Bob Brown', 'Booking', 'Truck', '2024-01-12', 'Bangalore', 'Hyderabad', 'Approved'),
('Charlie White', 'Cancellation', 'Car', '2024-01-11', 'Hyderabad', 'Chennai', 'Cancelled'),
('Eve Black', 'Booking', 'Bus', '2024-01-10', 'Pune', 'Nagpur', 'Pending'),
('Tom Green', 'Rescheduling', 'Van', '2024-01-09', 'Jaipur', 'Udaipur', 'Approved'),
('Lucy Blue', 'Booking', 'Truck', '2024-01-08', 'Ahmedabad', 'Surat', 'Rejected'),
('Jack Red', 'Cancellation', 'Car', '2024-01-07', 'Goa', 'Mumbai', 'Cancelled'),
('Sophia Grey', 'Rescheduling', 'Bus', '2024-01-06', 'Chennai', 'Patna', 'Approved');

INSERT INTO TCS_EmployeeRequests (employee_name, request_type, vehicle_type, reservation_date, pickup_location, drop_location, status)
VALUES 
('John Doe', 'Booking', 'Car', '2024-01-15', 'Chennai', 'Kolkata', 'Approved'),
('Jane Smith', 'Cancellation', 'Bus', '2024-01-14', 'Mumbai', 'Pune', 'Rejected'),
('Alice Johnson', 'Rescheduling', 'Van', '2024-01-13', 'Delhi', 'Jaipur', 'Pending'),
('Bob Brown', 'Booking', 'Truck', '2024-01-12', 'Bangalore', 'Hyderabad', 'Approved'),
('Charlie White', 'Cancellation', 'Car', '2024-01-11', 'Hyderabad', 'Chennai', 'Cancelled'),
('Eve Black', 'Booking', 'Bus', '2024-01-10', 'Pune', 'Nagpur', 'Pending'),
('Tom Green', 'Rescheduling', 'Van', '2024-01-09', 'Jaipur', 'Udaipur', 'Approved'),
('Lucy Blue', 'Booking', 'Truck', '2024-01-08', 'Ahmedabad', 'Surat', 'Rejected'),
('Jack Red', 'Cancellation', 'Car', '2024-01-07', 'Goa', 'Mumbai', 'Cancelled'),
('Sophia Grey', 'Rescheduling', 'Bus', '2024-01-06', 'Chennai', 'Patna', 'Approved');
