package no.kristiania.library.database;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

public class JdbcPhysicalBookDaoTest extends AbstractPhysicalBookDaoTest {

    public JdbcPhysicalBookDaoTest(@TestDataSource DataSource dataSource) {
        super(new JdbcLibraryDao(dataSource), new JdbcBookDao(dataSource), new JdbcPhysicalBookDao(dataSource));
    }
}
