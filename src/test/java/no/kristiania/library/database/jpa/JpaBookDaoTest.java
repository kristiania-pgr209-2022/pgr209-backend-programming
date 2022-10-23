package no.kristiania.library.database.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import no.kristiania.library.database.AbstractBookDaoTest;
import no.kristiania.library.database.InMemoryDataSource;
import no.kristiania.library.database.jdbc.JdbcBookDao;

public class JpaBookDaoTest extends AbstractBookDaoTest {

    public JpaBookDaoTest(@TestPersistenceManager("library") EntityManager entityManager) {
        super(new JpaBookDao(entityManager));
    }
}
