package no.kristiania.library.database;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractLibraryDaoTest {
    private final LibraryDao dao;

    public AbstractLibraryDaoTest(LibraryDao dao) {
        this.dao = dao;
    }

    @Test
    void shouldRetrieveSavedLibrary() throws SQLException {
        var library = SampleData.sampleLibrary();
        dao.save(library);
        flush();
        assertThat(dao.retrieve(library.getId()))
                .hasNoNullFieldsOrPropertiesExcept("books")
                .usingRecursiveComparison()
                .isEqualTo(library);
    }

    protected void flush() {

    }
}
