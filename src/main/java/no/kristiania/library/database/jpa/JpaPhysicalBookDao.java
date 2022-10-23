package no.kristiania.library.database.jpa;

import jakarta.persistence.EntityManager;
import no.kristiania.library.database.Book;
import no.kristiania.library.database.Library;
import no.kristiania.library.database.PhysicalBook;
import no.kristiania.library.database.PhysicalBookDao;

import java.util.List;
import java.util.stream.Collectors;

public class JpaPhysicalBookDao implements PhysicalBookDao {
    private final EntityManager entityManager;

    public JpaPhysicalBookDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void insert(Library library, Book book) {
        entityManager.persist(new PhysicalBook(book, library));
    }

    @Override
    public List<Book> findByLibrary(long libraryId) {
        return entityManager.createNamedQuery("findAll", PhysicalBook.class)
                .getResultList()
                .stream().map(PhysicalBook::getBook)
                .collect(Collectors.toList());
    }
}
