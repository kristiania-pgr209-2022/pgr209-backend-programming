package no.kristiania.library.database.jdbc;

import no.kristiania.library.database.AbstractLibraryDaoTest;
import no.kristiania.library.database.InMemoryDataSource;

public class JdbcLibraryDaoTest extends AbstractLibraryDaoTest {

    public JdbcLibraryDaoTest() {
        super(new JdbcLibraryDao(InMemoryDataSource.createTestDataSource()));
    }
}
