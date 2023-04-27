package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.StateGame;
import it.polimi.ingsw.enums.State;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.SendDataToServer;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.observer.ObservableView;

import java.util.Scanner;

import static it.polimi.ingsw.enums.State.*;

public class CLI extends ObservableView<Message> implements View {
    // Reset
    public static final String RESET = "\033[0m";  // Text Reset
    // Regular Colors
    public static final String BLACK = "\033[0;30m";   // BLACK
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;92m";   // GREEN
    public static final String YELLOW = "\033[0;93m";  // YELLOW
    public static final String BLUE = "\033[0;94m";    // BLUE
    public static final String PURPLE = "\033[0;95m";  // PURPLE
    public static final String CYAN = "\033[0;96m";    // CYAN
    public static final String WHITE = "\033[0;97m";   // WHITE
    int state = 0;
    Scanner terminal = new Scanner(System.in);
    Message msg = null;
    boolean playersFull = false;
    boolean askNumPlayer = false;
    boolean askNickname = false;
    boolean choosingConfirm = true;
    public CLI(){
        super();
    }

    @Override
    public void run() {
        System.out.println("\nCLI run");
        switch(state) {
            case 0:             //LogIn
                do{
                    System.out.println("Insert your nickname:");
                    msg = new SendDataToServer(SET_NICKNAME, terminal.next(), 0,0,false);
                    setChangedView();
                    notifyObserversView(msg);
                } while(askNickname);

                if(playersFull){
                    state = 10;
                    break;
                }

                while(askNumPlayer) {
                    System.out.println("Insert num players:");
                    msg = new SendDataToServer(SET_NUMPLAYERS, null, terminal.nextInt(),0,false);
                    setChangedView();
                    notifyObserversView(msg);
                }
                state = 1;

            case 1:
                do {
                    System.out.println("Enter the coordinates of the selected item: (row_space_column)");
                    msg = new SendDataToServer(SELECT_ITEM, null, terminal.nextInt(), terminal.nextInt(), false);
                    setChangedView();
                    notifyObserversView(msg);
                } while(choosingConfirm);

            case 2:
                break;

            case 10:
                System.out.println("No more players required");
                break;
        }
    }

    public void update(Server o, Message arg){
        State msg = arg.getInfo();
        switch(msg){
            case ASK_NUMPLAYERS:
                arg.printMsg();
                askNickname = false;
                askNumPlayer = true;
                break;

            case ACK_NUMPLAYERS:
                arg.printMsg();
                askNumPlayer = false;
                break;

            case OUT_BOUND_NUMPLAYERS:
                arg.printMsg();
                askNumPlayer = true;
                break;

            case SEND_MODEL:
                arg.printMsg();
                askNumPlayer = false;
                choosingConfirm = true;
                System.out.println(RED + "\nThis is the board:" + RESET);
                showBoard(arg.getBoard());
                System.out.print(RED + "\nThis is the first common goal:" + RESET);
                System.out.print(arg.getFirstCommon());
                System.out.print(RED + "\nThis is the second common goal:" + RESET);
                System.out.print(arg.getSecondCommon());
                System.out.println(RED + "\nThis is your personal goal:" + RESET);
                showShelf(arg.getPersonal());
                break;

            default:
                System.err.println("Ignoring event from " + msg + ": " + arg);
                break;
        }
    }
    private void showBoard(String board){
        for(int j = 0; j < board.length(); j++) {
            switch(board.charAt(j)){
                case 'W':
                    System.out.print(WHITE + '■' + RESET);
                    break;
                case 'G':
                    System.out.print(GREEN + '■' + RESET);
                    break;
                case 'B':
                    System.out.print(BLUE + '■' + RESET);
                    break;
                case 'Y':
                    System.out.print(YELLOW + '■' + RESET);
                    break;
                case 'P':
                    System.out.print(PURPLE + '■' + RESET);
                    break;
                case 'L':
                    System.out.print(CYAN + '■' + RESET);
                    break;
                case '0':
                case '2':
                case '1':
                    System.out.print(" ");
                    break;
                default:
                    System.out.print(BLACK + board.charAt(j) + RESET);
                    break;
            }
        }
    }

    public void showShelf(String items){
        for(int j = 0; j < items.length(); j++) {
            switch(items.charAt(j)){
                case 'W':
                    System.out.print(WHITE + '■' + RESET);
                    break;
                case 'G':
                    System.out.print(GREEN + '■' + RESET);
                    break;
                case 'B':
                    System.out.print(BLUE + '■' + RESET);
                    break;
                case 'Y':
                    System.out.print(YELLOW + '■' + RESET);
                    break;
                case 'P':
                    System.out.print(PURPLE + '■' + RESET);
                    break;
                case 'L':
                    System.out.print(CYAN + '■' + RESET);
                    break;
                default:
                    System.out.print(BLACK + items.charAt(j) + RESET);
                    break;
            }
        }
    }
}
