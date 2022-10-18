package no.kristiania.library.database;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LibraryDao {
    private final DataSource dataSource;

    public LibraryDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(Library library) throws SQLException {
        try (var connection = dataSource.getConnection()) {
            var sql = "insert into libraries (name) values (?)";
            try (var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, library.getName());
                statement.executeUpdate();

                try (var generatedKeys = statement.getGeneratedKeys()) {
                    generatedKeys.next();
                    library.setId(generatedKeys.getLong("id"));
                }
            }
        }
    }

    public Library retrieve(long id) throws SQLException {
        try (var connection = dataSource.getConnection()) {
            try (var statement = connection.prepareStatement("select * from libraries where id = ?")) {
                statement.setLong(1, id);
                try (var rs = statement.executeQuery()) {
                    rs.next();
                    var library = new Library();
                    library.setName(rs.getString("name"));
                    return library;
                }
            }
        }
    }
}
