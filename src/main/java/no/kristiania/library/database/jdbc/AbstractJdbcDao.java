package no.kristiania.library.database.jdbc;

import no.kristiania.library.database.Book;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AbstractJdbcDao {
    @FunctionalInterface
    interface RowMapper<T> {
        T mapRow(ResultSet rs) throws SQLException;
    }

    protected <T> T collectSingleResult(
            PreparedStatement statement,
            RowMapper<T> rowMapper
    ) throws SQLException {
        try (var rs = statement.executeQuery()) {
            if (rs.next()) {
                return rowMapper.mapRow(rs);
            } else {
                return null;
            }
        }
    }

    protected <T> List<T> collectQueryResult(
            PreparedStatement statement,
            RowMapper<T> rowMapper
    ) throws SQLException {
        try (var rs = statement.executeQuery()) {
            var result = new ArrayList<T>();
            while (rs.next()) {
                result.add(rowMapper.mapRow(rs));
            }
            return result;
        }
    }
}
