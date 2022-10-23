package no.kristiania.library.database;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractPhysicalBookDaoTest {
    private final LibraryDao libraryDao;
    private final BookDao bookDao;
    private final PhysicalBookDao dao;

    public AbstractPhysicalBookDaoTest(PhysicalBookDao dao, BookDao bookDao, LibraryDao libraryDao) {
        this.libraryDao = libraryDao;
        this.bookDao = bookDao;
        this.dao = dao;
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
