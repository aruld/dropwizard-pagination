package dropwizard.api;

import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dropwizard.resources.ItemsResource;
import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;

import org.glassfish.jersey.test.inmemory.InMemoryTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerException;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * Reference: https://github.com/jersey/jersey/tree/master/examples/declarative-linking
 */
public class LinkWebAppTest extends JerseyTest {

    @Override
    protected ResourceConfig configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        final ResourceConfig rc = new ResourceConfig(ItemsResource.class);
        rc.register(LoggingFeature.class);
        rc.register(DeclarativeLinkingFeature.class);
        return rc;
    }

    @Override
    protected TestContainerFactory getTestContainerFactory() throws TestContainerException {
        return new InMemoryTestContainerFactory();
    }

    /**
     * Test that the expected response is sent back.
     */
    @Test
    public void testLinksJson() throws Exception {
        final Response response = target().path("items")
                .queryParam("offset", 10)
                .queryParam("limit", 10)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(Response.class);

        final Response.StatusType statusInfo = response.getStatusInfo();
        assertEquals("Should have succeeded", 200, statusInfo.getStatusCode());

        final String content = response.readEntity(String.class);
        final List<Object> linkHeaders = response.getHeaders().get("Link");

        assertEquals("Should have two link headers", 2, linkHeaders.size());
        assertThat("Content should contain next link",
                content,
                containsString("http://localhost:" + getPort() + "/items?offset=20&limit=10"));
    }

    /**
     * Test that the expected response is sent back.
     */
    @Test
    public void testLinksXml() throws Exception {
        final Response response = target().path("items")
                .queryParam("offset", 10)
                .queryParam("limit", 10)
                .request(MediaType.APPLICATION_XML_TYPE)
                .get(Response.class);

        final Response.StatusType statusInfo = response.getStatusInfo();
        assertEquals("Should have succeeded", 200, statusInfo.getStatusCode());

        final String content = response.readEntity(String.class);
        final List<Object> linkHeaders = response.getHeaders().get("Link");

        assertEquals("Should have two link headers", 2, linkHeaders.size());
        assertThat("Content should contain next link",
                content,
                containsString("http://localhost:" + getPort() + "/items?offset=20&amp;limit=10"));
    }
}