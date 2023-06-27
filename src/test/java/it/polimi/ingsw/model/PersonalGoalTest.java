package it.polimi.ingsw.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.assets.PersonalGoalJson;
import it.polimi.ingsw.enums.Type;
import it.polimi.ingsw.tuples.Triplet;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

class PersonalGoalTest {

    @Test
    void sendToString() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        PersonalGoalJson[] personalGoalJsonArray = objectMapper.readValue(PersonalGoalTest.class.getResourceAsStream("/json/personalGoals.json"), PersonalGoalJson[].class);

        List<PersonalGoal> personalGoalList = new ArrayList<>();
        int i = 0;
        for (PersonalGoalJson personalGoalJson : personalGoalJsonArray) {
            Set<Triplet<Integer, Integer, Type>> pg = personalGoalJson.toSet();
            personalGoalList.add(new PersonalGoal(pg, i));
            i++;
        }

        for(PersonalGoal p: personalGoalList)
            p.sendToString();
    }
}