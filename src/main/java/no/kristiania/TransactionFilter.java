package no.kristiania;

import jakarta.persistence.EntityManager;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import java.io.IOException;

public class TransactionFilter implements Filter {
    private final MyResourceConfig resourceConfig;

    public TransactionFilter(MyResourceConfig resourceConfig) {
        this.resourceConfig = resourceConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        EntityManager entityManager = resourceConfig.createEntityManager();
        var transaction = entityManager.getTransaction();
        transaction.begin();
        chain.doFilter(request, response);
        transaction.commit();
        resourceConfig.removeEntityManager();
    }
}
