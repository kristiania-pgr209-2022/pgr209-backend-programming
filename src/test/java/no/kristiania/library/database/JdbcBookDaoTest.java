package no.kristiania.library.database;

public class JdbcBookDaoTest extends AbstractBookDaoTest {

    public JdbcBookDaoTest() {
        super(new JdbcBookDao(InMemoryDataSource.createTestDataSource()));
    }
}
