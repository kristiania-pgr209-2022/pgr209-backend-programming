package no.kristiania.library.database;

import jakarta.persistence.EntityManager;

import java.sql.SQLException;
import java.util.List;

public class JpaBookDao implements BookDao {
    private final EntityManager entityManager;

    public JpaBookDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Book book) {

    }

    @Override
    public Book retrieve(long id) {
        return null;
    }

    @Override
    public List<Book> findByAuthorName(String authorName) {
        return null;
    }
}
