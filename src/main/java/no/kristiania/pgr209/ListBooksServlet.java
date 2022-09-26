package no.kristiania.pgr209;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import no.kristiania.pgr209.books.BookRepository;

import java.io.IOException;

public class ListBooksServlet extends HttpServlet {
    private final BookRepository bookRepository;

    public ListBooksServlet(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        for (String book : bookRepository.listBooks()) {
            resp.getWriter().write(book);
        }
    }
}
