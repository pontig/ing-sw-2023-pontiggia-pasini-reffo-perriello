package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Shelf;

import java.util.Stack;

public abstract class CommonGoalAbstract {
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
    public int removePoint(){
        return points.pop();
    }
    public abstract boolean specificGoal(Shelf shelf);
}
