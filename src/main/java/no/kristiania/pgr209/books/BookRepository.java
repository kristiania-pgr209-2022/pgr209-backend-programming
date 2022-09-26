package no.kristiania.pgr209.books;

import java.util.ArrayList;
import java.util.List;

public class BookRepository {
    private static final BookRepository instance = new BookRepository();

    public static BookRepository getInstance() {
        return instance;
    }

    private final List<String> books = new ArrayList<>();
    public void addBook(String title, String author) {
        books.add(title + " by " + author);
    }

    public List<String> listBooks() {
        return books;
    }
}
