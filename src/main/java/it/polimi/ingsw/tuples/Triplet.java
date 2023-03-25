package it.polimi.ingsw.tuples;

public class Triplet<A, B, C> {
    public final A _x;
    public final B _y;
    public final C _z;

    public Triplet(A x, B y, C type) {
        this._x = x;
        this._y = y;
        this._z = type;
    }

    @Override
    public String toString() {
        return "Triplet{" +
                "_x=" + _x +
                ", _y=" + _y +
                ", _z=" + _z +
                '}';
    }
}
