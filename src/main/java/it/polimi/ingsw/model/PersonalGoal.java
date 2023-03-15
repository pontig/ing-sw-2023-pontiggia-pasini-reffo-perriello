package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enums.Type;

import java.util.Set;

public class PersonalGoal {
    private final Set<PG<Integer, Integer, Type>> pg;
    public PersonalGoal(Set<PG<Integer, Integer, Type>> pg) {
        this.pg = pg;
    }
}
