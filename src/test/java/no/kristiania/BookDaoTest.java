package no.kristiania;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BookDaoTest {

    @Test
    void shouldRetrieveSavedBook() {
        var book = sampleBook();
        var dao = new BookDao();
        dao.save(book);
        flush();
        assertThat(dao.retrieve(book.getId()))
                .isNotSameAs(book)
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .isEqualTo(book);

    }

    private Book sampleBook() {
        return null;
    }

    private void flush() {

    }
}
