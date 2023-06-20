package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Item;
import it.polimi.ingsw.model.Shelf;
import it.polimi.ingsw.enums.Type;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Covers more than one goal:
 * Two columns each formed by 6 different types of tiles
 * Three columns each formed by 6 tiles of maximum three different types. One column can show the same or a different combination of another column
 * Two lines each formed by 5 different types of tiles. One line can show the same or a different combination of the other line
 * Four lines each formed by 5 tiles of
 * maximum three different types. One line can show the same or a different combination of another line.
 */
public class AdjacentDifferentItemsGoal extends CommonGoalAbstract {
    private char direction; // h for horizontal, v for vertical
    private int variety; // number of different items in a row or column
    private int quantity; // minimum number of rows or columns with the correct variety

    /**
     * Constructor of AdjacentDifferentItemsGoal
     * @param numberPlayers number of players
     * @param direction direction of the rows or columns to check
     * @param variety number of different items in a row or column
     * @param quantity minimum number of rows or columns with the correct variety
     */
    public AdjacentDifferentItemsGoal(int numberPlayers, char direction, int variety, int quantity) {
        super(numberPlayers);
        this.direction = direction;
        this.variety = variety;
        this.quantity = quantity;
    }

    /**
     * Checks if the item is already in the set and adds it if it isn't
     * @param i item to check
     * @param set set of items to check against
     * @return if the variety is correct
     */
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

    /**
     * Checks if the shelf has the correct number of rows or columns with the correct variety
     * @param shelf shelf to check
     * @return if the shelf has the correct number of rows or columns with the correct variety
     * @throws IllegalArgumentException if the direction is not 'h' or 'v'
     */
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
