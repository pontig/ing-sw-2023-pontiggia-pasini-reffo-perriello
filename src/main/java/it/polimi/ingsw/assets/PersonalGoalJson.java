package it.polimi.ingsw.assets;

import it.polimi.ingsw.enums.Type;
import it.polimi.ingsw.tuples.Triplet;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PersonalGoalJson {
    public List<Triplet<Integer, Integer, String>> pg;

    /**
     * Converts the list of triplets to a set of triplets
     * @return set of triplets
     */
    public Set<Triplet<Integer, Integer, Type>> toSet() {
        Set<Triplet<Integer, Integer, Type>> set = new HashSet<>();
        for (Triplet<Integer, Integer, String> triplet : pg) {
            set.add(new Triplet<>(triplet.get_x(), triplet.get_y(), Type.valueOf(triplet.get_z())));
        }
        return set;
    }
}
