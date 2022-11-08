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
import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

public class BookDaoTest {

    private BookDao bookDao;
    private EntityManager entityManager;

    @BeforeEach
    void setUp() throws NamingException {
        var dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:test");
        var flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();

        new Resource("jdbc/dataSource", dataSource);
        entityManager = Persistence.createEntityManagerFactory("library")
                .createEntityManager();
        bookDao = new BookDao(entityManager);
        entityManager.getTransaction().begin();
    }

    @AfterEach
    void tearDown() {
        entityManager.getTransaction().rollback();
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
