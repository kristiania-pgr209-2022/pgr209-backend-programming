package no.kristiania.library.database;

import jakarta.persistence.EntityManager;
import no.kristiania.library.database.jpa.JpaBookDao;

public class JpaBookDaoTest extends AbstractBookDaoTest {

    private final EntityManager entityManager;

    public JpaBookDaoTest(@TestEntityManager EntityManager entityManager) {
        super(new JpaBookDao(entityManager));
        this.entityManager = entityManager;
    }

    @Override
    protected void flush() {
        entityManager.flush();
        entityManager.clear();
    }
}
