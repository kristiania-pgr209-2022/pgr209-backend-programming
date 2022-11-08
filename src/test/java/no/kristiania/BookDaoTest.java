package no.kristiania;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BookDaoTest {

    private BookDao bookDao;
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        entityManager = Persistence.createEntityManagerFactory("library")
                .createEntityManager();
        bookDao = new BookDao(entityManager);
    }

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

    private void flush() {
        entityManager.flush();
    }

    private Book sampleBook() {
        return new Book();
    }
}
