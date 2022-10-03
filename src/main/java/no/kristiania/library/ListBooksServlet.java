package no.kristiania.library;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ListBooksServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var exampleBook = new Book();
        exampleBook.setAuthor("Test Persson");
        exampleBook.setTitle("Example Book");
        var books = List.of(exampleBook);

        JsonArrayBuilder booksJson = Json.createArrayBuilder();
        for (Book book : books) {
            booksJson.add(Json.createObjectBuilder()
                    .add("title", book.getTitle())
            );
        }
        resp.getWriter().print(booksJson.build().toString());
    }
}
