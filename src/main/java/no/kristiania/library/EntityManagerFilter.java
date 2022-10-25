package no.kristiania.library;

import jakarta.persistence.EntityManager;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.eclipse.jetty.servlet.Source;

import java.io.IOException;

public class EntityManagerFilter implements Filter {
    private final LibraryResourceConfig config;

    public EntityManagerFilter(LibraryResourceConfig config) {
        this.config = config;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        EntityManager entityManager = config.createEntityManagerForRequest();

        if (((HttpServletRequest)request).getMethod().equals("GET")) {
            chain.doFilter(request, response);
        } else {
            entityManager.getTransaction().begin();
            chain.doFilter(request, response);
            entityManager.flush();
            entityManager.getTransaction().commit();
        }

        config.cleanRequestEntityManager();
    }
}
