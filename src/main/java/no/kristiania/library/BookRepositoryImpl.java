package no.kristiania.library;

import java.util.List;

public class BookRepositoryImpl implements BookRepository {

    private final List<Book> books = List.of(new Book("Hello World"));

    @Override
    public List<Book> listAll() {
        return books;
    }
}
