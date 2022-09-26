package no.kristiania.pgr209;

import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import no.kristiania.pgr209.books.BookRepository;
import org.eclipse.jetty.servlet.Source;

import java.io.IOException;

public class ListBooksServlet extends HttpServlet {
    private final BookRepository bookRepository;

    public ListBooksServlet(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        for (String book : bookRepository.listBooks()) {
            resp.getWriter().write(book);
        }
    }
}
