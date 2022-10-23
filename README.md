# Lecture 8

1. DAO test
   1. DAO Data Access Object (DAO ~= Repository)
   2. All properties on saved object should be retrieved
   3. CRUD (Create Retrieve Update Delete)
   4. findByXXX
   5. Finds should include matching objects and exclude non-matching objects

* [x] Maven project
* [x] BookDaoTest shouldRetrieveSavedBook
* [x] H2 in-memory database
* [x] Flyway for database migrations
* [x] Store data in a real database - PostgreSQL
* [x] BookDaoTest shouldFindByAuthorName
* [x] Repeat: LibraryDaoTest
* [ ] Foreign keys and joins: BookCopyDaoTest

# Lecture 9: The same but with JPA (migrate the parts over to JPA)

Disclaimer: JPA destroys much of the work we have been relying on so far. Don't be disappointed

* [ ] Install SQL server
  * Install Docker from https://docs.docker.com/get-docker/
  * Run with Docker: `docker run --name pgr209-sqlserver -e ACCEPT_EULA=Y -e SA_PASSWORD=... -p 1433:1433 -d mcr.microsoft.com/mssql/server:2022-latest`
  * Use `docker logs pgr209-sqlserver` to see if the server started successfully
  * Use `docker stop pgr209-sqlserver` to stop the server
  * Use `docker start pgr209-sqlserver` to restart the server
* [ ] Migrate to SQL server
  * [ ] Use a portable data source
  * [ ] Add SQL data source
  * [ ] Update Demo.java and migrations
* [ ] Extract interfaces from DTOs
* [ ] Move JDBC implementation to separate package
* [ ] Create JpaLibraryDao
* [ ] Create JpaBookDao
* [ ] Create JpaPhysicalBookDao
* [ ] Library -> PhyscialBook -> Book foreign keys
