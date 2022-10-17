package no.kristiania.library.database;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BookDaoTest {

    private BookDao dao;

    @Test
    void shouldRetrieveSavedBook() {
        var book = sampleBook();
        dao.save(book);

        assertThat(dao.retrieve(book.getId()))
                .hasNoNullFieldsOrProperties()
                .isNotSameAs(book)
                .usingRecursiveComparison()
                .isEqualTo(book);
    }

    private Book sampleBook() {
        Book book = new Book();
        book.setTitle("Sample title");
        book.setAuthor("Firstname Lastname");
        return book;
    }
}
