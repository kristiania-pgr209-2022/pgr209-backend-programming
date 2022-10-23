package no.kristiania.library.database.jpa;

import jakarta.persistence.Persistence;
import no.kristiania.library.database.AbstractLibraryDaoTest;
import no.kristiania.library.database.InMemoryDataSource;
import no.kristiania.library.database.jdbc.JdbcLibraryDao;

public class JpaLibraryDaoTest extends AbstractLibraryDaoTest {

    public JpaLibraryDaoTest() {
        super(new JpaLibraryDao(Persistence.createEntityManagerFactory("library").createEntityManager()));
    }
}
