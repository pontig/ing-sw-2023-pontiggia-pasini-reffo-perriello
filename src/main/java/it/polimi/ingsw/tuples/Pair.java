package it.polimi.ingsw.tuples;

/**
 * A generic class representing a pair of two values
 *
 * @param <X> the type of the first value in the pair
 * @param <Y> the type of the second value in the pair
 */
public class Pair <X, Y>{
    private X x;
    private Y y;

    /**
     * Constructs a pair with the specified values
     *
     * @param x the first value of the pair
     * @param y the second value of the pair
     */
    public Pair(X x, Y y){
        this.x = x;
        this.y = y;
    }

    /**
     * @return the first value of the pair
     */
    public X getX() { return this.x; }

    /**
     * @return the second value of the pair
     */
    public Y getY() { return this.y; }

    /**
     * Sets the first value of the pair
     *
     * @param x the new first value of the pair
     */
    public void setX(X x) { this.x = x; }

    /**
     * Sets the second value of the pair
     *
     * @param y the new second value of the pair
     */
    public void setY(Y y) { this.y = y;}
}