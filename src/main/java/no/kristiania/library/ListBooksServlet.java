package no.kristiania.library;

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

        resp.getWriter().println("[");
        for (Book book : books) {
            resp.getWriter().print("{");
            resp.getWriter().print("\"title\": \"");
            resp.getWriter().print(book.getTitle());
            resp.getWriter().print("\"}");
        }
        resp.getWriter().println("]");

        resp.getWriter().print("");
    }
}
