package no.kristiania;

import org.junit.jupiter.api.Test;

public class BookDaoTest {

    private BookDao bookDao;

    @Test
    void shouldListSavedBooks() {
        var book1 = sampleBook();
        var book2 = sampleBook();
        bookDao.save(book1);
        bookDao.save(book2);
        flush();
        assertThat(bookDao.listAll())
                .extracting(Book::getId)
                .contains(book1.getId(), book2.getId());
    }
}
