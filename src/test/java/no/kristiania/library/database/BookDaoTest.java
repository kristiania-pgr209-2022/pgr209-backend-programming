package no.kristiania.library.database;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class BookDaoTest {

    private BookDao dao;

    @BeforeEach
    void setUp() {
        var dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:bookDaoTest;DB_CLOSE_DELAY=-1");

        var flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();

        dao = new BookDao(dataSource);
    }

    @Test
    void shouldRetrieveSavedBook() throws SQLException {
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
