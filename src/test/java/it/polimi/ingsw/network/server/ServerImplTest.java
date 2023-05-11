package it.polimi.ingsw.network.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.enums.CommonGoalName;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.PersonalGoal;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServerImplTest {
    ServerImpl s = new ServerImpl();

    ServerImplTest() throws RemoteException {
    }

    @Test
    void register() {
    }

    @Test
    void updateModel() {
    }
}