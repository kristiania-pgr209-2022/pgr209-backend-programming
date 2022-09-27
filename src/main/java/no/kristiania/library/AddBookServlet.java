package no.kristiania.library;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class AddBookServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(AddBookServlet.class);

    private final BookRepository bookRepository;

    public AddBookServlet(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Book book = new Book();
        book.setAuthor(req.getParameter("bookAuthor"));
        book.setTitle(req.getParameter("bookTitle"));

        logger.info("Adding {}", book);
        bookRepository.add(book);
    }
}

