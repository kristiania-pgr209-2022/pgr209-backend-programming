package no.kristiania.library.database.jpa;

import no.kristiania.library.database.AbstractLibraryDaoTest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

public class JpaLibraryDaoTest extends AbstractLibraryDaoTest {

    public JpaLibraryDaoTest() {
        super(new JpaLibraryDao(createEntityManager()));
    }

    private static EntityManager createEntityManager() {
        var entityManagerFactory = Persistence
                .createEntityManagerFactory("library");
        return entityManagerFactory.createEntityManager();
    }
}
