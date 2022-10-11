package no.kristiania.library;

import java.util.List;

public class BookRepository {

    private final List<Book> books = List.of(new Book("Hello World"));

    public List<Book> listAll() {
        return books;
    }
}
