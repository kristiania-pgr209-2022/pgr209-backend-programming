package no.kristiania.library.database;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractBookDaoTest {
    private final BookDao dao;

    protected AbstractBookDaoTest(BookDao dao) {
        this.dao = dao;
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
    void shouldFindAllBooks() throws SQLException {
        var book1 = SampleData.sampleBook();
        var book2 = SampleData.sampleBook();

        dao.save(book1);
        dao.save(book2);
        flush();

        assertThat(dao.listAll())
                .extracting(Book::getId)
                .contains(book1.getId(), book2.getId());
    }


    @Test
    void shouldRetrieveNullForMissingBook() throws SQLException {
        assertThat(dao.retrieve(-1)).isNull();
    }

    protected void flush() {
    }
}
