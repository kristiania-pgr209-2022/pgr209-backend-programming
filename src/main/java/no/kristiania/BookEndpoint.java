package no.kristiania;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.List;

@Path("/books")
public class BookEndpoint {

    private final List<Book> bookList = new ArrayList<>();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addBook(Book book) {
        bookList.add(book);
    }

    @GET
    public List<Book> listBooks() {
        return bookList;
    }
}
