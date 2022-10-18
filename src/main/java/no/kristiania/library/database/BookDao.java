package no.kristiania.library.database;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Statement;

public class BookDao {

    private final DataSource dataSource;

    public BookDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(Book book) throws SQLException {
        try (var connection = dataSource.getConnection()) {
            var sql = "insert into books (title) values (?)";
            try (var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, book.getTitle());
                statement.executeUpdate();

                try (var generatedKeys = statement.getGeneratedKeys()) {
                    generatedKeys.next();
                    book.setId(generatedKeys.getLong("id"));
                }
            }
        }
    }

    public Book retrieve(long id) throws SQLException {

        try (var connection = dataSource.getConnection()) {
            try (var statement = connection.prepareStatement("select * from books where id = ?")) {
                statement.setLong(1, id);
                try (var rs = statement.executeQuery()) {
                    if (rs.next()) {
                        var book = new Book();
                        book.setId(rs.getLong("id"));
                        book.setTitle(rs.getString("title"));
                        return book;
                    } else {
                        return null;
                    }
                }
            }
        }
    }
}
