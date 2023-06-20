package it.polimi.ingsw.view;

import it.polimi.ingsw.enums.State;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Shelf;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.SendDataToServer;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.observer.ObservableView;
import it.polimi.ingsw.observer.ObserverView;

import java.io.IOException;
import java.util.Scanner;

import static it.polimi.ingsw.enums.State.*;

public class CLI extends View {
    // Reset
    public static final String RESET = "\033[0m";  // Text Reset
    // Regular Colors
    public static final String BLACK = "\033[0;30m";   // BLACK
    public static final String RED = "\033[0;91m";     // RED
    public static final String GREEN = "\033[0;92m";   // GREEN
    public static final String YELLOW = "\033[0;93m";  // YELLOW
    public static final String BLUE = "\033[0;94m";    // BLUE
    public static final String PURPLE = "\033[0;95m";  // PURPLE
    public static final String CYAN = "\033[0;36m";    // CYAN
    public static final String WHITE = "\033[0;97m";   // WHITE
    int state = 20;
    Scanner terminal = new Scanner(System.in);
    private final Object lock = new Object();
    private volatile boolean isRunning = true;
    Message msg = null;
    String nickname;

    public CLI() {
        super();
    }

    /**
     * stops the thread
     */
    public void stop() {
        isRunning = false;
        synchronized (lock) {
            lock.notifyAll();
        }
    }

