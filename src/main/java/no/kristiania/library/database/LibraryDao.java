package no.kristiania.library.database;

import java.sql.SQLException;

public interface LibraryDao {
    void save(Library library) throws SQLException;

    Library retrieve(long id) throws SQLException;
}
