package it.polimi.ingsw.model;

import it.polimi.ingsw.enums.Type;
public final class Item {

    private final Type type;

    private final int variant ;

    public Item(Type type, int variant)  {
        this.type = type;
        this.variant = variant;
    }

    /** getType is used to get the type of a certain item
     * @return a type from the enum Type
     */
    public Type getType(){return this.type;}

    /** getVariant is used to get the variant of a certain item (variants identify different images of items of the same type)
     * @return an int (0, 1, 2) which identifies a certain variant
     */
    public int getVariant(){return this.variant;}
}
