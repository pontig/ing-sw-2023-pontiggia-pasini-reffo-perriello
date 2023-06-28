package it.polimi.ingsw.view;

import it.polimi.ingsw.enums.State;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.SendDataToServer;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.view.cliChat.ThreadRead;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static it.polimi.ingsw.enums.State.*;

/**
 * The CLI class represents the Command-Line Interface (CLI) view in the application
 * It extends the View class and provides methods for interacting with the user through the command line
 */
public class CLI extends View {
    // Reset
    public static final String RESET = "\033[0m";  // Text Reset
    // Regular Colors
    public static final String BLACK = "\033[38;5;238m";   // BLACK
    public static final String RED = "\033[38;5;196m";     // RED
    public static final String GREEN = "\033[38;5;46m";   // GREEN
    public static final String YELLOW = "\033[38;5;226m";  // YELLOW
    public static final String BLUE = "\033[38;5;21m";    // BLUE
    public static final String PURPLE = "\033[38;5;129m";  // PURPLE
    public static final String CYAN = "\033[38;5;51m";    // CYAN
    public static final String WHITE = "\033[38;5;231m";   // WHITE
    int state = 20;
    Scanner terminal = new Scanner(System.in);
    private final Object lock = new Object();
    private volatile boolean isRunning = true;
    Message msg = null;
    private String nickname;
    int networkProtocol;
    StringBuilder playerList = new StringBuilder("╬");
    boolean chatOpen = false;
    boolean waiting = false;
    boolean alreadyDone = false;
    boolean end = false;
    boolean canNotPlay = false;
    Socket socket = null;
    List<String> otherShelf = new ArrayList<>();
    ThreadRead userInput;
    int yourNumber = 0;

    /**
     * Constructs a new CLI view
     */
    public CLI() {
        super();
    }

    /**
     * Gets the nickname of the user
     *
     * @return the nickname
     */
    public String getNickname(){
        return this.nickname;
    }

