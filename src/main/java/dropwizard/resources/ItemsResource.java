package dropwizard.resources;

import dropwizard.api.ItemsRepresentation;
import dropwizard.db.ItemModel;
import dropwizard.db.ItemsModel;
import dropwizard.views.ItemsView;
import io.dropwizard.jersey.params.IntParam;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

/**
 * Reference: https://github.com/jersey/jersey/tree/master/examples/declarative-linking
 *
 * @author Arul Dhesiaseelan (arul@httpmine.org)
 */
@Path("items")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class ItemsResource {

    private ItemsModel itemsModel;

    public ItemsResource() {
        itemsModel = ItemsModel.getInstance();
    }

    @GET
    public ItemsRepresentation query(
            @Context javax.ws.rs.core.UriInfo info,
            @QueryParam("offset") @DefaultValue("-1") IntParam _offset, @QueryParam("limit") @DefaultValue("-1") IntParam _limit) {

        int offset = _offset.get();
        int limit = _limit.get();

        if (offset == -1 || limit == -1) {
            offset = offset == -1 ? 0 : offset;
            limit = limit == -1 ? 10 : limit;

            throw new WebApplicationException(
                    Response.seeOther(info.getRequestUriBuilder().queryParam("offset", offset)
                            .queryParam("limit", limit).build())
                            .build()
            );
        }

        return new ItemsRepresentation(itemsModel, offset, limit);
    }

    @Path("{id}")
    public ItemResource get(@PathParam("id") String id) {
        return new ItemResource(itemsModel, id);
    }

    @GET
    @Path("view")
    @Produces({MediaType.TEXT_HTML})
    public ItemsView view(@Context javax.ws.rs.core.UriInfo info,
                           @QueryParam("offset") @DefaultValue("-1") IntParam _offset, @DefaultValue("-1") @QueryParam("limit") IntParam _limit){

        int offset = _offset.get();
        int limit = _limit.get();

        if (offset == -1 || limit == -1) {
            offset = offset == -1 ? 0 : offset;
            limit = limit == -1 ? 10 : limit;

            throw new WebApplicationException(
                    Response.seeOther(info.getRequestUriBuilder().queryParam("offset", offset)
                            .queryParam("limit", limit).build())
                            .build()
            );
        }

        ItemsRepresentation representation = new ItemsRepresentation(itemsModel, offset, limit);

        // render the view
        return new ItemsView(representation);
    }

}