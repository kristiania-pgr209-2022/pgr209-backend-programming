package no.kristiania.library.database;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PhysicalBookDaoTest {

    private final PhysicalBookDao dao = new PhysicalBookDao(InMemoryDataSource.createTestDataSource());

    @Test
    void shouldListBooksByLibrary() {
        var firstBook = SampleData.sampleBook();
        var secondBook = SampleData.sampleBook();

        var firstLibrary = SampleData.sampleLibrary();
        var secondLibrary = SampleData.sampleLibrary();

        dao.insert(firstLibrary, firstBook);
        dao.insert(firstLibrary, secondBook);
        dao.insert(secondLibrary, firstBook);

        assertThat(dao.findByLibrary(firstLibrary.getId()))
                .extracting(Book::getId)
                .contains(firstBook.getId(), secondBook.getId());
        assertThat(dao.findByLibrary(secondLibrary.getId()))
                .extracting(Book::getId)
                .contains(firstBook.getId())
                .doesNotContain(secondBook.getId())
        ;


    }
}
