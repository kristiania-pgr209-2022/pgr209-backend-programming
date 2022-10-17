package no.kristiania.library.database;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class BookDao {

    private final DataSource dataSource;
    private Map<Long, Book> books = new HashMap<>();

    public BookDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(Book book) {
        book.setId((long) (books.size() + 1));
        books.put(book.getId(), book);
    }

    public Book retrieve(Long id) {
        return books.get(id);
    }
}
