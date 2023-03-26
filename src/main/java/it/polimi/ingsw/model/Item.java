package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enums.Type;
final class Item {

    // https://www.geeksforgeeks.org/create-immutable-class-java/
    // questa classe qui sarà immutabile. segui le istruzioni di questo link per capire come funziona

    private final Type type; //Read only
    private final int variant ;

    public Item(Type type, int variant)  {
        this.type = type;
        this.variant = variant;
    }

    public Type getType(){return this.type;}
    public int getVariant(){return this.variant;}
}
