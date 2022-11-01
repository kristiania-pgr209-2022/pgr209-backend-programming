package no.kristiania.library.database;

import no.kristiania.library.database.jdbc.JdbcBookDao;

public class JdbcBookDaoTest extends AbstractBookDaoTest {

    public JdbcBookDaoTest() {
        super(new JdbcBookDao(InMemoryDataSource.createTestDataSource()));
    }
}
