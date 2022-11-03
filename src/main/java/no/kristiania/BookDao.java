package no.kristiania;

import jakarta.persistence.EntityManager;

import java.util.List;

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

    public List<Book> findAll() {
        return entityManager.createQuery("select b from Book b")
                .getResultList();
    }
}
