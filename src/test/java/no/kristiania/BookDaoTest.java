package no.kristiania;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import org.eclipse.jetty.plus.jndi.Resource;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.naming.NamingException;

import static org.assertj.core.api.Assertions.assertThat;

public class BookDaoTest {

    private EntityManager entityManager;

    @BeforeEach
    void setUp() throws NamingException {
        var dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:test;MODE=LEGACY;DB_CLOSE_DELAY=-1");
        var flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();
        new Resource("jdbc/dataSource", dataSource);
        entityManager = Persistence
                .createEntityManagerFactory("library")
                .createEntityManager();
        entityManager.getTransaction().begin();
    }

    @AfterEach
    void tearDown() {
        entityManager.getTransaction().rollback();
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
