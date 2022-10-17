package no.kristiania.library.database;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class BookCopyDaoTest {

    private final DataSource dataSource = InmemoryDatabase.testDataSource();
    private final BookDao bookDao = new BookDao(dataSource);
    private final LibraryDao libraryDao = new LibraryDao(dataSource);
    private final BookCopyDao dao = new BookCopyDao(dataSource);

    private final Library library = SampleData.sampleLibrary();
    private final Book firstBook = SampleData.sampleBook();
    private final Book secondBook = SampleData.sampleBook();


    @Test
    void shouldFindBooksByLibrary() throws SQLException {
        bookDao.save(firstBook);
        bookDao.save(secondBook);

        var otherLibrary = SampleData.sampleLibrary();
        libraryDao.save(library);
        libraryDao.save(otherLibrary);

        dao.insert(library, firstBook);
        dao.insert(library, secondBook);
        dao.insert(otherLibrary, firstBook);

        assertThat(dao.findByLibrary(library.getId()))
                .extracting(Book::getId)
                .contains(firstBook.getId(), secondBook.getId());
        assertThat(dao.findByLibrary(otherLibrary.getId()))
                .extracting(Book::getId)
                .contains(firstBook.getId())
                .doesNotContain(secondBook.getId());
    }
}
