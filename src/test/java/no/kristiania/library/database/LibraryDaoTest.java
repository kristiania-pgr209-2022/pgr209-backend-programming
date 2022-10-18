package no.kristiania.library.database;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LibraryDaoTest {

    private final LibraryDao dao = new LibraryDao(InMemoryDataSource.createTestDataSource());

    @Test
    void shouldRetrieveSavedLibrary() {
        var library = SampleData.sampleLibrary();
        dao.save(library);
        assertThat(dao.retrieve(library.getId()))
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .isEqualTo(library);
    }
}
