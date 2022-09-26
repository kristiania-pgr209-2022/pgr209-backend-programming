package no.kristiania.pgr209;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import no.kristiania.pgr209.books.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddBookServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(AddBookServlet.class);
    private final BookRepository bookRepository;

    public AddBookServlet(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        var title = req.getParameter("title");
        var author = req.getParameter("author");
        logger.info("Added book! title={} author={}", title, author);
        bookRepository.addBook(title, author);
    }
}
