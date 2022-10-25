package no.kristiania.library.database;

import jakarta.persistence.EntityManager;

public class JpaLibraryDao implements LibraryDao {
    private final EntityManager entityManager;

    public JpaLibraryDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Library library) {

    }

    @Override
    public Library retrieve(long id) {
        return null;
    }
}
