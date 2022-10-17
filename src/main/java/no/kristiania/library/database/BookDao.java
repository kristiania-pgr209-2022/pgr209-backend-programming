package no.kristiania.library.database;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class BookDao {

    private final DataSource dataSource;
    private Map<Long, Book> books = new HashMap<>();

    public BookDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(Book book) throws SQLException {
        try (var connection = dataSource.getConnection()) {
            var sql = "insert into books (title, author_name) values (?, ?)";
            try (var statement = connection.prepareStatement(sql)) {
                statement.setString(1, book.getTitle());
                statement.setString(2, book.getAuthor());

                statement.executeUpdate();
            }
        }

        book.setId((long) (books.size() + 1));
        books.put(book.getId(), book);
    }

    public Book retrieve(Long id) {
        return books.get(id);
    }
}
