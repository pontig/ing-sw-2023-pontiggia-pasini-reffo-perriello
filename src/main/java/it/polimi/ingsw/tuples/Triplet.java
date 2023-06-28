package it.polimi.ingsw.tuples;

/**
 * A generic class representing a triplet of three values
 *
 * @param <A> the type of the first value in the triplet
 * @param <B> the type of the second value in the triplet
 * @param <C> the type of the third value in the triplet
 */
public class Triplet<A, B, C> {
    public final A _x;
    public final B _y;
    public final C _z;

    /**
     * Constructs a new Triplet object with default null values
     */
    public Triplet() {
        this(null, null, null);
    }

    /**
     * Constructs a new Triplet object with the specified values
     *
     * @param x the first value of the triplet
     * @param y the second value of the triplet
     * @param type the third value of the triplet
     */
    public Triplet(A x, B y, C type) {
        this._x = x;
        this._y = y;
        this._z = type;
    }

    /**
     * @return the first value of the triplet
     */
    public A get_x(){ return this._x; }

    /**
     * @return the second value of the triplet
     */
    public B get_y(){ return this._y; }

    /**
     * @return the third value of the triplet
     */
    public C get_z(){ return this._z; }

    /**
     * @return a string representation of the Triplet object
     */
    @Override
    public String toString() {
        return "Triplet{" +
                "_x=" + _x +
                ", _y=" + _y +
                ", _z=" + _z +
                '}';
    }
}
