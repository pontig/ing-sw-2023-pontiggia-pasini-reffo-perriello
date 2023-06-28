package it.polimi.ingsw.model;

import it.polimi.ingsw.enums.Type;

/**
 * Item is a class that represents an item to be placed on the shelf
 * It contains the type of the item and the variant of the item
 * <p>
 * The type is an enum (Ammo, Weapon, PowerUp)
 * The variant is an int (0, 1, 2) which identifies different images of items of the same type
 */
public final class Item {

    private Type type;

    private int variant;

    /**
     * Constructs a new item with the specified type and variant.
     *
     * @param type the type of the item
     * @param variant the variant of the item (an int (0, 1, 2) which identifies different images of items of the same type)
     */
    public Item(Type type, int variant) {
        this.type = type;
        this.variant = variant;
    }

    /**
     * Constructor Item
     */
    public Item() {}

    /**
     * getType is used to get the type of a certain item
     *
     * @return a type from the enum Type
     */
    public Type getType() {
        return this.type;
    }

    /**
     * getVariant is used to get the variant of a certain item (variants identify different images of items of the same type)
     *
     * @return an int (0, 1, 2) which identifies a certain variant
     */
    public int getVariant() {
        return this.variant;
    }

    /**
     * setType is used to set the type of a certain item
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * setVariant is used to set the variant of a certain item (variants identify different images of items of the same type)
     */
    public void setVariant(int variant) {
        this.variant = variant;
    }

    /**
     * setType is used to set the type of a certain item (JsonProperty)
     */
    public void setType(String type) {
        this.type = Type.valueOf(type);
    }
}
