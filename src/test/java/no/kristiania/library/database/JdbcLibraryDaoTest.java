package no.kristiania.library.database;

public class JdbcLibraryDaoTest extends AbstractLibraryDaoTest {

    public JdbcLibraryDaoTest() {
        super(new JdbcLibraryDao(InMemoryDataSource.createTestDataSource()));
    }
}
