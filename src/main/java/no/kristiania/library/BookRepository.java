package no.kristiania.library;

import java.util.List;

public interface BookRepository {
    List<Book> listAll();

    void save(Book book);
}
