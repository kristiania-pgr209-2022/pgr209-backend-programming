package no.kristiania.library;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import no.kristiania.library.database.Book;
import no.kristiania.library.database.BookDao;

import java.sql.SQLException;
import java.util.List;

@Path("/books")
public class BookEndpoint {

    @Inject
    private BookDao bookDao;

    @POST
    public void addBook(Book book) throws SQLException {
        bookDao.save(book);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> listAllBooks() throws SQLException {
        return bookDao.listAll();
    }
}
