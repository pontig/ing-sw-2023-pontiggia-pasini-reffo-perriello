package it.polimi.ingsw.model;

import it.polimi.ingsw.enums.Type;
import it.polimi.ingsw.tuples.Triplet;

import java.util.Set;

public final class PersonalGoal {
    public final Set<Triplet<Integer, Integer, Type>> pg;
    public final int which;

    /**
     * Constructor of PersonalGoal
     *
     * @param pg the deck of personal goals
     */
    public PersonalGoal(Set<Triplet<Integer, Integer, Type>> pg, int number) {
        this.pg = pg;
        this.which = number;
    }

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
