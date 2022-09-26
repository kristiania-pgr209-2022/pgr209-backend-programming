package no.kristiania.pgr209;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class AddBookServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(AddBookServlet.class);


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var title = req.getParameter("title");
        var author = req.getParameter("author");
        logger.info("Added book! title={} author={}", title, author);
    }
}
