package no.kristiania.library.database;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BookCopyDaoTest {
    private BookDao bookDao;
    private LibraryDao libraryDao;
    private BookCopyDao dao;

    private Library library;
    private Book firstBook;
    private Book secondBook;


    @Test
    @Disabled
    void shouldFindBooksByLibrary() {
        Library otherLibrary = null;

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
