package no.kristiania.library.database;

import no.kristiania.library.database.jdbc.JdbcBookDao;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractBookDaoTest {
    private final BookDao dao;

    public AbstractBookDaoTest(BookDao jdbcBookDao) {
        this.dao = jdbcBookDao;
    }

    @Test
    void shouldRetrieveSavedBook() throws SQLException {
        var book = SampleData.sampleBook();
        dao.save(book);
        flush();
        assertThat(dao.retrieve(book.getId()))
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .isEqualTo(book)
                .isNotSameAs(book)
        ;
    }

    @Test
    void shouldFindBooksByAuthorName() throws SQLException {
        var book = SampleData.sampleBook();
        var bookWithSameAuthor = SampleData.sampleBook();
        bookWithSameAuthor.setAuthorName(book.getAuthorName());
        var bookWithOtherAuthor = SampleData.sampleBook();
        bookWithOtherAuthor.setAuthorName("Other Author");

        dao.save(book);
        dao.save(bookWithSameAuthor);
        dao.save(bookWithOtherAuthor);
        flush();

        assertThat(dao.findByAuthorName(book.getAuthorName()))
                .extracting(Book::getId)
                .contains(book.getId(), bookWithSameAuthor.getId())
                .doesNotContain(bookWithOtherAuthor.getId());
    }

    @Test
    void shouldRetrieveNullForMissingBook() throws SQLException {
        assertThat(dao.retrieve(-1)).isNull();
    }


    protected void flush() {

    }
}
