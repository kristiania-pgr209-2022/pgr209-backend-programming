package no.kristiania.library;

import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

public class TransactionFilter implements Filter {
    private final EntityManagerFactory entityManagerFactory;

    public TransactionFilter(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        var entityManager = entityManagerFactory.createEntityManager();
        LibraryResourceConfig.setEntityManager(entityManager);
        try {
            if (((HttpServletRequest) request).getMethod().equals("GET")) {
                chain.doFilter(request, response);
            } else {
                entityManager.getTransaction().begin();
                chain.doFilter(request, response);
                entityManager.getTransaction().commit();
            }
        } catch (RuntimeException e) {
            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            LibraryResourceConfig.setEntityManager(null);
        }
    }
}
