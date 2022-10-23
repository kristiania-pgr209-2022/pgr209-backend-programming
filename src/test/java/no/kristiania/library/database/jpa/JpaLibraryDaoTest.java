package no.kristiania.library.database.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import no.kristiania.library.database.AbstractLibraryDaoTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class JpaLibraryDaoTest extends AbstractLibraryDaoTest {

    private final EntityManager entityManager;

    public JpaLibraryDaoTest(@TestPersistenceManager EntityManager entityManager) {
        super(new JpaLibraryDao(entityManager));
        this.entityManager = entityManager;
    }

    @Override
    protected void flush() {
        entityManager.flush();
    }
}
