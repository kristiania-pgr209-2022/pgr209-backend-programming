package no.kristiania.library.database;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LibraryDao {
    private final DataSource dataSource;

    public LibraryDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(Library library) {

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
