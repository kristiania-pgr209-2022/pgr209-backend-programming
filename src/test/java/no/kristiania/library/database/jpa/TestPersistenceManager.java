package no.kristiania.library.database.jpa;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import org.eclipse.jetty.plus.jndi.Resource;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import javax.naming.NamingException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Properties;

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.GLOBAL;

@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(TestPersistenceManager.PersistenceManagerResolver.class)
public @interface TestPersistenceManager {

    String value();

    class PersistenceManagerResolver implements ParameterResolver, BeforeEachCallback, AfterEachCallback {

        @Override
        public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
            return parameterContext.getParameter().getType() == EntityManager.class;
        }

        @Override
        public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
            try {
                new Resource("jdbc/dataSource", createDataSource());
            } catch (NamingException | IOException e) {
                throw new ParameterResolutionException(e.toString());
            }

            var persistenceUnitName = parameterContext.getParameter().getAnnotation(TestPersistenceManager.class).value();
            var entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
            var entityManager = entityManagerFactory.createEntityManager();
            extensionContext.getStore(GLOBAL).put(EntityManager.class.getName(), entityManager);
            return entityManager;
        }

        private Object createDataSource() throws IOException {
            var properties = new Properties();
            try (var reader = new FileReader("application.properties")) {
                properties.load(reader);
            }

            var dataSource = new HikariDataSource();
            dataSource.setJdbcUrl(properties.getProperty("jdbc.url", "jdbc:postgresql://localhost:5432/library"));
            dataSource.setUsername(properties.getProperty("jdbc.username", "kristiania_app"));
            dataSource.setPassword(properties.getProperty("jdbc.password"));
            Flyway.configure().dataSource(dataSource).load().migrate();

            return dataSource;
        }

        @Override
        public void beforeEach(ExtensionContext context) {
            context.getStore(GLOBAL).get(EntityManager.class.getName(), EntityManager.class)
                    .getTransaction().begin();
        }

        @Override
        public void afterEach(ExtensionContext context) {
            context.getStore(GLOBAL).get(EntityManager.class.getName(), EntityManager.class)
                    .getTransaction().rollback();
        }
    }
}
