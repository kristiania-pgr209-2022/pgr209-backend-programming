package no.kristiania.library.database;

import org.junit.jupiter.api.Test;

public class BookDaoTest {
    @Test
    void shouldRetrieveSavedBook() {
        var book = sampleBook();
        dao.save(book);
        assertThat(dao.retrieve(book.getId()))
                .usingRecursiveComparision()
                .isEqualTo(book);
    }
}
