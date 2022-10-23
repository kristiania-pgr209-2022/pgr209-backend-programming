package no.kristiania.library.database.jdbc;

import no.kristiania.library.database.AbstractBookDaoTest;
import no.kristiania.library.database.InMemoryDataSource;

public class JdbcBookDaoTest extends AbstractBookDaoTest {

    public JdbcBookDaoTest() {
        super(new JdbcBookDao(InMemoryDataSource.createTestDataSource()));
    }
}
