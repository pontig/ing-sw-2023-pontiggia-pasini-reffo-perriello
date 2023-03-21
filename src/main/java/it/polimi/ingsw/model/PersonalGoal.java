package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enums.Type;

import java.util.Set;

final class PersonalGoal {
    private final Set<PG<Integer, Integer, Type>> pg;
    public PersonalGoal(Set<PG<Integer, Integer, Type>> pg) {
        this.pg = pg;
    }
}
