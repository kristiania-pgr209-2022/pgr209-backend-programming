package no.kristiania.library.database.jdbc;

import no.kristiania.library.database.AbstractLibraryDaoTest;
import no.kristiania.library.database.InMemoryDataSource;
import no.kristiania.library.database.jdbc.JdbcLibraryDao;

import static org.assertj.core.api.Assertions.assertThat;

public class JdbcLibraryDaoTest extends AbstractLibraryDaoTest {

    public JdbcLibraryDaoTest() {
        super(new JdbcLibraryDao(InMemoryDataSource.createTestDataSource()));
    }
}
