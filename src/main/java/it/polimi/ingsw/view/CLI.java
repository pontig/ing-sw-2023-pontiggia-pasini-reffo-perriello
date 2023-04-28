package it.polimi.ingsw.view;

import it.polimi.ingsw.enums.State;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.SendDataToServer;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.observer.ObservableView;

import java.util.Scanner;

import static it.polimi.ingsw.enums.State.*;

public class CLI extends ObservableView<Message> implements View, Runnable {
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
    private final Object lock = new Object();
    private volatile boolean isRunning = true;
    Message msg = null;
    String nickname;
    public CLI(){
        super();
    }

    public void stop() {
        isRunning = false;
        synchronized (lock) {
            lock.notifyAll();
        }
    }
    @Override
    public void run() {
        System.out.println("\nCLI run");

        System.out.println(YELLOW + "\n" + "ooo        ooooo                   .oooooo..o oooo                  oooo   .o88o.  o8o              \n" +
                                           "`88.       .888'                  d8P'    `Y8 `888                  `888   888 `\"  `\"'            \n" +
                                           " 888b     d'888  oooo    ooo      Y88bo.       888 .oo.    .ooooo.   888  o888oo  oooo   .ooooo.    \n" +
                                           " 8 Y88. .P  888   `88.  .8'        `\"Y8888o.   888P\"Y88b  d88' `88b  888   888    `888  d88' `88b \n" +
                                           " 8  `888'   888    `88..8'             `\"Y88b  888   888  888ooo888  888   888     888  888ooo888  \n" +
                                           " 8    Y     888     `888'         oo     .d8P  888   888  888    .o  888   888     888  888    .o   \n" +
                                           "o8o        o888o     .8'          8\"\"88888P'  o888o o888o `Y8bod8P' o888o o888o   o888o `Y8bod8P' \n" +
                                           "                 .o..P'                                                                             \n" +
                                           "                 `Y8P'                                                                              \n" + RESET);

        System.out.println("Please enter a nickname:");
        nickname = terminal.next();
        msg = new SendDataToServer(SET_NICKNAME, nickname, 0, 0, false);
        setChangedView();
        notifyObserversView(msg);

        while(isRunning) {
            switch (state) {
                //Form to enter a new nickname because someone already have the one expressed before
                case 0:
                    System.out.println("Someone has your nickname, plese enter a different one:");
                    nickname = terminal.next();
                    msg = new SendDataToServer(SET_NICKNAME, nickname, 0, 0, false);
                    setChangedView();
                    notifyObserversView(msg);
                    break;
                //All the players required are in game so no more are needed
                case 1:
                    System.out.println("No more players required, good bye");
                    stop();
                    break;
                //Waiting for player and waiting that a player does all its moves
                case 2:
                    System.out.println("--| WAITING |--");
                    synchronized (lock){
                        try{
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                //Insert the number of player for the game 2 / 3 / 4
                case 3:
                    System.out.println("Insert num players:");
                    msg = new SendDataToServer(SET_NUMPLAYERS, null, terminal.nextInt(), 0, false);
                    setChangedView();
                    notifyObserversView(msg);
                    break;
                //Selecting an items from board by typing its coordinated ros first followed by the column
                case 4:
                    System.out.println("\nEnter the coordinates of the selected item: (row_space_column)");
                    msg = new SendDataToServer(SELECT_ITEM, null, terminal.nextInt(), terminal.nextInt(), false);
                    setChangedView();
                    notifyObserversView(msg);
                    break;
                //Y = confirm the item selected from the board and then go to the insertion phase -- N = player want to add another item or deselect one
                case 5:
                    String confirmation = terminal.next();
                    if(confirmation.equals("Y") || confirmation.equals("y")) {
                        msg = new SendDataToServer(CONFIRM_ITEMS, null, 0, 0, true);
                        setChangedView();
                        notifyObserversView(msg);
                        break;
                    }
                    state = 4;
                    break;

                case 6:
                    if(terminal.next().equals("C"))
                        System.out.println("Selezione della colonna");
                    else
                        System.out.println("Ordinamento");
                    state = 2;
                    break;
                case 7:

                    break;

                default:
                    System.out.println("Error");
                    break;
            }

        }
    }

    public void update(Server o, Message arg){
        synchronized (lock) {
            State msg = arg.getInfo();
            switch (msg) {
                case ACK_NICKNAME:
                case ACK_NUMPLAYERS:
                    arg.printMsg();
                    state = 2;
                    break;

                case SAME_NICKNAME:
                    arg.printMsg();
                    state = 0;
                    break;

                case NACK_NICKNAME:
                    arg.printMsg();
                    state = 1;
                    break;

                case ASK_NUMPLAYERS:
                case OUT_BOUND_NUMPLAYERS:
                    arg.printMsg();
                    state = 3;
                    break;

                case SEND_MODEL:
                    arg.printMsg();
                    System.out.println(RED + "\nThis is the board:" + RESET);
                    showBoard(arg.getBoard());
                    System.out.print(RED + "\nThis is the first common goal:" + RESET);
                    System.out.print(arg.getFirstCommon());
                    System.out.print(RED + "\nThis is the second common goal:" + RESET);
                    System.out.print(arg.getSecondCommon());
                    System.out.println(RED + "\nThis is your personal goal:" + RESET);
                    showItems(arg.getPersonal());
                    if (this.nickname.equals(arg.getNickname())) state = 4;
                    else state = 2;
                    lock.notifyAll();
                    break;

                case SELECTED:
                    arg.printMsg();
                    System.out.print(RED + "\nThis is the board - the red highlight what ");
                    if(arg.getNickname().equals(this.nickname))
                        System.out.println("you selected:" + RESET);
                    else
                        System.out.println(arg.getNickname() + " selected:" + RESET);
                    showBoard(arg.getBoard());
                    if(arg.getNickname().equals(this.nickname)) {
                        System.out.print(RED + "\nYou chose:" + RESET);
                        showItems(arg.getSelected());
                        if(arg.getConfirm()){
                            System.out.println("\nIf you wanna confirm you selection enter 'Y' otherwise 'N':");
                            state = 5;
                        }
                        else state = 4;
                    }
                    else
                        state = 2;
                    break;

                case ORDER_n_COLUMN:
                    arg.printMsg();
                    System.out.println(RED + "\nThis is the board - without the choice of the current player " + RESET);
                    showBoard(arg.getBoard());
                    if(arg.getNickname().equals(this.nickname)){
                        System.out.print(RED + "\nThis is your shelf and the columns that you can choose\n " + RESET);
                        for(int i=0; i<5; i++) System.out.print(i + " ");
                        System.out.print("\n");
                        showItems(arg.getShelf());
                        showItems(arg.getColumns());
                        System.out.println(RED + "\n\nThese are the list of items:" + RESET);
                        System.out.print(RED + "UNORDERED ITEMS:" + RESET);
                        showItems(arg.getSelected());
                        System.out.print(RED + "\nORDERED ITEMS:" + RESET);
                        showItems(arg.getOrdered());

                        System.out.println("\nFor selecting column type \"C\" -- For ordering items type \"O\":");
                        state = 6;
                    }
                    else
                        state = 2;
                    break;

                default:
                    System.err.println("Ignoring event from " + msg + ": " + arg);
                    break;
            }
        }
    }
    private void showBoard(String board){
        System.out.print("  ");
        for(int i=0; i<9; i++) System.out.print(i + "  ");
        System.out.print("\n");
        int i = 0;
        for(int j = 0; j < board.length(); j++) {
            if(j == i*29 && i != 9){
                System.out.print(i);
                i++;
            }
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
                case '#':
                    System.out.print(RED + '■' + RESET);
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

    public void showItems(String items){
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
                case '▲':
                    System.out.print(RED + items.charAt(j) + RESET);
                    break;
                case '0':
                case '2':
                case '1':
                    System.out.print(" ");
                    break;
                default:
                    System.out.print(BLACK + items.charAt(j) + RESET);
                    break;
            }
        }
    }
}
