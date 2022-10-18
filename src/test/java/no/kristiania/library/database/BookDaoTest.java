package no.kristiania.library.database;

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
        dataSource.setUrl("jdbc:h2:mem:testDatabase");
        dao = new BookDao(dataSource);
    }

    @Test
    void shouldRetrieveSavedBook() throws SQLException {
        var book = sampleBook();
        dao.save(book);
        assertThat(dao.retrieve(book.getId()))
                .usingRecursiveComparison()
                .isEqualTo(book)
                .isNotSameAs(book)
        ;
    }

    private Book sampleBook() {
        return new Book();
    }
}
