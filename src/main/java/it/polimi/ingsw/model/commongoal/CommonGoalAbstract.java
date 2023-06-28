package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Shelf;

import java.util.Stack;

public abstract class CommonGoalAbstract {
    String description = null;
    private Stack<Integer> points;

    /**
     * Constructor: fills the stack with the correct set of points
     *
     * @param numberPlayers the number of players of the game
     */
    public CommonGoalAbstract(int numberPlayers){
        points = new Stack<>();
        switch (numberPlayers){
            case 2:
                points.push(4);
                points.push(8);
                break;

            case 3:
                points.push(4);
                points.push(6);
                points.push(8);
                break;

            case 4:
                points.push(2);
                points.push(4);
                points.push(6);
                points.push(8);
                break;

            default:

                break;
        }
    }

    /**
     * Sets the remaining points for the common goal
     *
     * @param remaining a stack of integers representing the remaining points
     */
    public void setPoints(Stack<Integer> remaining) {
        this.points = remaining;
    }

    /**
     * Returns the stack of remaining points for the common goal
     *
     * @return a stack of integers representing the remaining points
     */
    public Stack<Integer> getPoints(){
        return points;
    }

    /**
     * Adds a point to the stack of remaining points for the common goa
     *
     * @param point the point to be added
     */
    public void setPoint(int point) { this.points.push(point); }

    /**
     * Sets the description of the common goal
     *
     * @param description the description to be set
     */
    public void setDescription(String description){ this.description = description; }

    /**
     * Removes and returns the topmost point from the stack of remaining points for the common goal
     *
     * @return the removed point
     */
    public int removePoint(){
        return points.pop();
    }

    /**
     * toString is used to have a represetnation of the common goal as a string
     *
     * @return a string representation of the common goal
     */
    public String toString(){
        StringBuilder commonGoal = new StringBuilder();
        Object[] pointsArray = getPoints().toArray();
        commonGoal.append(description).append("\n").append("The points left are: ");
        for(int i=0; i<getPoints().size(); i++)
            commonGoal.append(pointsArray[i]).append(" ");
        commonGoal.append("\n");
        return commonGoal.toString();
    }

    /**
     * Checks if the common goal is achieved based on the specific criteria for each subclass
     *
     * @param shelf the shelf object to be evaluated
     * @return true if the common goal is achieved, false otherwise
     */
    public abstract boolean specificGoal(Shelf shelf);
}
