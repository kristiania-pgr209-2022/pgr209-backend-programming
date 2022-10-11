package no.kristiania.library;

import java.util.ArrayList;
import java.util.List;

public class BookRepositoryImpl implements BookRepository {
    private final List<Book> books = new ArrayList<>(
            List.of(new Book("Hello World"))
    );

    @Override
    public List<Book> listAll() {
        return books;
    }

    @Override
    public void save(Book book) {
        books.add(book);
    }
}
