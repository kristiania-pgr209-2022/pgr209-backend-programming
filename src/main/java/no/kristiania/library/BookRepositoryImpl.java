package no.kristiania.library;

import java.util.ArrayList;
import java.util.List;

public class BookRepositoryImpl implements BookRepository {
    private final List<Book> books = new ArrayList<>(
            List.of(sampleBook())
    );

    private static Book sampleBook() {
        var book = new Book();
        book.setTitle("Hello World");
        return book;
    }

    @Override
    public List<Book> listAll() {
        return books;
    }

    @Override
    public void save(Book book) {
        books.add(book);
    }
}
