package it.polimi.ingsw.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    public Item(Type type, int variant) {
        this.type = type;
        this.variant = variant;
    }
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

    public void setType(Type type) {
        this.type = type;
    }

    public void setVariant(int variant) {
        this.variant = variant;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = Type.valueOf(type);
    }
}
