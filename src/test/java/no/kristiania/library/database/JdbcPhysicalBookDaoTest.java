package no.kristiania.library.database;

import no.kristiania.library.database.jdbc.JdbcBookDao;
import no.kristiania.library.database.jdbc.JdbcLibraryDao;
import no.kristiania.library.database.jdbc.JdbcPhysicalBookDao;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

public class JdbcPhysicalBookDaoTest extends AbstractPhysicalBookDaoTest {

    public JdbcPhysicalBookDaoTest(@TestDataSource DataSource dataSource) {
        super(new JdbcLibraryDao(dataSource), new JdbcBookDao(dataSource), new JdbcPhysicalBookDao(dataSource));
    }
}
