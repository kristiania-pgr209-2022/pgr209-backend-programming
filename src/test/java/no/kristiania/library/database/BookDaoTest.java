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
        var dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:bookDaoTest;DB_CLOSE_DELAY=-1");

        var flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();

        dao = new BookDao(dataSource);
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
