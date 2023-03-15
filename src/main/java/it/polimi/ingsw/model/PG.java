package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enums.Type;

public class PG<A, B, C> {
    public final A _x;
    public final B _y;
    public final C type;
    public PG(A x, B y, C type) {
        this._x = x;
        this._y = y;
        this.type = type;
    }
    @Override
    public String toString() {
        return "PG{" +
                "_x=" + _x +
                ", _y=" + _y +
                ", type=" + type +
                '}';
    }
}
