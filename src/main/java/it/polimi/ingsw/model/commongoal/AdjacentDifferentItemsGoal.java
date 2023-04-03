package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Item;
import it.polimi.ingsw.model.Shelf;
import it.polimi.ingsw.model.enums.Type;

import java.util.ArrayList;
import java.util.HashSet;

public class AdjacentDifferentItemsGoal extends CommonGoalAbstract {
    private char direction; // h for horizontal, v for vertical
    private int variety; // number of different items in a row or column
    private int quantity; // minimum number of rows or columns with the correct variety

    public AdjacentDifferentItemsGoal(int numberPlayers, char direction, int variety, int quantity) {
        super(numberPlayers);
        this.direction = direction;
        this.variety = variety;
        this.quantity = quantity;
    }

    private boolean checkVariety(Type i, HashSet<Type> set) {
        switch (variety) {
            case 3:
                if (set.contains(i))
                    return true;
                set.add(i);
                return set.size() <= 3;
            default:
                if (set.contains(i))
                    return false;
                set.add(i);
                return true;
        }
    }

    @Override
    public boolean specificGoal(Shelf shelf) throws IllegalArgumentException {
        ArrayList<ArrayList<Item>> set; // set of rows or columns to cycle through
        int count = 0; // number of sets of items that have been checked to have the correct variety
        switch (direction) {
            case 'h':
                set = shelf.getRows();
                break;
            case 'v':
                set = shelf.getColumns();
                break;
            default:
                throw new IllegalArgumentException("Direction must be 'h' or 'v'");
        }
        for (ArrayList<Item> l : set) {
            boolean isCorrectForNow = true;
            HashSet<Type> uniqueItems = new HashSet<>();
            for (Item i : l) {
                if (i == null) {
                    isCorrectForNow = false;
                    break;
                }
                isCorrectForNow = checkVariety(i.getType(), uniqueItems);
                if (!isCorrectForNow) {
                    break;
                }
            }
            if (isCorrectForNow) count++;
        }
        return count >= quantity;
    }
}
