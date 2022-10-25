package no.kristiania.library.database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

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

    @BeforeEach
    void setUp() {
        entityManager.getTransaction().begin();
    }

    @AfterEach
    void tearDown() {
        entityManager.getTransaction().rollback();
    }
}
