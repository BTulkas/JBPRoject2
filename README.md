# John Bryce Coupon Project

## About
This is the backend code of the final project for the John Bryce Fullstack Java course. The Coupon Project is a RESTful coupon management CRUD application written with Spring Boot that has three possible end-users: an admin user for managing companies and customers, a company user for managing coupons and finally a customer user for purchasing coupons.
While this repository contains the frontend static files required for a complete run, in order to view the Angular frontend source code please refer to the [JBProject Angular](https://github.com/BTulkas/JBProjectAngular) repository.

## Overview

### Technologies
The Coupon Project uses Java 1.8 for access to modern features while maintaining some backwards compatibility and Spring Boot for quick and easy implementation of dependencies.
Dependencies include JPA with Hibernate ORM to define and manage database interactions and Spring-Web for RESTful MVC conventions.
The database of choice is MySQL for convenient development testing with the Workbench application.

### Installation and Launch
To launch this application on your machine make sure you have installed:
* JDK 13
* MySQL 8
(Note: backwards compatibility not tested against newer versions)

Other dependencies should install automatically from pom.xml instructions in most IDEs. Alternatively you may search for- and install them manually from [MVNRepository](https://mvnrepository.com/).

Next, go to `src/main/resources/application.properties` and change `spring.datasource.username=` and `spring.datasource.password=` to the username and password of your MySQL server.
Optionally you may also wish to change the DB name by replacing `spring.datasource.url=` to `jdbc:mysql://localhost:3306/`+`YourDBNameHere`+`?serverTimezone=UTC&createDatabaseIfNotExist=true`.

Finally, simply run the file `src/main/java/com.example/JBPRojest2/JbProject2Application.java` and navigate to `localhost:8080` in your browser.
Optionally, you may un-comment the first try-catch statement before the first run to fill the database with randomly generated data.


### Models\Beans
The primary beans (models) are:
* Company - with a OneToMany relationship to coupons it created.
* Coupon - with a ManyToMany relationship to customers who purchased it.
* Customer - with a ManyToMany relationship to coupons they purchased (mapped by Coupon).
* CategoryType - an Enum containing coupon categories.

The Coupon-Customer relationship is defined in the Coupon bean for convenience in CRUD operations, namely deletes.

The Admin user is hard coded with no DB representation.

##### Important!
As of Spring Boot 2.2.5 the Session bean is required for the proper functionality of the token-sessions system. Attempting to bypass this bean by working directly with a fresh HashMap will result in a bug that fills the HashMap with junk data and breaks functionality.