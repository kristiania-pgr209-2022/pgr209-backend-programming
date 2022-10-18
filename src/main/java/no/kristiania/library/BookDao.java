package no.kristiania.library;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDao {
    private final DataSource dataSource;


    public BookDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(Book book) {
        try (var connection = dataSource.getConnection()) {
            try (var statement = connection.prepareStatement("insert into books (name) values (?)")) {
                statement.setString(1, book.getName());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Book> findAll() {
        try (var connection = dataSource.getConnection()) {
            try (var statement = connection.prepareStatement("select * from books")) {
                try (var rs = statement.executeQuery()) {
                    var books = new ArrayList<Book>();
                    while (rs.next()) {
                        var book = new Book();
                        book.setName(rs.getString("name"));
                        books.add(book);
                    }
                    return books;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
