package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enums.Type;
import it.polimi.ingsw.tuples.Triplet;

import java.util.Set;

final class PersonalGoal {
    public final Set<Triplet<Integer, Integer, Type>> pg;
    public PersonalGoal(Set<Triplet<Integer, Integer, Type>> pg) {
        this.pg = pg;
    }
}
