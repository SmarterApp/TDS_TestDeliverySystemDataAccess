# Welcome to the TDSDLLDev Modules

The TDSDLLDev is group of shared modules which can be used by other projects to access and manipulate databases. TDSDLLDev contains business level logic of database reads and updates most commonly perfomed by stored procedures.


## License ##
This project is licensed under the [AIR Open Source License v1.0](http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf).

## Getting Involved ##
We would be happy to receive feedback on its capabilities, problems, or future enhancements:

* For general questions or discussions, please use the [Forum](http://forum.opentestsystem.org/viewforum.php?f=9).
* Use the **Issues** link to file bugs or enhancement requests.
* Feel free to **Fork** this project and develop your changes!

## Module Overview

### tds-dll-api

   tds-dll-api contains interfaces that can be implemented in other modules.

### tds-dll-common

  tds-dll-common contains implementation to retrieve and manipulate Student and Proctor data from Administration and Registration Tools (ART), formerly known as Test Registration (TR).

### tds-dll-integration-tests

   tds-dll-integration-tests contain implementation classes and configuration files used to do unit testing of database access functionality.

### tds-dll-mssql

   tds-dll-mssql contain MSSQL implementation of interfaces from tds-dll-api module. 

### tds-dll-mysql

  tds-dll-mysql contains MySql implementation of interfaces from tds-dll-api module.

### tds-dll-schemas

  tds-dll-schemas contains sql scripts to create databases and applicable tables, indexes, constraints, triggers, stored procedures on these databases: archive, configs, itembank, session, corestandards, permissions.



## Setup
In general, build the code and deploy the JAR file.


### Build Order

If building all components from scratch the following build order is needed:

* shared-db
* shared-tr-api


## Dependencies
TDSDLLDev has a number of direct dependencies that are necessary for it to function.  These dependencies are already built into the Maven POM files.

### Compile Time Dependencies
* shared-db
* shared-tr-api


### Test Dependencies
* junit