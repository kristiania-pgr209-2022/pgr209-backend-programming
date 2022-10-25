# Lecture 8: JDBC

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

# Lecture 9: JPA

* [ ] Converter til SQL Server instead of PostgreSQL
  * Configure connection properties
  * Install Docker
  * Run sql server on docker: `docker run --name pgr209-lecture -e "ACCEPT_EULA=Y" -e "MSSQL_SA_PASSWORD=JUNL3252aaa#sw" -p 1433:1433 -d mcr.microsoft.com/mssql/server:2022-latest`
  * URL: `jdbc:sqlserver://localhost:1433;encrypt=true; trustServerCertificate=true`
* [ ] Extract interfaces from DAO-classes => JdbcBookDao
* [ ] Extract abstract superclasses from test
* [ ] Implement DAO (and test) with JPA
   * Junit extensions for JPA

* Add LibraryServerTest and reimplement LibraryServer with BookEndpoint
* Integrate BookEndpoint with JpaBookDao
* Deploy to Azure
* 
