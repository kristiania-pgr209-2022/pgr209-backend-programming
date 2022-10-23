package no.kristiania.library;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import no.kristiania.library.database.Book;
import no.kristiania.library.database.BookDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Path("/books")
public class BookResource {

    @Inject
    private BookDao bookDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> getAllBooks() {
        return bookDao.findAll();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addBook(Book book) throws SQLException {
        bookDao.save(book);
    }
}
