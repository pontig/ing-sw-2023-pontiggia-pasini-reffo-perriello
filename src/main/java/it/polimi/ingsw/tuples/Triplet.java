package it.polimi.ingsw.tuples;

public class Triplet<A, B, C> {
    public final A _x;
    public final B _y;
    public final C _z;

    public Triplet() {
        this(null, null, null);
    }
    public Triplet(A x, B y, C type) {
        this._x = x;
        this._y = y;
        this._z = type;
    }

    public A get_x(){ return this._x; }
    public B get_y(){ return this._y; }
    public C get_z(){ return this._z; }

    @Override
    public String toString() {
        return "Triplet{" +
                "_x=" + _x +
                ", _y=" + _y +
                ", _z=" + _z +
                '}';
    }
}
