package no.kristiania.library;

import org.glassfish.hk2.api.DynamicConfigurationService;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.Populator;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ClasspathDescriptorFileFinder;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Feature;
import jakarta.ws.rs.core.FeatureContext;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


/* Auto scan the jax-rx @Contract and @Service. From https://mkyong.com/webservices/jax-rs/jersey-and-hk2-dependency-injection-auto-scanning/  */
public class AutoScanFeature implements Feature {

    @Inject
    ServiceLocator serviceLocator;

    @Override
    public boolean configure(FeatureContext context) {

        DynamicConfigurationService dcs =
                serviceLocator.getService(DynamicConfigurationService.class);
        Populator populator = dcs.getPopulator();
        try {
            // Populator - populate HK2 service locators from inhabitants files
            // ClasspathDescriptorFileFinder - find files from META-INF/hk2-locator/default
            populator.populate(
                    new ClasspathDescriptorFileFinder(this.getClass().getClassLoader()));

        } catch (IOException | MultiException ex) {
            Logger.getLogger(AutoScanFeature.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
}
