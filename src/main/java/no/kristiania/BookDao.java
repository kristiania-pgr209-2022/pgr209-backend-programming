package no.kristiania;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

public class BookDao {
    private final EntityManager entityManager;

    @Inject
    public BookDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Book book) {
        entityManager.persist(book);
    }

    public List<Book> listAll() {
        return entityManager.createQuery(
                "select b from Book b"
        ).getResultList();
    }
}
