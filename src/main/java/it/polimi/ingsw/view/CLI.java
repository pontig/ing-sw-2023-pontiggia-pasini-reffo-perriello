package it.polimi.ingsw.view;

import it.polimi.ingsw.enums.State;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.Nickname;
import it.polimi.ingsw.network.messages.Players;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.observer.ObservableView;

import java.util.Scanner;

import static it.polimi.ingsw.enums.State.SET_NAME;
import static it.polimi.ingsw.enums.State.SET_NUMPLAYERS;

public class CLI extends ObservableView<Message> implements View {
    Scanner terminal = new Scanner(System.in);
    String nickname = null;
    int numPlayers = 0;
    Message msg = null;
    boolean playersFull = false;
    boolean askNumPlayer = false;
    public CLI(){
        super();
    }

    @Override
    public void run() {
        System.out.println("\nCLI run");
        while(true) {
            System.out.println("Insert your nickname:");
            nickname = terminal.next();
            msg = new Nickname(SET_NAME, nickname);
            setChangedView();
            notifyObserversView(msg);
            if(askNumPlayer) {
                System.out.println("Insert num players:");
                numPlayers = terminal.nextInt();
                msg = new Players(SET_NUMPLAYERS, numPlayers);
                setChangedView();
                notifyObserversView(msg);
            }
            if(playersFull) break;
        }
    }

    public void update(Server o, Message arg){
        State msg = arg.getInfo();
        switch(msg){
            case NICKNAME_AK:
                arg.printMsg();
                if(arg.getNumPlayers() == 0) askNumPlayer = true;
                break;
            case NICKNAME_NAK:
                arg.printMsg();
                System.out.println("No more players reuquired");
                playersFull = true;
                break;
            case PLAYERS_AK:
                arg.printMsg();
                break;
            default:
                System.err.println("Ignoring event from " + msg + ": " + arg);
                break;
        }
    }
}
