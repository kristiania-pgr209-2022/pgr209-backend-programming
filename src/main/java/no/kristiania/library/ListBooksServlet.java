package no.kristiania.library;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ListBooksServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var exampleBook = new Book();
        exampleBook.setTitle("Java in a nutshell");
        exampleBook.setAuthor("David Flannagan");
        var books = List.of(exampleBook);

        JsonArrayBuilder result = Json.createArrayBuilder();
        resp.getWriter().write("[");
        for (Book book : books) {
            result.add(Json.createObjectBuilder()
                    .add("title", book.getTitle())
                    .add("author", book.getAuthor())
            );

            resp.getWriter().write("{");
            resp.getWriter().write("\"title\":\"" + book.getTitle() + "\"");
            resp.getWriter().write("\"author\":\"" + book.getAuthor() + "\"");
            resp.getWriter().write("}");
        }
        resp.getWriter().write("]");
        result.build().toString();
    }
}
