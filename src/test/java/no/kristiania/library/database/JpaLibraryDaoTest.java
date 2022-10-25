package no.kristiania.library.database;

import jakarta.persistence.Persistence;

public class JpaLibraryDaoTest extends AbstractLibraryDaoTest {

    public JpaLibraryDaoTest() {
        super(new JpaLibraryDao(Persistence.createEntityManagerFactory("library")
                .createEntityManager()));
    }
}
