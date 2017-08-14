package dropwizard;

import dropwizard.resources.ItemsResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import org.glassfish.jersey.linking.DeclarativeLinkingFeature;

/**
 * Pagination DW application
 *
 * @author Arul Dhesiaseelan (arul@httpmine.org)
 */
public class PaginationApplication extends Application<PaginationConfiguration> {

    public static void main(final String[] args) throws Exception {
        new PaginationApplication().run(args);
    }

    @Override
    public String getName() {
        return "Pagination";
    }

    @Override
    public void initialize(final Bootstrap<PaginationConfiguration> bootstrap) {
        bootstrap.addBundle(new ViewBundle<>());
    }

    @Override
    public void run(final PaginationConfiguration configuration,
                    final Environment environment) {
        final ItemsResource resource = new ItemsResource();
        environment.jersey().register(resource);
        environment.jersey().register(DeclarativeLinkingFeature.class);
    }

}