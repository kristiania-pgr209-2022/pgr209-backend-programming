package no.kristiania.library.database;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BookDaoTest {

    private BookDao dao = new BookDao();

    @Test
    void shouldRetrieveSavedBook() {
        var book = sampleBook();
        dao.save(book);
        assertThat(dao.retrieve(book.getId()))
                .usingRecursiveComparison()
                .isEqualTo(book);
    }

    private Book sampleBook() {
        return new Book();
    }
}
