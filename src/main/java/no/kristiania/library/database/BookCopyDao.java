package no.kristiania.library.database;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookCopyDao {
    private final DataSource dataSource;

    public BookCopyDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insert(Library library, Book book) throws SQLException {
        try (var connection = dataSource.getConnection()) {
            var sql = "insert into book_copies (library_id, book_id) values (?, ?)";
            try (var statement = connection.prepareStatement(sql)) {
                statement.setLong(1, library.getId());
                statement.setLong(2, book.getId());
                statement.executeUpdate();
            }
        }
    }

    public List<Book> findByLibrary(Long id) throws SQLException {
        try (var connection = dataSource.getConnection()) {
            var sql = "select b.* from book_copies c inner join books b on c.book_id = b.id where c.library_id = ?";
            try (var statement = connection.prepareStatement(sql)) {
                statement.setLong(1, id);

                try (var rs = statement.executeQuery()) {
                    var books = new ArrayList<Book>();
                    while (rs.next()) {
                        books.add(BookDao.readBook(rs));
                    }
                    return books;
                }
            }
        }
    }
}
