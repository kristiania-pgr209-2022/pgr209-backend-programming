package no.kristiania.library;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ListBooksServlet extends HttpServlet {
    private final BookRepository bookRepository;

    public ListBooksServlet(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        resp.getWriter().write("<html>");
        resp.getWriter().write("Here are the books in the category of " + req.getPathInfo());
        resp.getWriter().write("<ul>");
        for (Book book : bookRepository.listAll()) {
            resp.getWriter().write("<li>" + book + "</li>");
        }
        resp.getWriter().write("</ul>");
        resp.getWriter().write("</html>");
    }
}
