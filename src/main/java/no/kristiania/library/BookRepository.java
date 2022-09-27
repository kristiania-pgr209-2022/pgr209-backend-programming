package no.kristiania.library;

import java.util.ArrayList;
import java.util.List;

public class BookRepository {
    private static BookRepository instance = new BookRepository();
    private List<Book> books = new ArrayList<>();

    public static BookRepository getInstance() {
        return instance;
    }

    public void add(Book book) {
        books.add(book);
    }

    public List<Book> listAll() {
        return books;
    }
}
