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
        var dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:libraryDaoTest;DB_CLOSE_DELAY=-1");

        var flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();

        dao = new LibraryDao(dataSource);
    }

    @Test
    void shouldRetrieveSavedLibrary() throws SQLException {
        var library = sampleLibrary();
        dao.save(library);
        assertThat(dao.retrieve(library.getId()))
                .usingRecursiveComparison()
                .isEqualTo(library);
    }

    private Library sampleLibrary() {
        var library = new Library();
        library.setName("Deichmanske");
        library.setAddress("Dronning Eufemias gate");
        return library;
    }
}
