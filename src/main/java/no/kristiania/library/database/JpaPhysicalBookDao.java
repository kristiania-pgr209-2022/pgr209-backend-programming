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
        return (List<Book>) entityManager.createQuery(
                "select pb from PhysicalBook pb where pb.library.id = :libraryId"
        ).setParameter("libraryId", libraryId)
                .getResultList()
                .stream().map(pb -> ((PhysicalBook)pb).getBook())
                .collect(Collectors.toList());
    }
}
