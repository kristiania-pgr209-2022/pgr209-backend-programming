package no.kristiania.library.database.jpa;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import no.kristiania.library.database.Book;
import no.kristiania.library.database.BookDao;

import java.util.List;

public class JpaBookDao implements BookDao {
    private final EntityManager entityManager;

    @Inject
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
    public List<Book> findByAuthorName(String authorName) {
        return entityManager.createNamedQuery("findByAuthorName", Book.class)
                .setParameter("authorName", authorName)
                .getResultList();
    }

    @Override
    public List<Book> findAll() {
        return entityManager
                .createQuery(entityManager.getCriteriaBuilder().createQuery(Book.class))
                .getResultList();
    }
}
