package no.kristiania.library.database;

import org.junit.jupiter.api.Test;

public class LibraryDaoTest {

    private final LibraryDao dao = new LibraryDao(InMemoryDataSource.createTestDataSource());

    @Test
    void shouldRetrieveSavedLibrary() {
        var library = SampleData.sampleLibrary();
        dao.save(library);
        assertThat(dao.retrieve(library.getId()))
                .hasNoNullPropertiesOrFields()
                .usingRecursiveComparions()
                .isEqualTo(library);
    }
}
