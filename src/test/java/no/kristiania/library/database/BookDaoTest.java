package no.kristiania.library.database;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BookDaoTest {

    private BookDao dao;

    @BeforeEach
    void setUp() {
        var dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:bookDaoTest");
        dao = new BookDao(dataSource);
    }

    @Test
    void shouldRetrieveSavedBook() {
        var book = sampleBook();
        dao.save(book);

        assertThat(dao.retrieve(book.getId()))
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .isEqualTo(book)
                //.isNotSameAs(book)
        ;
    }

    private Book sampleBook() {
        Book book = new Book();
        book.setTitle("Sample title");
        book.setAuthor("Firstname Lastname");
        return book;
    }
}
