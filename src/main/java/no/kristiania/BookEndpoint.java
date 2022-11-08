package no.kristiania;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

import java.util.ArrayList;
import java.util.List;

@Path("/books")
public class BookEndpoint {

    private final List<Book> bookList = new ArrayList<>();

    @POST
    public void addBook(Book book) {
        bookList.add(book);
    }

    @GET
    public List<Book> listBooks() {
        return bookList;
    }
}
