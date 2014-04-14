many-to-many-unidirectional-jpa-postgres-example
================================================

Example application with unit tests using maven, Junit, CDI, JPA2 and CRUD operations using a postgres database with two Entities using unidirectional many to many relationship.

To import it with eclipse, go to this directory and run the command

mvn eclipse:eclipse

Then you can import this project into eclipse workspace.

To run the DAO tests, you have to create an empty database on postgres named "MANY_TO_MANY_UNIDIRECTIONAL_EXAMPLE".

Eclipselink will create the tables automatically while running the tests.

My local development postgres db has no password to access it, so if your postgres does have a password or the username is not postgres, edit the following properties on src/main/resources/META-INF/persistence.xml:

<property name="eclipselink.jdbc.url" value="jdbc:postgresql://localhost:5432/MANY_TO_MANY_UNIDIRECTIONAL_EXAMPLE" />

<property name="javax.persistence.jdbc.user" value="postgres" />
						
<property name="javax.persistence.jdbc.password" value="" />



