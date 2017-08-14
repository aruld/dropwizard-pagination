package dropwizard.api;

import dropwizard.db.ItemsModel;
import dropwizard.resources.ItemsResource;
import dropwizard.util.CustomJaxbAdapter;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Reference: https://github.com/jersey/jersey/tree/master/examples/declarative-linking
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "items")
@InjectLinks({
        @InjectLink(
                resource = ItemsResource.class,
                style = InjectLink.Style.ABSOLUTE,
                method = "query",
                condition = "${instance.offset + instance.limit < instance.modelLimit}",
                bindings = {
                        @Binding(name = "offset", value = "${instance.offset + instance.limit}"),
                        @Binding(name = "limit", value = "${instance.limit}")
                },
                rel = "next"
        ),
        @InjectLink(
                resource = ItemsResource.class,
                style = InjectLink.Style.ABSOLUTE,
                method = "query",
                condition = "${instance.offset - instance.limit >= 0}",
                bindings = {
                        @Binding(name = "offset", value = "${instance.offset - instance.limit}"),
                        @Binding(name = "limit", value = "${instance.limit}")
                },
                rel = "prev"
        )})

public class ItemsRepresentation {

    @XmlElement(name = "items")
    private List<ItemRepresentation> items;

    @XmlTransient
    private int offset, limit;

    @XmlTransient
    private ItemsModel itemsModel;

    @InjectLink(
            resource = ItemsResource.class,
            method = "query",
            style = InjectLink.Style.ABSOLUTE,
            bindings = {@Binding(name = "offset", value = "${instance.offset}"),
                    @Binding(name = "limit", value = "${instance.limit}")
            },
            rel = "self"
    )
    @XmlJavaTypeAdapter(CustomJaxbAdapter.class)
    @XmlElement(name = "link")
    Link self;

    @InjectLinks({
            @InjectLink(
                    resource = ItemsResource.class,
                    style = InjectLink.Style.ABSOLUTE,
                    method = "query",
                    condition = "${instance.offset + instance.limit < instance.modelLimit}",
                    bindings = {
                            @Binding(name = "offset", value = "${instance.offset + instance.limit}"),
                            @Binding(name = "limit", value = "${instance.limit}")
                    },
                    rel = "next"
            ),
            @InjectLink(
                    resource = ItemsResource.class,
                    style = InjectLink.Style.ABSOLUTE,
                    method = "query",
                    condition = "${instance.offset - instance.limit >= 0}",
                    bindings = {
                            @Binding(name = "offset", value = "${instance.offset - instance.limit}"),
                            @Binding(name = "limit", value = "${instance.limit}")
                    },
                    rel = "prev"
            )})
    @XmlElement(name = "link")
    @XmlElementWrapper(name = "links")
    @XmlJavaTypeAdapter(CustomJaxbAdapter.class)
    List<Link> links;

    public ItemsRepresentation() {
        offset = 0;
        limit = 10;
    }

    public ItemsRepresentation(ItemsModel itemsModel, int offset, int limit) {

        this.offset = offset;
        this.limit = limit;
        this.itemsModel = itemsModel;

        items = new ArrayList<>();
        for (int i = offset; i < (offset + limit) && i < itemsModel.getSize(); i++) {
            items.add(new ItemRepresentation(
                    itemsModel,
                    Integer.toString(i),
                    itemsModel.getItem(Integer.toString(i)).getName(), itemsModel.getItem(Integer.toString(i)).getDescription()));
        }

    }

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }

    public int getModelLimit() {
        return itemsModel.getSize();
    }

    public List<ItemRepresentation> getItems() {
        return items;
    }

    public Link getSelf() {
        return self;
    }

    public List<Link> getLinks() {
        return links;
    }
}