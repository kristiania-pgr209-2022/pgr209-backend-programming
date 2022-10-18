package no.kristiania.library.database;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhysicalBookDao {
    private final DataSource dataSource;

    public PhysicalBookDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insert(Library library, Book book) throws SQLException {
        try (var connection = dataSource.getConnection()) {
            var sql = "insert into physical_books (library_id, book_id) values (?, ?)";
            try (var statement = connection.prepareStatement(sql)) {
                statement.setLong(1, library.getId());
                statement.setLong(2, book.getId());

                statement.executeUpdate();
            }
        }
    }

    public List<Book> findByLibrary(long libraryId) throws SQLException {
        try (var connection = dataSource.getConnection()) {
            var sql = """
                select b.*
                from physical_books pb join books b on pb.book_id = b.id
                where library_id = ?
                """;
            try (var statement = connection.prepareStatement(sql)) {
                statement.setLong(1, libraryId);
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
