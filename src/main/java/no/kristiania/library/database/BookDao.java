package no.kristiania.library.database;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

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

    public Book retrieve(Long id) throws SQLException {

        try (var connection = dataSource.getConnection()) {
            try (var statement = connection.prepareStatement("select * from books where id = ?")) {
                statement.setLong(1, id);
                try (var rs = statement.executeQuery()) {
                    rs.next();
                    var book = new Book();
                    book.setId(rs.getLong("id"));
                    return book;
                }
            }
        }
    }
}
