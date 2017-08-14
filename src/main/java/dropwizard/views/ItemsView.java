package dropwizard.views;

import dropwizard.api.ItemRepresentation;
import dropwizard.api.ItemsRepresentation;
import io.dropwizard.views.View;

import java.util.List;

public class ItemsView extends View {

    private final ItemsRepresentation itemsRepresentation;

    public ItemsView(ItemsRepresentation itemsRepresentation) {
        super("Items.ftl");
        this.itemsRepresentation = itemsRepresentation;
    }

//    public List<ItemRepresentation> getItems() {
//        return items.getItems();
//    }

    public ItemsRepresentation getItemsRepresentation() {
        return itemsRepresentation;
    }

}
