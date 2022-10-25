package no.kristiania.library.database;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractPhysicalBookDaoTest {
    private final LibraryDao libraryDao;
    private final BookDao bookDao;
    private final PhysicalBookDao physicalBookDao;

    public AbstractPhysicalBookDaoTest(LibraryDao libraryDao, BookDao bookDao, PhysicalBookDao physicalBookDao) {
        this.libraryDao = libraryDao;
        this.bookDao = bookDao;
        this.physicalBookDao = physicalBookDao;
    }

    @Test
    void shouldListBooksByLibrary() throws SQLException {
        var firstBook = SampleData.sampleBook();
        var secondBook = SampleData.sampleBook();
        bookDao.save(firstBook);
        bookDao.save(secondBook);

        var firstLibrary = SampleData.sampleLibrary();
        var secondLibrary = SampleData.sampleLibrary();
        libraryDao.save(firstLibrary);
        libraryDao.save(secondLibrary);

        physicalBookDao.insert(firstLibrary, firstBook);
        physicalBookDao.insert(firstLibrary, secondBook);
        physicalBookDao.insert(secondLibrary, firstBook);
        flush();

        assertThat(physicalBookDao.findByLibrary(firstLibrary.getId()))
                .extracting(Book::getId)
                .contains(firstBook.getId(), secondBook.getId());
        assertThat(physicalBookDao.findByLibrary(secondLibrary.getId()))
                .extracting(Book::getId)
                .contains(firstBook.getId())
                .doesNotContain(secondBook.getId())
        ;


    }

    protected void flush() {

    }
}
