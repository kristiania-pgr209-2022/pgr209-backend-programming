package no.kristiania.library;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

public class BookRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void add(Book book) {
        entityManager.persist(book);
    }

    public List<Book> listAll() {
        return entityManager
                .createQuery(entityManager.getCriteriaBuilder().createQuery(Book.class))
                .getResultList();
    }
}
