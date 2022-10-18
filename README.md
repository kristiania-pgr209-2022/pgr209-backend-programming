

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
* [ ] BookDaoTest shouldFindByAuthorName
* [ ] Repeat: LibraryDaoTest
* [ ] Foreign keys and joins: BookCopyDaoTest

* [ ] The same but with JPA (migrate the parts over to JPA)
