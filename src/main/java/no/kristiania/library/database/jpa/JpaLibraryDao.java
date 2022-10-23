package no.kristiania.library.database.jpa;

import jakarta.persistence.EntityManager;
import no.kristiania.library.database.Library;
import no.kristiania.library.database.LibraryDao;
import no.kristiania.library.database.jdbc.JdbcLibraryDao;

import java.sql.SQLException;

public class JpaLibraryDao implements LibraryDao {
    public JpaLibraryDao(EntityManager entityManager) {
    }

    @Override
    public void save(Library library) throws SQLException {

    }

    @Override
    public Library retrieve(long id) throws SQLException {
        return null;
    }
}
