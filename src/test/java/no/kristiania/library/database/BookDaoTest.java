package no.kristiania.library.database;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class BookDaoTest {

    private BookDao dao;

    @BeforeEach
    void setUp() {
        dao = new BookDao(InmemoryDatabase.testDataSource());
    }

    @Test
    void shouldRetrieveSavedBook() throws SQLException {
        var book = SampleData.sampleBook();
        dao.save(book);

        assertThat(dao.retrieve(book.getId()))
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .isEqualTo(book)
                .isNotSameAs(book);
    }

    @Test
    void shouldReturnNullForMissingBook() throws SQLException {
        assertThat(dao.retrieve(-1L)).isNull();;
    }

    @Test
    void shouldFindBookByAuthor() throws SQLException {
        var matchingBook = SampleData.sampleBook();
        dao.save(matchingBook);
        var otherBookBySameAuthor = SampleData.sampleBook();
        otherBookBySameAuthor.setAuthor(matchingBook.getAuthor());
        dao.save(otherBookBySameAuthor);
        var bookByOtherAuthor = SampleData.sampleBook();
        bookByOtherAuthor.setAuthor("Other Author");
        dao.save(bookByOtherAuthor);

        assertThat(dao.findByAuthor(matchingBook.getAuthor()))
                .extracting(Book::getId)
                .contains(matchingBook.getId(), otherBookBySameAuthor.getId())
                .doesNotContain(bookByOtherAuthor.getId());
    }

}
