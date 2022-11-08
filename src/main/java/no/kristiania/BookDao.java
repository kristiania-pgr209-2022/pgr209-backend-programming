package no.kristiania;

import jakarta.persistence.EntityManager;

import java.util.List;

public class BookDao {
    private final EntityManager entityManager;

    public BookDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Book book) {

    }

    public List<Book> listAll() {
        return null;
    }
}
