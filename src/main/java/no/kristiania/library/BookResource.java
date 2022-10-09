package no.kristiania.library;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("")
public class BookResource {

    @Inject
    private BookRepository bookRepository;

    @GET
    @Path("/books")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> listBooks() {
        return BookRepository.listAllBooks();
    }
}
