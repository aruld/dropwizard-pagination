package dropwizard.db;

/**
 * Reference: https://github.com/jersey/jersey/tree/master/examples/declarative-linking
 */
public class ItemModel {
    String name;
    String description;

    public ItemModel(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}