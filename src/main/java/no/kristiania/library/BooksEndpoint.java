package no.kristiania.library;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.List;

@Path("/books")
public class BooksEndpoint {

    @Inject
    private BookRepository repository;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addBook(Book book) {
        repository.add(book);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> listBooks() {
        return repository.listAll();
    }

}
