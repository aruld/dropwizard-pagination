package dropwizard.util;

import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.namespace.QName;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;


/**
 * Value type for {@link javax.ws.rs.core.Link} that can be marshalled and unmarshalled by JAXB/MOXy.
 *
 * Reference: http://kingsfleet.blogspot.com/2014/05/reading-and-writing-jax-rs-link-objects.html
 *
 * @see javax.ws.rs.core.Link.JaxbLink
 */
public class CustomJaxbLink {

    private URI uri;
    private Map<QName, Object> params;


    public CustomJaxbLink() {
        this(null, null);
    }

    public CustomJaxbLink(URI uri) {
        this(uri, null);
    }

    public CustomJaxbLink(URI uri, Map<QName, Object> map) {

        this.uri = uri;
        this.params = map != null ? map : new HashMap<>();

    }


    @XmlAttribute(name = "href")
    public URI getUri() {
        return uri;
    }

    @XmlAnyAttribute
    public Map<QName, Object> getParams() {
        return params;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public void setParams(Map<QName, Object> params) {
        this.params = params;
    }

}