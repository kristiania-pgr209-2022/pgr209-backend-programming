package no.kristiania.library.database;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class BookDao {

    private final DataSource dataSource;
    private Map<Long, Book> allBooks = new HashMap<>();

    public BookDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(Book book) throws SQLException {
        try (var connection = dataSource.getConnection()) {
            try (var statement = connection.prepareStatement("insert into books (title) value (?)")) {
                statement.setString(1, book.getTitle());
                statement.executeUpdate();
            }
        }

        book.setId((long) allBooks.size());
        allBooks.put(book.getId(), book);
    }

    public Book retrieve(Long id) {
        return allBooks.get(id);
    }
}
