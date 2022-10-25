package no.kristiania.library;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import no.kristiania.library.database.Book;

import java.util.List;

@Path("/books")
public class BookEndpoint {
    @POST
    public void addBook() {

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> listAllBooks() {
        return List.of();
    }
}
