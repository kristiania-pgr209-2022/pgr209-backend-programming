package no.kristiania.library;

import java.util.ArrayList;
import java.util.List;

public class BookRepository {
    private List<Book> books = new ArrayList<>();

    public void add(Book book) {
        books.add(book);
    }
}
