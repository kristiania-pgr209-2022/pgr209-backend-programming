package no.kristiania;

import jakarta.persistence.EntityManager;

public class BookDao {

    private final EntityManager entityManager;

    public BookDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Book book) {
        entityManager.persist(book);
    }

    public Book retrieve(long id) {
        return entityManager.find(Book.class, id);
    }
}
