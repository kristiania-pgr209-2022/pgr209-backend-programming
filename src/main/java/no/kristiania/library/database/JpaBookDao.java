package no.kristiania.library.database;

import jakarta.persistence.EntityManager;

import java.util.List;

public class JpaBookDao implements BookDao {
    private final EntityManager entityManager;

    public JpaBookDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Book book) {
        entityManager.persist(book);
    }

    @Override
    public Book retrieve(long id) {
        return entityManager.find(Book.class, id);
    }

    @Override
    public List<Book> listAll() {
        return entityManager.createQuery(
                entityManager.getCriteriaBuilder().createQuery(Book.class)
        ).getResultList();
    }

    @Override
    public List<Book> findByAuthorName(String authorName) {
        return entityManager.createNamedQuery("findByAuthorName")
                .setParameter("authorName", authorName)
                .getResultList();
    }
}
