package no.kristiania.library;

import jakarta.json.Json;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

public class BooksServlet extends HttpServlet {

    private final BookDao booksDao;

    public BooksServlet(DataSource dataSource) {
        this.booksDao = new BookDao(dataSource);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var json = Json.createReader(req.getReader()).readObject();
        var book = new Book();
        book.setName(json.getString("name"));
        booksDao.save(book);
        resp.setStatus(204);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        var arrayBuilder = Json.createArrayBuilder();
        for (Book book : booksDao.findAll()) {
            arrayBuilder.add(Json.createObjectBuilder().add("name", book.getName()));
        }
        List<Book> allBooks = booksDao.findAll();
        resp.getWriter().write(arrayBuilder.build().toString());
    }
}
