package no.kristiania.library.database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class JpaLibraryDaoTest extends AbstractLibraryDaoTest {

    private static EntityManager entityManager = Persistence.createEntityManagerFactory("library")
            .createEntityManager();

    public JpaLibraryDaoTest() {
        super(new JpaLibraryDao(entityManager));
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
