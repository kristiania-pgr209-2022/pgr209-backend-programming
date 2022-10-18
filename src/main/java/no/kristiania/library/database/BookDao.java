package no.kristiania.library.database;

import java.util.HashMap;
import java.util.Map;

public class BookDao {

    private Map<Long, Book> allBooks = new HashMap<>();

    public void save(Book book) {
        book.setId((long) allBooks.size());
        allBooks.put(book.getId(), book);
    }

    public Book retrieve(Long id) {
        return allBooks.get(id);
    }
}
