# data-storage-service
Binds to employeeQueue with RMQ consumer, listens to all messages, and consumes them. Once consumed, it decrypts data with password string derived AES secret key and deserialize the protobuf to the employee. This employee data is now serialized to XML or CSV based on RMQ headers consumed and saves in files named with Employee names. <employee.xml or employee.csv> in Present Working Directory.

## Endpoints:
GET http://localhost:8081/data-storage/employee/read/{{employee_name}}?fileType=XML/CSV

This endpoint will find the file with employee in the requested format and if found, it'll be serialized to protobuf before responding.

## Pre-requisite:
RMQ must be up and running at below mentioned port.
http://localhost:15672

## Steps to run in local:
1. git clone git@github.com:RakshithVP/data-storage-service.git - Clone this repo.
2. mvn clean install - to generate jar files in target repository.
3. java -jar dataStorageService-0.0.1-SNAPSHOT.jar - run the spring boot application.
