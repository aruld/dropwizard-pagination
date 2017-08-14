package dropwizard.api;

import dropwizard.db.ItemsModel;
import dropwizard.resources.ItemResource;
import dropwizard.util.CustomJaxbAdapter;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

/**
 * Reference: https://github.com/jersey/jersey/tree/master/examples/declarative-linking
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "item")
@InjectLinks({
        @InjectLink(
                resource = ItemResource.class,
                style = InjectLink.Style.ABSOLUTE,
                condition = "${instance.next}",
                bindings = @Binding(name = "id", value = "${instance.nextId}"),
                rel = "next"
        ),
        @InjectLink(
                resource = ItemResource.class,
                style = InjectLink.Style.ABSOLUTE,
                condition = "${instance.prev}",
                bindings = @Binding(name = "id", value = "${instance.prevId}"),
                rel = "prev"
        )
})
public class ItemRepresentation {

    @XmlElement
    private String name;

    @XmlElement
    private String description;

    @XmlTransient
    private String id;

    @XmlTransient
    private ItemsModel itemsModel;

    @InjectLink(
            resource = ItemResource.class,
            style = InjectLink.Style.ABSOLUTE,
            bindings = @Binding(name = "id", value = "${instance.id}"),
            rel = "self"
    )
    @XmlJavaTypeAdapter(CustomJaxbAdapter.class)
    @XmlElement(name = "link")
    Link self;

    @InjectLinks({
            @InjectLink(
                    resource = ItemResource.class,
                    style = InjectLink.Style.ABSOLUTE,
                    condition = "${instance.next}",
                    bindings = @Binding(name = "id", value = "${instance.nextId}"),
                    rel = "next"
            ),
            @InjectLink(
                    resource = ItemResource.class,
                    style = InjectLink.Style.ABSOLUTE,
                    condition = "${instance.prev}",
                    bindings = @Binding(name = "id", value = "${instance.prevId}"),
                    rel = "prev"
            )})
    @XmlElement(name = "link")
    @XmlElementWrapper(name = "links")
    @XmlJavaTypeAdapter(CustomJaxbAdapter.class)
    List<Link> links;

    public ItemRepresentation() {

    }

    public ItemRepresentation(ItemsModel itemsModel, String id, String name, String description) {
        this.itemsModel = itemsModel;
        this.name = name;
        this.description = description;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public boolean isNext() {
        return itemsModel.hasNext(id);
    }

    public boolean isPrev() {
        return itemsModel.hasPrev(id);
    }

    public String getNextId() {
        return itemsModel.getNextId(id);
    }

    public String getPrevId() {
        return itemsModel.getPrevId(id);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Link getSelf() {
        return self;
    }
}