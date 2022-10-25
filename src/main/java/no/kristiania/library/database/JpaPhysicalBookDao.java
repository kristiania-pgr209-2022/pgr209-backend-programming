package no.kristiania.library.database;

import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.stream.Collectors;

public class JpaPhysicalBookDao implements PhysicalBookDao {
    private final EntityManager entityManager;

    public JpaPhysicalBookDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void insert(Library library, Book book) {
        entityManager.persist(new PhysicalBook(library, book));
    }

    @Override
    public List<Book> findByLibrary(long libraryId) {
        return entityManager.find(Library.class, libraryId)
                .getPhysicalBooks()
                .stream().map(PhysicalBook::getBook)
                .collect(Collectors.toList());
    }
}
