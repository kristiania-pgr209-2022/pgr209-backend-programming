package no.kristiania.library.database.jpa;

import jakarta.persistence.EntityManager;
import no.kristiania.library.database.Book;
import no.kristiania.library.database.Library;
import no.kristiania.library.database.PhysicalBookDao;

import java.sql.SQLException;
import java.util.List;

public class JpaPhysicalBookDao implements PhysicalBookDao {
    private final EntityManager entityManager;

    public JpaPhysicalBookDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void insert(Library library, Book book) {

    }

    @Override
    public List<Book> findByLibrary(long libraryId) {
        return null;
    }
}
