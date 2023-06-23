package it.polimi.ingsw.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.enums.Type;
import it.polimi.ingsw.tuples.Triplet;

import java.util.Set;

/**
 * PersonalGoal is a class that represents a personal goal of a player
 */
public final class PersonalGoal {
    public final Set<Triplet<Integer, Integer, Type>> pg;
    public final int which;

    /**
     * constructor of the class
     *
     * @param pg     set of triplets that represent the personal goal
     * @param number number of the personal goal [used for choosing the right image]
     */
    public PersonalGoal(Set<Triplet<Integer, Integer, Type>> pg, int number) {
        this.pg = pg;
        this.which = number;
    }

    /**
     * sendToString is used to send the personal goal to the client visible in the CLI
     *
     * @return a string that represents the personal goal
     */
    public String sendToString() {
        boolean item = false;
        StringBuilder personalGoal = new StringBuilder(" ");
        Triplet<Integer, Integer, Type>[] array = new Triplet[pg.size()];
        pg.toArray(array);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                for (Triplet<Integer, Integer, Type> p : array) {
                    if (p.get_x() == j && p.get_y() == i) {
                        item = true;
                        switch (p.get_z()) {
                            case BOOK:
                                personalGoal.append("W").append(" ");
                                break;
                            case CAT:
                                personalGoal.append("G").append(" ");
                                break;
                            case FRAME:
                                personalGoal.append("B").append(" ");
                                break;
                            case GAME:
                                personalGoal.append("Y").append(" ");
                                break;
                            case PLANTS:
                                personalGoal.append("P").append(" ");
                                break;
                            case TROPHY:
                                personalGoal.append("L").append(" ");
                                break;
                            default:
                                System.out.println("Error");
                                break;
                        }
                    }
                }
                if (!item)
                    personalGoal.append('■').append(" ");
                item = false;
            }
            personalGoal.append("\n ");
        }
        personalGoal.append("~").append(this.which).append("~");
        return personalGoal.toString();
    }
}
