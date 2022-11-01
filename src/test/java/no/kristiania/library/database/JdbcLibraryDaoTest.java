package no.kristiania.library.database;

import no.kristiania.library.database.jdbc.JdbcLibraryDao;

public class JdbcLibraryDaoTest extends AbstractLibraryDaoTest {

    public JdbcLibraryDaoTest() {
        super(new JdbcLibraryDao(InMemoryDataSource.createTestDataSource()));
    }
}
