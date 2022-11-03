package no.kristiania;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BookDaoTest {

    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        entityManager = Persistence
                .createEntityManagerFactory("library")
                .createEntityManager();
    }

    @Test
    void shouldRetrieveSavedBook() {
        var book = sampleBook();
        var dao = new BookDao(entityManager);
        dao.save(book);
        flush();
        assertThat(dao.retrieve(book.getId()))
                .isNotSameAs(book)
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .isEqualTo(book);

    }

    private Book sampleBook() {
        var book = new Book();
        book.setAuthorName("Johannes Brodwall");
        book.setTitle("The Code and the Coder");
        return book;
    }

    private void flush() {
        entityManager.flush();
    }
}