    /**
     * Sets the network protocol
     *
     * @param networkClient the network protocol value
     */
    public void setNetwork(int networkClient) {
        this.networkProtocol = networkClient;
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
            //System.out.println("Stato: " + state);
            switch (state) {
                case 0:
                    //Form to enter a new nickname because someone already have the one expressed before
                    System.out.print("Someone has a nickname as the one you just wrote.\nPlease enter a different nickname: ");
                    nickname = terminal.next();
                    msg = new SendDataToServer(SET_NICKNAME, nickname, 0, 0, false);
                    setChangedView();
                    notifyObserversView(msg);
                    msg = null;
                    if (networkProtocol == 1)
                        state = 20;
                    break;
                case 1:
                    //All the players required are in game so no more are needed
                    if (!end) {
                        System.out.println("No more players are required in this game, we are sorry, you will be disconnected");
                        chatOpen = true;
                        canNotPlay = true;
                    } else {
                        System.out.println("Game ended, gooda bye");
                    }
                    stop();
                    break;
                case 2:
                    //Waiting for player and waiting
                    synchronized (lock) {
                        try {
                            if (!waiting) {
                                System.out.print("\n\n ~ WAITING FOR PLAYERS ~ \n\n");
                                waiting = true;
                            }
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

                    if(okNumber) {
                        msg = new SendDataToServer(SET_NUMPLAYERS, this.nickname, numberPlayers, 0, false);
                        setChangedView();
                        notifyObserversView(msg);
                        msg = null;
                        if (networkProtocol == 1)
                            state = 20;
                    }
                    break;
                case -1:
                    //Check if the game should begin
                    msg = new SendDataToServer(GAME_READY, null, 0, 0, false);
                    setChangedView();
                    notifyObserversView(msg);
                    msg = null;
                    if (networkProtocol == 1)
                        state = 20;
                    break;
                case 4:
                    //Selecting an items from board by typing its coordinated row first followed by the column
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
                    if (networkProtocol == 1)
                        state = 20;
                    break;

                case 5:
                    //Y = confirm the item selected from the board and then go to the insertion phase
                    msg = new SendDataToServer(CONFIRM_ITEMS, null, 0, 0, true);
                    setChangedView();
                    notifyObserversView(msg);
                    msg = null;
                    if (networkProtocol == 1)
                        state = 20;
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
                    if (networkProtocol == 1)
                        state = 20;
                    break;

                case 7:
                    //Confirm column and order
                    msg = new SendDataToServer(CONFIRM_INSERTION, null, 0, 0, true);
                    setChangedView();
                    notifyObserversView(msg);
                    msg = null;
                    if (networkProtocol == 1)
                        state = 20;
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
            if (socket != null){
                userInput = new ThreadRead(this.socket, this);
                userInput.start();
            }
        }
        System.exit(1);
    }

    /**
     * @see View#update(Server, Message)
     */
    public void update(Server o, @NotNull Message arg) {
        synchronized (lock) {
            State msg = arg.getInfo();
            switch (msg) {
                case RECONNECTED:
                case ACK_NICKNAME:
                case ACK_NUMPLAYERS:
                    //arg.printMsg();
                    state = 2;
                    lock.notifyAll();
                    break;

                case SAME_NICKNAME:
                    //arg.printMsg();
                    state = 0;
                    if(networkProtocol == 1)
                        lock.notifyAll();
                    break;

                case NOT_IN_PREV_GAME:
                case NACK_NICKNAME:
                    //arg.printMsg();
                    state = 1;
                    if(networkProtocol == 1)
                        lock.notifyAll();
                    break;

                case NACK_NUMPLAYERS:
                    //arg.printMsg();
                    if (arg.getNickname().equals(this.nickname)) {
                        System.out.println("Someone has already chosen the number of players, you will be added to that game");
                        state = -1;
                    }
                    if(networkProtocol == 1)
                        lock.notifyAll();
                    break;

                case ASK_NUMPLAYERS:
                case OUT_BOUND_NUMPLAYERS:
                    //arg.printMsg();
                    if (msg == OUT_BOUND_NUMPLAYERS && arg.getNickname().equals(this.nickname))
                        System.out.println("Enter a valid number of player!!");
                    state = 3;
                    lock.notifyAll();
                    break;

                case GAME_READY:
                case IN_GAME:
                    //arg.printMsg();
                    if(nickname.equals(arg.getNickname())){
                        System.out.println(RED + "\nThis is the board: " + RESET);
                        showBoard(arg.getBoard());
                        System.out.print(RED + "\nThis is the first common goal: " + RESET);
                        String[] splitGoal = arg.getFirstCommon().split("\n");
                        writeCommon(splitGoal[0]);
                        System.out.println(splitGoal[1]);
                        System.out.print(RED + "\nThis is the second common goal: " + RESET);
                        splitGoal = arg.getSecondCommon().split("\n");
                        writeCommon(splitGoal[0]);
                        System.out.println(splitGoal[1]);
                        System.out.println(RED + "\nYour shelf:             Your personal goal: " + RESET);
                        showPersonalAndShelf(arg.getPersonal(), arg.getShelf());

                        if(msg == IN_GAME){
                            System.out.println();
                            for(int i = 0; i*2 < otherShelf.size(); i++) {
                                System.out.print(RED + otherShelf.get(i * 2) + "'s shelf:" + RESET);
                                for(int j = 0; j < 15-otherShelf.get(i*2).length(); j++)
                                    System.out.print(" ");
                            }
                            System.out.println();
                            showOtherShelf(otherShelf);
                            otherShelf.clear();
                        }

                        if(!chatOpen){
                            String os = System.getProperty("os.name").toLowerCase();

                            String command;

                            if (os.contains("win")) {
                                //On Windows
                                command = "cmd /c start java -jar TerminalServer.jar";
                            } else if (os.contains("nix") || os.contains("nux")) {
                                //On Linux
                                //command = "x-terminal-emulator -e java -jar ./TerminalServer.jar";
                                command = "x-terminal-emulator -e java -jar /home/tommi/Scrivania/IngSW_Tommi/MyShelfie/TerminalServer.jar";

                            } else if (os.contains("mac")) {
                                //On macOS
                                command = "open -a Terminal.app java -jar ./TerminalServer.jar";
                            } else {
                                //Error
                                throw new UnsupportedOperationException("Sistema operativo non supportato");
                            }

                            try {
                                Process process = Runtime.getRuntime().exec(command);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                    if(networkProtocol == 1)
                        lock.notifyAll();
                    break;

                case PLAYER_LIST:
                    //arg.printMsg();
                    int j = 1;
                    for(int i = 1; i < 5; i++){
                        switch(i) {
                            case 1:
                                if (arg.getNickname() != null && !arg.getNickname().equals(nickname)){
                                    playerList.append(j).append(" - ").append(arg.getNickname()).append(" - ");
                                    j++;
                                }
                                if(arg.getNickname() != null && arg.getNickname().equals(nickname))
                                    this.yourNumber = i;
                                break;

                            case 2:
                                if(arg.getBoard() != null && !arg.getBoard().equals(nickname)){
                                    playerList.append(j).append(" - ").append(arg.getBoard()).append(" - ");
                                    j++;
                                }
                                if(arg.getBoard() != null && arg.getBoard().equals(nickname))
                                    this.yourNumber = i;
                                break;

                            case 3:
                                if(arg.getPersonal() != null && !arg.getPersonal().equals(nickname)){
                                    playerList.append(j).append(" - ").append(arg.getPersonal()).append(" - ");
                                    j++;
                                }
                                if(arg.getPersonal() != null && arg.getPersonal().equals(nickname))
                                    this.yourNumber = i;
                                break;
                            case 4:
                                if(arg.getShelf() != null && !arg.getShelf().equals(nickname)){
                                    playerList.append(j).append(" - ").append(arg.getShelf()).append(" - ");
                                    j++;
                                }
                                if(arg.getShelf() != null && arg.getShelf().equals(nickname))
                                    this.yourNumber = i;
                                break;

                            default:
                                System.out.println(RED + "Error" + RESET);
                                break;
                        }
                    }
                    playerList.append("\n");
                    break;

                case SEND_MODEL:
                    //arg.printMsg();
                    if(!chatOpen) {
                        try {
                            int port = -1;

                            if(networkProtocol == 1) {
                                int timeGUI = 8000*yourNumber;
                                Thread.sleep(timeGUI);
                            } else {
                                int timeCLI = 2000 * yourNumber;
                                Thread.sleep(timeCLI);
                            }

                            /*int timeCLI = 2000 * yourNumber;
                            Thread.sleep(timeCLI);*/

                            try (BufferedReader reader = new BufferedReader(new FileReader("port.txt"))) {
                                String line;

                                do {
                                    line = reader.readLine();
                                } while (line != null && line.equals("-"));

                                if(line != null)
                                    port = Integer.parseInt(line);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            //Thread.sleep(2000);

                            System.out.print(RED + "\nConnected to server chat at port: " + port + RESET);
                            this.socket = new Socket("localhost", port);
                            String players = playerList.toString();                                          //Send the list of players to the chat, so it can show it

                            //Thread.sleep(2000);

                            socket.getOutputStream().write(players.getBytes());
                            socket.getOutputStream().flush();

                            userInput = new ThreadRead(this.socket, this);
                            userInput.start();

                            chatOpen = true;
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    if(!canNotPlay) {
                        if (!nickname.equals(arg.getNickname()) && !alreadyDone) {
                            System.out.println("\n\nIt is " + arg.getNickname() + "'s turn, let's wait for your turn!!");
                            state = 20;
                            alreadyDone = true;
                        } else if (nickname.equals(arg.getNickname()) && !alreadyDone) {
                            System.out.println("\n\nIt is your turn " + arg.getNickname() + "!!");
                            System.out.println("To select a tile you must enter the couple Row - Column, if you wanna deselect it you can do it during the next submission by typing the same couple Row - Column.");
                            System.out.println("Each time you choose a tile press enter key to submit.");
                            state = 4;
                            alreadyDone = true;
                        }
                        lock.notifyAll();
                    }
                    break;

                case SELECTED:
                    //arg.printMsg();
                    if (arg.getNickname().equals(this.nickname)) {
                        System.out.println(RED + "\nIn the board, if you chose a valid item, it is highlight in red" + RESET);
                        showBoard(arg.getBoard());
                        System.out.print(RED + "\nHere you can see what you chose till now: " + RESET);
                        showItems(arg.getSelected());
                        if (arg.getConfirm()) {
                            System.out.println("\nIf you wanna confirm you selection enter \"YES\" otherwise \"NO\": -- (default \"NO\")");
                            String confirmation = terminal.next();
                            if (confirmation.equals("Y") || confirmation.equals("y") || confirmation.equals("yes") || confirmation.equals("YES") || confirmation.equals("Yes")){
                                state = 5;
                                if(networkProtocol == 1)
                                    lock.notifyAll();
                                break;
                            }
                        }
                        state = 4;
                    }
                    lock.notifyAll();
                    break;

                case ORDER_n_COLUMN:
                    //arg.printMsg();
                    if(!canNotPlay) {
                        System.out.print(RED + "\nIt follows the current state of the board, it is without ");
                        if (arg.getNickname().equals(this.nickname)) System.out.println("your choice" + RESET);
                        else System.out.println(arg.getNickname() + "'s choice" + RESET);
                        showBoard(arg.getBoard());
                        if (arg.getNickname().equals(this.nickname)) {
                            System.out.print(RED + "\nYour shelf:             Your personal goal: \n " + RESET);
                            for (int i = 0; i < 5; i++) System.out.print(i + " ");
                            System.out.print("\n");
                            showPersonalAndShelf(arg.getPersonal(), arg.getShelf());
                            showItems(arg.getColumns());
                            System.out.print(RED + "\nYou can put tiles only in the columns where there is a red arrow" + RESET);

                            System.out.print(RED + "\n\nThese are the lists of items you selected:" + RESET);
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
                        if (networkProtocol == 1)
                            lock.notifyAll();
                    }
                    break;

                case ACK_ORDER_n_COLUMN:
                    //arg.printMsg();
                    if (arg.getNickname().equals(nickname)) {
                        System.out.print(RED + "\nYour shelf:             Your personal goal: \n " + RESET);
                        for (int i = 0; i < 5; i++) System.out.print(i + " ");
                        System.out.print("\n");
                        showPersonalAndShelf(arg.getPersonal(), arg.getShelf());
                        showItems(arg.getColumns());
                        System.out.print(RED + "\nYou can put tiles only in the columns where there is a red arrow" + RESET);

                        System.out.print(RED + "\n\nThese are the lists of items you selected:" + RESET);
                        System.out.print("\n                   ");
                        for (int i = 0; i < 3; i++) System.out.print(i + " ");
                        System.out.print(RED + "\nUNORDERED ITEMS:" + RESET + " 1");
                        showItems(arg.getSelected());
                        System.out.print(RED + "\n  ORDERED ITEMS:" + RESET + " 0");
                        showItems(arg.getOrderedRanking());
                        if (arg.getConfirm()) {
                            System.out.println("\nIf you wanna confirm you selection enter \"YES\" otherwise \"NO\": -- (default \"NO\")");
                            String insert = terminal.next();
                            if (insert.equals("Y") || insert.equals("y") || insert.equals("yes") || insert.equals("YES") || insert.equals("Yes")){
                                state = 7;
                                if(networkProtocol == 1)
                                    lock.notifyAll();
                                break;
                            }
                        }
                        System.out.println("\nType \"C\" for choosing the column or type \"O\" for ordering the tiles: -- (default \"O\")");
                        state = 6;
                    }
                    if(networkProtocol == 1)
                        lock.notifyAll();
                    break;

                case NACK_COLUMN:
                    //arg.printMsg();
                    if (arg.getNickname().equals(nickname)) {
                        System.out.println("\nPlease enter a valid column number!");
                        System.out.println("\nFor changing column type \"C\" -- For ordering items type \"O\": ");
                        state = 6;
                    }
                    if(networkProtocol == 1)
                        lock.notifyAll();
                    break;

                case NACK_ORDER:
                    //arg.printMsg();
                    if (arg.getNickname().equals(nickname)) {
                        System.out.println("\nPlease enter a valid item number or select a non empty list!");
                        System.out.println("\nFor changing column type \"C\" -- For ordering items type \"O\": ");
                        state = 6;
                    }
                    if(networkProtocol == 1)
                        lock.notifyAll();
                    break;

                case INSERTION_DONE:
                    //arg.printMsg();
                    if (arg.getNickname().equals(nickname)) {
                        System.out.println("\nYour shelf now is:");
                        showItems(arg.getShelf());
                        System.out.println("\n\nYour turn is now finished!!");
                    }
                    state = 20;
                    if(networkProtocol == 1)
                        lock.notifyAll();
                    break;

                case SEND_OTHER_SHELF:
                    //arg.printMsg();
                    if(!arg.getNickname().equals(nickname)){
                        otherShelf.add(arg.getNickname());
                        otherShelf.add(arg.getShelf());
                        alreadyDone = false;
                    }
                    break;

                case FIRSTCOMMONGOAL_TAKEN:
                    //arg.printMsg();
                    if (arg.getNickname().equals(this.nickname)) {
                        System.out.print(RED + "\nYou");
                    } else {
                        System.out.print(RED + "\n" + arg.getNickname());
                    }
                    System.out.println("obtained: " + arg.getFirstCommon() + " points from the first common goal" + RESET);
                    if(networkProtocol == 1)
                        lock.notifyAll();
                    break;

                case SECONDCOMMONGOAL_TAKEN:
                    //arg.printMsg();
                    if (arg.getNickname().equals(this.nickname)) {
                        System.out.print(RED + "\nYou");
                    } else {
                        System.out.print(RED + "\n" + arg.getNickname());
                    }
                    System.out.println(" obtained: " + arg.getSecondCommon() + " points from the second common goal" + RESET);
                    if(networkProtocol == 1)
                        lock.notifyAll();
                    break;

                case TOKEN_END_GAME:
                    //arg.printMsg();
                    if (arg.getNickname().equals(this.nickname)) {
                        System.out.print(RED + "\nYou");
                    } else {
                        System.out.print(RED + "\n" + arg.getNickname());
                    }
                    System.out.println("completed Shelf and obtained endGame's point!" + RESET);
                    if(networkProtocol == 1)
                        lock.notifyAll();
                    break;

                case RESULTS:
                    //arg.printMsg();
                    System.out.println(RED + arg.getOrderedRanking() + RESET);
                    end = true;
                    state = 1;
                    lock.notifyAll();
                    break;

                case CHAT_MESSAGE:
                    //arg.printMsg();
                    try {
                        OutputStream outputStream = socket.getOutputStream();
                        StringBuilder message = new StringBuilder("");
                        String chatMsg;
                        if(!arg.getFrom().equals(nickname)) {
                            if (arg.getTo() != null && arg.getTo().equals(nickname))
                                message.append("P-").append(arg.getFrom()).append("-").append(arg.getText()).append("-P");
                            else if(arg.getTo() == null)
                                message.append("A-").append(arg.getFrom()).append("-").append(arg.getText()).append("-A");
                            else
                                break;
                            chatMsg = message.toString();
                            outputStream.write((chatMsg + "\n").getBytes());
                            outputStream.flush();
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case TOKENS_TAKEN:
                case PING:
                    //arg.printMsg();
                    break;

                case TOO_MANY:
                    //arg.printMsg();
                    System.out.println("\n\n Too many players are trying to access this game, you will be disconnected \n\n");
                    System.exit(1);
                    break;

                default:
                    System.err.println("Ignoring event from " + msg + ": " + arg);
                    break;
            }
        }
    }

    /**
     * Renders correctly the board
     *
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

    /**
     * Displays the other players' shelves
     *
     * @param otherShelf the list of strings representing the other players' shelves
     */
    private void showOtherShelf(List<String> otherShelf){
        int numShelf = otherShelf.size() / 2;
        switch(numShelf){
            case 1:
                showItems(otherShelf.get(1));
                break;

            case 2:
                for(int i = 0; i < otherShelf.get(1).length(); i++){
                    if(i == 6)
                        break;
                    showItems(otherShelf.get(1).split("\n")[i]);
                    System.out.print("             ");
                    showItems(otherShelf.get(3).split("\n")[i]);
                    System.out.print("\n");
                }
                break;

            case 3:
                for(int i = 0; i < otherShelf.get(1).length(); i++){
                    if(i == 6)
                        break;
                    showItems(otherShelf.get(1).split("\n")[i]);
                    System.out.print("             ");
                    showItems(otherShelf.get(3).split("\n")[i]);
                    System.out.print("             ");
                    showItems(otherShelf.get(5).split("\n")[i]);
                    System.out.print("\n");
                }
                break;

            default:
                System.out.println("Error showing others shelf");
                break;
        }
    }

    /**
     * Writes a description of a common goal based on the provided goal identifier
     *
     * @param commonGoal the identifier of the common goal
     */
    private void writeCommon(String commonGoal){
        //System.out.println(commonGoal);
        switch (commonGoal) {
            case "FIVEX":
                System.out.println("Five tiles of the same type forming an X");
                break;

            case "FOURANGLES":
                System.out.println("Four tiles of the same type in the four corners of the bookshelf");
                break;

            case "SIXCOUPLES":
                System.out.println("Six groups each containing at least 2 tiles of the same type\n" +
                        "                               The tiles of one group can be different from those of another group");
                break;

            case "FIVEDIAGONAL":
                System.out.println("Five tiles of the same type forming a diagonal");
                break;

            case "SQUARE2X2":
                System.out.println("Two groups each containing 4 tiles of the same type in a 2x2 square\n" +
                        "                               The tiles of one square has to be the same from those of the other square.");
                break;

            case "FOURADJACENT":
                System.out.println("Four groups each containing at least 4 tiles of the same type\n" +
                        "                               The tiles of one group can be different from those of another group.");
                break;

            case "EIGHTSAMETYPE":
                System.out.println("Eight tiles of the same type");
                break;

            case "FIVEDECRESING":
                System.out.println("Five columns of increasing or decreasing height: starting with the first column to the left or right,\n" +
                        "                               each subsequent column must consist of one more tile. Tiles can be of any type.");
                break;

            case "ROW4ITEMS5":
                System.out.println("Four lines each formed by 5 tiles of maximum three different types.\n" +
                        "                               One line can show the same or a different combination of another line");
                break;

            case "COLUMNS3ITEMS6":
                System.out.println("Three columns each formed by 6 tiles of maximum three different types.\n" +
                        "                               One column can show the same or a different combination of another column");
                break;

            case "ROW2ITEMS5DIFFERENT":
                System.out.println("Two lines each formed by 5 different types of tiles.\n" +
                        "                               One line can show the same or a different combination of the other line");
                break;

            case "COLUMNS2ITEMS6DIFFERENT":
                System.out.println("Two columns each formed by 6 different types of tiles");
                break;
        }
    }

    /**
     * Displays the personal goals and the player's own shelf in a formatted manner
     *
     * @param personalGoals the string representing the personal goals
     * @param ownShelf the string representing the player's own shelf
     */
    private void showPersonalAndShelf(String personalGoals, String ownShelf){
        String[] personal = personalGoals.split("\n");
        String[] shelf = ownShelf.split("\n");

        for(int i = 0; i < shelf.length; i++){
            showItems(shelf[i]);
            System.out.print("             ");
            showItems(personal[i]);
            System.out.print("\n");
        }
    }

    /**
     * Displays the items in the specified format
     *
     * @param items the items to display
     */
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
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                    case '9':
                    case '~':
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
