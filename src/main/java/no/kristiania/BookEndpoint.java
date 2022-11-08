package no.kristiania;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/books")
public class BookEndpoint {

    @Inject
    private BookDao bookDao;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addBook(Book book) {
        bookDao.save(book);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> listBooks() {
        return bookDao.listAll();
    }
}