    /**
     * @see View#run()
     */
    @Override
    public void run() {
        System.out.println("\nCLI RUNNING CORRECTLY");

        System.out.println(YELLOW + "\n" + "ooo        ooooo                   .oooooo..o oooo                  oooo   .o88o.  o8o              \n" +
                "`88.       .888'                  d8P'    `Y8 `888                  `888   888 `\"  `\"'            \n" +
                " 888b     d'888  oooo    ooo      Y88bo.       888 .oo.    .ooooo.   888  o888oo  oooo   .ooooo.    \n" +
                " 8 Y88. .P  888   `88.  .8'        `\"Y8888o.   888P\"Y88b  d88' `88b  888   888    `888  d88' `88b \n" +
                " 8  `888'   888    `88..8'             `\"Y88b  888   888  888ooo888  888   888     888  888ooo888  \n" +
                " 8    Y     888     `888'         oo     .d8P  888   888  888    .o  888   888     888  888    .o   \n" +
                "o8o        o888o     .8'          8\"\"88888P'  o888o o888o `Y8bod8P' o888o o888o   o888o `Y8bod8P' \n" +
                "                 .o..P'                                                                             \n" +
                "                 `Y8P'                                                                              \n" + RESET);

        System.out.print("Please enter a nickname: ");
        nickname = terminal.next();
        msg = new SendDataToServer(SET_NICKNAME, nickname, 0, 0, false);
        setChangedView();
        notifyObserversView(msg);

        while (isRunning) {
            System.out.println("Stato: " + state);
            switch (state) {
                case 0:
                    //Form to enter a new nickname because someone already have the one expressed before
                    System.out.print("Someone has a nickname as the one you just wrote.\nPlease enter a different nickname: ");
                    nickname = terminal.next();
                    msg = new SendDataToServer(SET_NICKNAME, nickname, 0, 0, false);
                    setChangedView();
                    notifyObserversView(msg);
                    msg = null;
                    break;
                case 1:
                    //All the players required are in game so no more are needed
                    System.out.println("No more players are required in this game, we are sorry, you will be disconnected");
                    stop();
                    break;
                case 2:
                    //Waiting for player and waiting
                    synchronized (lock) {
                        try {
                            System.out.print("\n\n ~ WAITING FOR PLAYERS ~ \n\n");
                            lock.wait();        //possibile lock per tutti i giocatori -> da inviare una richiesta al server se i due numeri sono uguali
                            msg = null;
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 3:
                    //Insert the number of player for the game 2 / 3 / 4
                    int numberPlayers = 0;
                    boolean okNumber = false;
                    System.out.print("Insert the number of players for the game (2 / 3 / 4): ");
                    do {
                        try {
                            numberPlayers = Integer.parseInt(terminal.next());
                            if (numberPlayers < 2 || numberPlayers > 4) throw new NumberFormatException();
                            okNumber = true;
                        } catch (NumberFormatException e) {
                            System.out.print("You entered an invalid character, please enter a number (2 / 3 / 4): ");
                        }
                    } while (!okNumber);
                    msg = new SendDataToServer(SET_NUMPLAYERS, this.nickname, numberPlayers, 0, false);
                    setChangedView();
                    notifyObserversView(msg);
                    msg = null;
                    break;
                case -1:
                    //Check if the game should begin
                    msg = new SendDataToServer(GAME_READY, null, 0, 0, false);
                    setChangedView();
                    notifyObserversView(msg);
                    msg = null;
                    break;
                case 4:
                    //Selecting an items from board by typing its coordinated ros first followed by the column
                    int row = -1;
                    int column = -1;
                    System.out.println("\nEnter the coordinates of the item you wanna select: ");
                    System.out.print("Row: ");
                    do {
                        try {
                            row = Integer.parseInt(terminal.next());
                        } catch (NumberFormatException e) {
                            System.out.println("It is not a valid number!!");
                        }
                        if (row < 0 || row > 8)
                            System.out.print("Enter a valid number for row: ");
                    } while (row < 0 || row > 8);

                    System.out.print("Column: ");
                    do {
                        try {
                            column = Integer.parseInt(terminal.next());
                        } catch (NumberFormatException e) {
                            System.out.println("It is not a valid number!!");
                        }
                        if (column < 0 || column > 8)
                            System.out.print("Enter a valid number for row: ");
                    } while (column < 0 || column > 8);
                    msg = new SendDataToServer(SELECT_ITEM, null, row, column, false);
                    setChangedView();
                    notifyObserversView(msg);
                    msg = null;
                    break;

                case 5:
                    //Y = confirm the item selected from the board and then go to the insertion phase
                    msg = new SendDataToServer(CONFIRM_ITEMS, null, 0, 0, true);
                    setChangedView();
                    notifyObserversView(msg);
                    msg = null;
                    break;

                case 6:
                    String orderOrColumn = terminal.next();
                    if (orderOrColumn.equals("C") || orderOrColumn.equals("c")) {
                        System.out.println("Type the number of one of the columns in the shelf highlighted with an arrow.");
                        boolean okColumn = false;
                        int columnShelf = 0;
                        do {
                            try {
                                columnShelf = Integer.parseInt(terminal.next());
                                okColumn = true;
                            } catch (NumberFormatException e) {
                                System.out.println("Please try again with a valid number: ");
                            }
                        } while (!okColumn);
                        msg = new SendDataToServer(SELECT_COLUMN, null, 0, columnShelf, false);
                    } else {
                        System.out.println("Now you must order the items in the UNORDERED LIST, to do so you select the list and the item you wanna order");
                        System.out.println("Type 1 for the UNORDERED LIST or type 0 for the ORDERED.");
                        System.out.print("From the list (0 / 1): ");
                        int action = -1;
                        do {
                            try {
                                action = Integer.parseInt(terminal.next());
                            } catch (NumberFormatException e) {
                                System.out.println("It is not a valid number!!");
                            }
                            if (action != 0 && action != 1)
                                System.out.print("Enter 0 for ORDERED or 1 for UNORDERED: ");
                        } while (action != 0 && action != 1);
                        System.out.print("Order the item in position (0 / 1 / 2): ");
                        int pos = -1;
                        do {
                            try {
                                pos = Integer.parseInt(terminal.next());
                            } catch (NumberFormatException e) {
                                System.out.println("It is not a valid number!!");
                            }
                            if (pos != 0 && pos != 1 && pos != 2)
                                System.out.print("Enter 0 - first item, 1 - second item or 2 - third item: ");
                        } while (pos != 0 && pos != 1 && pos != 2);
                        msg = new SendDataToServer(ORDER_ITEM, null, action, pos, false);
                    }
                    setChangedView();
                    notifyObserversView(msg);
                    msg = null;
                    break;

                case 7:
                    //Confirm column and order
                    msg = new SendDataToServer(CONFIRM_INSERTION, null, 0, 0, true);
                    setChangedView();
                    notifyObserversView(msg);
                    msg = null;
                    break;

                case 20:
                    synchronized (lock) {
                        try {
                            lock.wait();
                            msg = null;
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;

                default:
                    System.out.println("Error");
                    break;
            }

        }
    }

    /**
     * @see View#update(Server, Message)
     */
    public void update(Server o, Message arg) {
        synchronized (lock) {
            State msg = arg.getInfo();
            switch (msg) {
                case ACK_NICKNAME:
                case ACK_NUMPLAYERS:
                    arg.printMsg();
                    state = 2;
                    lock.notifyAll();
                    break;

                case SAME_NICKNAME:
                    arg.printMsg();
                    state = 0;
                    break;

                case NACK_NICKNAME:
                    arg.printMsg();
                    state = 1;
                    break;

                case NACK_NUMPLAYERS:
                    arg.printMsg();
                    if (arg.getNickname().equals(this.nickname))
                        System.out.println("Someone has already chosen the number of players, you will be added to that game");
                    state = -1;
                    break;

                case ASK_NUMPLAYERS:
                case OUT_BOUND_NUMPLAYERS:
                    arg.printMsg();
                    if (msg == OUT_BOUND_NUMPLAYERS && arg.getNickname().equals(this.nickname))
                        System.out.println("Enter a valid number of player!!");
                    state = 3;
                    lock.notifyAll();
                    break;

                case GAME_READY:
                    arg.printMsg();
                    if(nickname.equals(arg.getNickname())){
                        System.out.println("Board");
                        showBoard(arg.getBoard());
                        System.out.print("First");
                        System.out.print(arg.getFirstCommon());
                        System.out.print("Second");
                        System.out.print(arg.getSecondCommon());
                        System.out.println("Personal");
                        showItems(arg.getPersonal());
                        System.out.println("Shelf");
                        showItems(arg.getShelf());
                    }
                    if(arg.getConfirm()){
                        System.out.println("\n\nIt is your turn " + arg.getNickname() + "!!");
                        System.out.println("To select a tile you must enter the couple Row - Column, if you wanna deselect it you can do it during the next submission by typing the same couple Row - Column.");
                        System.out.println("Each time you choose a tile press enter key to submit.");
                        state = 4;
                    } else{
                        System.out.println("\n\nIt is " + arg.getNickname() + "'s turn, let's wait for your turn!!");
                        state = 20;
                    }
                    break;

                case SEND_MODEL:
                    arg.printMsg();

                    try {
                        String os = System.getProperty("os.name").toLowerCase();
                        Runtime runtime = Runtime.getRuntime();

                        if (os.contains("win")) {
                            runtime.exec("cmd /c start");
                        } else if (os.contains("nix") || os.contains("nux")) {
                            runtime.exec("gnome-terminal");
                        } else if (os.contains("mac")) {
                            runtime.exec("open -a Terminal");
                        } else {
                            System.out.println("Impossibile aprire il terminale. Sistema operativo non supportato.");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    System.out.println(RED + "\nThis is the board: " + RESET);
                    showBoard(arg.getBoard());
                    System.out.print(RED + "\nThis is the first common goal: " + RESET);
                    System.out.print(arg.getFirstCommon());
                    System.out.print(RED + "\nThis is the second common goal: " + RESET);
                    System.out.print(arg.getSecondCommon());
                    if (this.nickname.equals(arg.getNickname())) {
                        System.out.println(RED + "\nThis is your own personal goal: " + RESET);
                        showItems(arg.getPersonal());
                        System.out.println(RED + "\nThis is your own shelf: " + RESET);
                        showItems(arg.getShelf());
                        System.out.println("\n\nIt is your turn " + arg.getNickname() + "!!");
                        System.out.println("To select a tile you must enter the couple Row - Column, if you wanna deselect it you can do it during the next submission by typing the same couple Row - Column.");
                        System.out.println("Each time you choose a tile press enter key to submit.");
                        state = 4;
                    } else {
                        System.out.println("\n\nIt is " + arg.getNickname() + "'s turn, let's wait for your turn!!");
                        state = 20;
                    }
                    lock.notifyAll();
                    break;

                case SELECTED:
                    arg.printMsg();
                    if (arg.getNickname().equals(this.nickname)) {
                        System.out.println(RED + "\nIn the board, if you chose a valid item, it is highlight in red" + RESET);
                        showBoard(arg.getBoard());
                        System.out.print(RED + "\nHere you can see what you chose till now: " + RESET);
                        showItems(arg.getSelected());
                        if (arg.getConfirm()) {
                            System.out.println("\nIf you wanna confirm you selection enter \"YES\" otherwise \"NO\": -- (default \"NO\")");
                            String confirmation = terminal.next();
                            if (confirmation.equals("Y") || confirmation.equals("y") || confirmation.equals("yes") || confirmation.equals("YES") || confirmation.equals("Yes"))
                                state = 5;
                        } else
                            state = 4;
                    }
                    lock.notifyAll();
                    break;

                case ORDER_n_COLUMN:
                    arg.printMsg();
                    System.out.print(RED + "\nIt follows the current state of the board, it is without ");
                    if (arg.getNickname().equals(this.nickname)) System.out.println("your choice" + RESET);
                    else System.out.println(arg.getNickname() + "'s choice" + RESET);
                    showBoard(arg.getBoard());
                    if (arg.getNickname().equals(this.nickname)) {
                        System.out.println(RED + "\nThis is your own personal goal: " + RESET);
                        showItems(arg.getPersonal());
                        System.out.print(RED + "\nThis is your own shelf, you can put the tiles only in the columns where there is a red arrow\n " + RESET);
                        for (int i = 0; i < 5; i++) System.out.print(i + " ");
                        System.out.print("\n");
                        showItems(arg.getShelf());
                        showItems(arg.getColumns());
                        System.out.print(RED + "\n\nThese two are the lists of items you selected:" + RESET);
                        System.out.print("\n                   ");
                        for (int i = 0; i < 3; i++) System.out.print(i + " ");
                        System.out.print(RED + "\nUNORDERED ITEMS:" + RESET + " 1");
                        showItems(arg.getSelected());
                        System.out.print(RED + "\n  ORDERED ITEMS:" + RESET + " 0");
                        showItems(arg.getOrderedRanking());

                        System.out.println("\n\nNow you must select a column in the shelf and order the tile that will be inserted in it.");
                        System.out.println("Type \"C\" for choosing the column or type \"O\" for ordering the tiles: -- (default \"O\")");
                        state = 6;
                    }
                    break;

                case ACK_ORDER_n_COLUMN:
                    arg.printMsg();
                    if (arg.getNickname().equals(nickname)) {
                        System.out.println(RED + "\nThis is your own personal goal: " + RESET);
                        showItems(arg.getPersonal());
                        System.out.print(RED + "\nThis is your shelf and the columns you chose is highlighted with a red harrow.\n " + RESET);
                        for (int i = 0; i < 5; i++) System.out.print(i + " ");
                        System.out.print("\n");
                        showItems(arg.getShelf());
                        showItems(arg.getColumns());
                        System.out.print(RED + "\n\nThese two are the lists of items you selected:" + RESET);
                        System.out.print("\n                   ");
                        for (int i = 0; i < 3; i++) System.out.print(i + " ");
                        System.out.print(RED + "\nUNORDERED ITEMS:" + RESET + " 1");
                        showItems(arg.getSelected());
                        System.out.print(RED + "\n  ORDERED ITEMS:" + RESET + " 0");
                        showItems(arg.getOrderedRanking());
                        if (arg.getConfirm()) {
                            System.out.println("\nIf you wanna confirm you selection enter \"YES\" otherwise \"NO\": -- (default \"NO\")");
                            String insert = terminal.next();
                            if (insert.equals("Y") || insert.equals("y") || insert.equals("yes") || insert.equals("YES") || insert.equals("Yes"))
                                state = 7;
                        } else {
                            System.out.println("\nType \"C\" for choosing the column or type \"O\" for ordering the tiles: -- (default \"O\")");
                            state = 6;
                        }
                    }
                    break;

                case NACK_COLUMN:
                    arg.printMsg();
                    if (arg.getNickname().equals(nickname)) {
                        System.out.println("\nPlease enter a valid column number!");
                        System.out.println("\nFor changing column type \"C\" -- For ordering items type \"O\": ");
                        state = 6;
                    }
                    break;

                case NACK_ORDER:
                    arg.printMsg();
                    if (arg.getNickname().equals(nickname)) {
                        System.out.println("\nPlease enter a valid item number or select a non empty list!");
                        System.out.println("\nFor changing column type \"C\" -- For ordering items type \"O\": ");
                        state = 6;
                    }
                    break;

                case INSERTION_DONE:
                    arg.printMsg();
                    if (arg.getNickname().equals(nickname)) {
                        System.out.println("\nYour shelf now is:");
                        showItems(arg.getShelf());
                        System.out.println("\n\nYour turn is now finished!!");
                    }
                    state = 20;
                    break;

                case FIRSTCOMMONGOAL_TAKEN:
                    arg.printMsg();
                    if (arg.getNickname().equals(this.nickname)) {
                        System.out.print("You ");
                    } else {
                        System.out.print(arg.getNickname() + " ");
                    }
                    System.out.println("obtained: " + arg.getFirstCommon() + " points from the first common goal");
                    break;

                case SECONDCOMMONGOAL_TAKEN:
                    arg.printMsg();
                    if (arg.getNickname().equals(this.nickname)) {
                        System.out.print("You ");
                    } else {
                        System.out.print(arg.getNickname() + " ");
                    }
                    System.out.println(arg.getNickname() + "obtained: " + arg.getSecondCommon() + " points from the second common goal");
                    break;

                case TOKEN_END_GAME:
                    arg.printMsg();
                    if (arg.getNickname().equals(this.nickname)) {
                        System.out.print("You ");
                    } else {
                        System.out.print(arg.getNickname() + " ");
                    }
                    System.out.println(arg.getNickname() + "completed Shelf and obtained endGame's point");
                    break;

                case RESULTS:
                    arg.getInfo();
                    System.out.println(RED + arg.getOrderedRanking() + RESET);
                    state = 1;
                    lock.notifyAll();
                    break;

                default:
                    System.err.println("Ignoring event from " + msg + ": " + arg);
                    break;
            }
        }
    }

    /**
     * Renders correctly the board
     * @param board the encoded board
     * @see Board#toString()
     */
    private void showBoard(String board) {
        System.out.print("  ");
        for (int i = 0; i < 9; i++) System.out.print(i + "  ");
        System.out.print("\n");
        int i = 0;
        for (int j = 0; j < board.length(); j++) {
            if (j == i * 29 && i != 9) {
                System.out.print(i);
                i++;
            }
            switch (board.charAt(j)) {
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

 // TODO: not sure what is  this
    public void showItems(String items) {
        boolean columnChoosen = false;
        for (int i = 0; i < items.length(); i++) {
            if (items.charAt(i) == '#') {
                columnChoosen = true;
                break;
            }
        }

        for (int j = 0; j < items.length(); j++) {
            if (columnChoosen) {
                if (items.charAt(j) == '#') System.out.print(RED + '▲' + RESET);
                else System.out.print(BLACK + items.charAt(j) + RESET);
            } else {
                switch (items.charAt(j)) {
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
}
