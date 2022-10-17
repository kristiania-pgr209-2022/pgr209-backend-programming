package no.kristiania.library.database;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class LibraryDaoTest {

    private LibraryDao dao;

    @BeforeEach
    void setUp() {
        dao = new LibraryDao(InmemoryDatabase.testDataSource());
    }

    @Test
    void shouldRetrieveSavedLibrary() throws SQLException {
        var library = SampleData.sampleLibrary();
        dao.save(library);
        assertThat(dao.retrieve(library.getId()))
                .usingRecursiveComparison()
                .isEqualTo(library);
    }

}
