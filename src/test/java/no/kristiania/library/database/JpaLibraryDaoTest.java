package no.kristiania.library.database;

import jakarta.persistence.EntityManager;

public class JpaLibraryDaoTest extends AbstractLibraryDaoTest {

    private final EntityManager entityManager;

    public JpaLibraryDaoTest(@TestEntityManager EntityManager entityManager) {
        super(new JpaLibraryDao(entityManager));
        this.entityManager = entityManager;
    }

    @Override
    protected void flush() {
        entityManager.flush();
    }
}
