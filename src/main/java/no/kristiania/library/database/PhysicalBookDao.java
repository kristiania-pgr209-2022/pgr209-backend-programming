package no.kristiania.library.database;

import java.sql.SQLException;
import java.util.List;

public interface PhysicalBookDao {
    void insert(Library library, Book book) throws SQLException;

    List<Book> findByLibrary(long libraryId) throws SQLException;
}
