package dropwizard.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Reference: https://github.com/jersey/jersey/tree/master/examples/declarative-linking
 */
public class ItemsModel {

    private List<ItemModel> items;
    private static ItemsModel instance;

    public static synchronized ItemsModel getInstance() {
        if (instance == null) {
            instance = new ItemsModel();
        }
        return instance;
    }

    private ItemsModel() {
        items = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            items.add(new ItemModel("Item " + i, "Description " + i));
        }
    }

    public boolean hasNext(String currentId) {
        return getIndex(currentId) < items.size() - 1;
    }

    public boolean hasPrev(String currentId) {
        return getIndex(currentId) > 0;
    }

    public ItemModel getItem(String id) {
        return items.get(getIndex(id));
    }

    public String getNextId(String id) {
        return Integer.toString(getIndex(id) + 1);
    }

    public String getPrevId(String id) {
        return Integer.toString(getIndex(id) - 1);
    }

    private int getIndex(String id) {
        return Integer.parseInt(id);
    }

    public int getSize() {
        return items.size();
    }
}