package no.kristiania.library.database.jdbc;

import no.kristiania.library.database.Book;
import no.kristiania.library.database.BookDao;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class JdbcBookDao extends AbstractJdbcDao implements BookDao {

    private final DataSource dataSource;

    public JdbcBookDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override public void save(Book book) throws SQLException {
        try (var connection = dataSource.getConnection()) {
            var sql = "insert into books (title, author_name) values (?, ?)";
            try (var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, book.getTitle());
                statement.setString(2, book.getAuthorName());
                statement.executeUpdate();

                try (var generatedKeys = statement.getGeneratedKeys()) {
                    generatedKeys.next();
                    book.setId(generatedKeys.getLong(1));
                }
            }
        }
    }

    @Override public Book retrieve(long id) throws SQLException {
        try (var connection = dataSource.getConnection()) {
            try (var statement = connection.prepareStatement("select * from books where id = ?")) {
                statement.setLong(1, id);
                return collectSingleResult(statement, JdbcBookDao::readBook);
            }
        }
    }

    @Override public List<Book> findByAuthorName(String authorName) throws SQLException {
        try (var connection = dataSource.getConnection()) {
            try (var statement = connection.prepareStatement("select * from books where author_name = ?")) {
                statement.setString(1, authorName);
                return collectQueryResult(statement, JdbcBookDao::readBook);
            }
        }
    }

    @Override
    public List<Book> findAll() throws SQLException {
        try (var connection = dataSource.getConnection()) {
            try (var statement = connection.prepareStatement("select * from books")) {
                return collectQueryResult(statement, JdbcBookDao::readBook);
            }
        }
    }

    public static Book readBook(ResultSet rs) throws SQLException {
        var book = new Book();
        book.setId(rs.getLong("id"));
        book.setTitle(rs.getString("title"));
        book.setAuthorName(rs.getString("author_name"));
        return book;
    }

}
