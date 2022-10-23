package no.kristiania.library.database.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import no.kristiania.library.database.AbstractPhysicalBookDaoTest;
import no.kristiania.library.database.jdbc.JdbcBookDao;
import no.kristiania.library.database.jdbc.JdbcLibraryDao;
import no.kristiania.library.database.jdbc.JdbcPhysicalBookDao;
import no.kristiania.library.database.jdbc.TestDataSourceProvider;

import javax.sql.DataSource;

public class JpaPhysicalBookDaoTest extends AbstractPhysicalBookDaoTest {

    private JpaPhysicalBookDaoTest(@TestPersistenceManager("library") EntityManager entityManager) {
        super(new JpaPhysicalBookDao(entityManager), new JpaBookDao(entityManager), new JpaLibraryDao(entityManager));
    }
}
