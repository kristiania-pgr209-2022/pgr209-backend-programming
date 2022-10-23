package no.kristiania.library.database.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import no.kristiania.library.database.AbstractLibraryDaoTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class JpaLibraryDaoTest extends AbstractLibraryDaoTest {

    private static EntityManager entityManager = Persistence.createEntityManagerFactory("library").createEntityManager();

    public JpaLibraryDaoTest() {
        super(new JpaLibraryDao(entityManager));
    }

    @BeforeEach
    void setUp() {
        entityManager.getTransaction().begin();
    }

    @AfterEach
    void tearDown() {
        entityManager.getTransaction().rollback();
    }

    @Override
    protected void flush() {
        entityManager.flush();
    }
}
