package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Shelf;

import java.util.Stack;

public abstract class CommonGoalAbstract {
    String description = null;
    private Stack<Integer> points;
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
    public Stack<Integer> getPoints(){
        return points;
    }
    public void setPoint(int point) { this.points.push(point); }
    public void setDescription(String description){ this.description = description; }
    public int removePoint(){
        return points.pop();
    }
    public String toString(){
        StringBuilder commonGoal = new StringBuilder(" ");
        Object[] pointsArray = getPoints().toArray();
        commonGoal.append(description).append("\n").append("The points left are: ");
        for(int i=0; i<getPoints().size(); i++)
            commonGoal.append(pointsArray[i]).append(" ");
        commonGoal.append("\n");
        return commonGoal.toString();
    }
    public abstract boolean specificGoal(Shelf shelf);
}
