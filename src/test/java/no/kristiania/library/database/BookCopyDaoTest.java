package no.kristiania.library.database;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BookCopyDaoTest {
    private BookDao bookDao;
    private LibraryDao libraryDao;
    private BookCopyDao dao;

    private final Library library = SampleData.sampleLibrary();
    private final Book firstBook = SampleData.sampleBook();
    private final Book secondBook = SampleData.sampleBook();


    @Test
    @Disabled
    void shouldFindBooksByLibrary() {
        var otherLibrary = SampleData.sampleLibrary();

        dao.insert(library, firstBook);
        dao.insert(library, secondBook);
        dao.insert(otherLibrary, firstBook);

        assertThat(dao.findByLibrary(library.getId()))
                .extracting(Book::getId)
                .contains(firstBook.getId(), secondBook.getId());
        assertThat(dao.findByLibrary(otherLibrary.getId()))
                .extracting(Book::getId)
                .contains(firstBook.getId(), secondBook.getId());
    }
}
