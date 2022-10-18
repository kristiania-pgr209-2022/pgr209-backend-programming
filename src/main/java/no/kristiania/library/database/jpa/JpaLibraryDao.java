package no.kristiania.library.database.jpa;

import no.kristiania.library.database.Library;
import no.kristiania.library.database.LibraryDao;

import jakarta.persistence.EntityManager;
import java.sql.SQLException;

public class JpaLibraryDao implements LibraryDao {

    private final EntityManager entityManager;

    public JpaLibraryDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Library library) {
        entityManager.persist(library);
    }

    @Override
    public Library retrieve(long id) {
        return entityManager.find(Library.class, id);
    }
}
