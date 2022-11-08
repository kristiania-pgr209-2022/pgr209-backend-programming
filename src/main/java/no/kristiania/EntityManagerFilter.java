package no.kristiania;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.eclipse.jetty.servlet.Source;

import java.io.IOException;

public class EntityManagerFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        MyResourceConfig.startTransaction();
        chain.doFilter(request, response);
        MyResourceConfig.commitTransaction();
    }
}
