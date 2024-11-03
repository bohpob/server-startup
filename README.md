# Client-Server Application. School Project. Server

## Overview

This is a client-server application designed with a three-tier architecture, leveraging suitable Java technologies and libraries, specifically the Spring Framework. 
The application interacts with three entities from a relational database, performing all CRUD operations with a many-to-many relationship, which results in four tables in the relational database. 
It utilizes Object-Relational Mapping (ORM) with MySQL as the database.

## Server Side

The server-side of the application consists of the following layers:

- **Data Layer**: Handles all CRUD operations for the entities and manages the many-to-many relationships effectively. 
- **Application Logic Layer**: Provides a set of services that allow data layer operations, including additional queries and handling of complex business logic.
- **REST API**: A well-designed REST API exposes all operations from the application logic layer, enabling seamless interaction with the client. This includes CRUD operations for all entities and additional query functionalities.

The application is built using Gradle, and during the build process, automated tests are executed and evaluated to ensure code quality and functionality.

## Client Side
[Client Repository Link](https://github.com/bohpob/SpringShellDemoClient)

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/bohpob/SpringDemoServer.git
   ```
2. Navigate to the server directory and build the application:
   ```bash
   cd server
    ./gradlew build
   ```
3. Run the server:
   ```bash
   ./gradlew bootRun
   ```
