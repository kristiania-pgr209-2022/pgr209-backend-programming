package no.kristiania.library.database;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
            try (var statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, book.getTitle());
                statement.setString(2, book.getAuthor());

                statement.executeUpdate();

                try (var generatedKeys = statement.getGeneratedKeys()) {
                    generatedKeys.next();
                    book.setId(generatedKeys.getLong("id"));
                }
            }
        }
        books.put(book.getId(), book);
    }

    public Book retrieve(Long id) {
        return books.get(id);
    }
}
