package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enums.Type;
public final class Item {

    private final Type type;

    private final int variant ;

    public Item(Type type, int variant)  {
        this.type = type;
        this.variant = variant;
    }

    public Type getType(){return this.type;}
    public int getVariant(){return this.variant;}
}
