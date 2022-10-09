package no.kristiania.library;

import org.jvnet.hk2.annotations.Service;

import java.util.List;

@Service
public class BookRepository {
    static List<Book> listAllBooks() {
        return List.of(new Book("Hello World"));
    }
}
