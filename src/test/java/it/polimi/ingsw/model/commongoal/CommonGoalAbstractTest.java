package it.polimi.ingsw.model.commongoal;

import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalAbstractTest {
    CommonGoalAbstract goal = new Square2x2Goal(2);
    @Test
    void getPoints(){
        assertEquals(4, goal.getPoints().get(0));
        assertEquals(8, goal.getPoints().get(1));
    }
    @Test
    void setPoint(){
        goal.setPoint(10);
        assertEquals(10, goal.getPoints().get(2));
    }
    @Test
    void removePoint(){
        assertEquals(8, goal.removePoint());
        assertEquals(1, goal.getPoints().size());

        assertEquals(4, goal.removePoint());
        assertEquals(0, goal.getPoints().size());
    }


}