package dropwizard.resources;

import dropwizard.api.ItemRepresentation;
import dropwizard.db.ItemModel;
import dropwizard.db.ItemsModel;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Reference: https://github.com/jersey/jersey/tree/master/examples/declarative-linking
 */
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class ItemResource {

    private ItemsModel itemsModel;
    private ItemModel itemModel;
    private String id;

    public ItemResource() {
        throw new IllegalStateException("Only for JAX-B dressing");
    }

    public ItemResource(ItemsModel itemsModel, String id) {
        this.id = id;
        this.itemsModel = itemsModel;
        try {
            itemModel = itemsModel.getItem(id);
        } catch (IndexOutOfBoundsException ex) {
            throw new NotFoundException();
        }
    }

    @GET
    public ItemRepresentation get() {
        return new ItemRepresentation(itemsModel, id, itemModel.getName(), itemModel.getDescription());
    }

    public String getId() {
        return id;
    }
}