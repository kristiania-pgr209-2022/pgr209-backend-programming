package no.kristiania.library.database.jpa;

import jakarta.persistence.EntityManager;
import no.kristiania.library.database.Library;
import no.kristiania.library.database.LibraryDao;
import no.kristiania.library.database.jdbc.JdbcLibraryDao;

import java.sql.SQLException;

public class JpaLibraryDao implements LibraryDao {
    private final EntityManager entityManager;

    public JpaLibraryDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Library library) throws SQLException {
        entityManager.persist(library);
    }

    @Override
    public Library retrieve(long id) {
        return entityManager.find(Library.class, id);
    }
}
