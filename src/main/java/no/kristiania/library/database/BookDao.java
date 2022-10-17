package no.kristiania.library.database;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDao {

    private final DataSource dataSource;

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
    }

    public Book retrieve(Long id) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (var statement = connection.prepareStatement("select * from books where id = ?")) {
                statement.setLong(1, id);

                try (var rs = statement.executeQuery()) {
                    if (rs.next()) {
                        return readBook(rs);
                    } else {
                        return null;
                    }
                }
            }
        }
    }

    public List<Book> findByAuthor(String author) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (var statement = connection.prepareStatement("select * from books where author_name = ?")) {
                statement.setString(1, author);

                try (var rs = statement.executeQuery()) {
                    var books = new ArrayList<Book>();
                    while (rs.next()) {
                        books.add(readBook(rs));
                    }
                    return books;
                }
            }
        }
    }

    static Book readBook(ResultSet rs) throws SQLException {
        var book = new Book();
        book.setId(rs.getLong("id"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author_name"));
        return book;
    }

}
