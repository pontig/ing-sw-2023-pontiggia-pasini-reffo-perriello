package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Board;

import java.util.Stack;

public abstract class CommonGoalAbstract {
    private Stack<Integer> points;

    public CommonGoalAbstract(int numberPlayers){
        points = new Stack<>();
        switch (numberPlayers){     //si puo fare una lista con i punti e per ogni case un ciclo in modulo che seleziona solo i punti giusti
            case 2:
                points.push();

            case 3:
                points.push();

            case 4:
                points.push();

            default:
                //impossibile
        }
    }

    public Stack<Integer> getPointsLeft(){
        return points;
    }
    public int removePoints(){
        return points.pop();
    }

    public abstract boolean specificGoal(Board board);

}
