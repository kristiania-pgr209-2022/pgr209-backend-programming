package no.kristiania.library.database;

import no.kristiania.library.database.jdbc.JdbcBookDao;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class PhysicalBookDaoTest {

    private final DataSource dataSource = InMemoryDataSource.createTestDataSource();
    private final LibraryDao libraryDao = new LibraryDao(dataSource);
    private final BookDao bookDao = new JdbcBookDao(dataSource);
    private final PhysicalBookDao dao = new PhysicalBookDao(dataSource);

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
