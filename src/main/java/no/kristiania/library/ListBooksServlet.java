package no.kristiania.library;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ListBooksServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var book = new Book();
        book.setTitle("Java in a nutshell");
        book.setAuthor("David Flannagan");
        var books = List.of(book);


        resp.getWriter().println("[{\"title\":\"Java in a nutshell\"}]");
    }
}
