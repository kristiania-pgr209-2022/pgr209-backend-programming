package no.kristiania.library.database.jdbc;

import no.kristiania.library.database.AbstractPhysicalBookDaoTest;
import no.kristiania.library.database.jdbc.JdbcBookDao;
import no.kristiania.library.database.jdbc.JdbcLibraryDao;
import no.kristiania.library.database.jdbc.JdbcPhysicalBookDao;
import no.kristiania.library.database.jdbc.TestDataSourceProvider;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

public class JdbcPhysicalBookDaoTest extends AbstractPhysicalBookDaoTest {

    private JdbcPhysicalBookDaoTest(@TestDataSourceProvider DataSource dataSource) {
        super(new JdbcPhysicalBookDao(dataSource), new JdbcBookDao(dataSource), new JdbcLibraryDao(dataSource));
    }
}
