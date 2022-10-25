package no.kristiania.library.database;

import jakarta.persistence.EntityManager;

public class JpaPhysicalBookDaoTest extends AbstractPhysicalBookDaoTest {

    private final EntityManager entityManager;

    public JpaPhysicalBookDaoTest(@TestEntityManager EntityManager entityManager) {
        super(new JpaLibraryDao(entityManager), new JpaBookDao(entityManager), new JpaPhysicalBookDao(entityManager));
        this.entityManager = entityManager;
    }

    @Override
    protected void flush() {
        entityManager.flush();
        entityManager.clear();
    }
}
