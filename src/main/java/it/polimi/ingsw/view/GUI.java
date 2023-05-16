package it.polimi.ingsw.view;

import it.polimi.ingsw.enums.State;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.SendDataToServer;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.view.gui.SceneController;
import it.polimi.ingsw.view.gui.scene.NicknameSceneController;
import it.polimi.ingsw.view.gui.scene.PlaySceneController;
import javafx.application.Platform;

import java.util.Scanner;

import static it.polimi.ingsw.enums.State.GAME_READY;
import static it.polimi.ingsw.enums.State.OUT_BOUND_NUMPLAYERS;

public class GUI  extends View {

    int state = 0;
    Scanner terminal = new Scanner(System.in);
    private final Object lock = new Object();
    private volatile boolean isRunning = true;
    Message msg = null;
    String nickname;

    public GUI() {
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

        Platform.runLater(() -> {
            SceneController.changeRootPane(getObservers(), "LoginScene.fxml");
        });

        while (isRunning) {
            switch (state) {
                //Form to enter a new nickname because someone already have the one expressed before
                case 0:
                    SceneController.showMessage("Someone has a nickname as the one you just wrote.\nPlease enter a different nickname");
                    NicknameSceneController controller = (NicknameSceneController) SceneController.getActiveController();
                    controller.setError();
                    break;
                //All the players required are in game so no more are needed
                case 1:
                    SceneController.showMessage("No more players are required in this game, we are sorry, you will be disconnected");
                    stop();
                    break;
                //Waiting for player and waiting
                case 2:
                    synchronized (lock) {
                        try {
                            SceneController.changeRootPane(getObservers(), "WaitingScene.fxml");
                            lock.wait();        //possibile lock per tutti i giocatori -> da inviare una richiesta al server se i due numeri sono uguali
                            msg = null;
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                //Insert the number of player for the game 2 / 3 / 4
                case 3:
                    SceneController.askForNumPlayer();

                    /*int numberPlayers = 0;
                    boolean okNumber = false;
                    System.out.print("Insert the number of players for the game (2 / 3 / 4): ");
                    do {
                        try {
                            numberPlayers = Integer.parseInt(terminal.next());
                            okNumber = true;
                        } catch (NumberFormatException e) {
                            System.out.print("You entered an invalid character, please enter a number (2 / 3 / 4): ");
                        }
                    } while (!okNumber);
                    msg = new SendDataToServer(SET_NUMPLAYERS, this.nickname, numberPlayers, 0, false);
                    setChangedView();
                    notifyObserversView(msg);
                    msg = null;
                    break;*/
                    //Check if the game should begin
                case -1:
                    // TODO: can't understand why this is here, i'll ask later
                    msg = new SendDataToServer(GAME_READY, null, 0, 0, false);
                    setChangedView();
                    notifyObserversView(msg);
                    msg = null;
                    //state = 2;
                    break;

                case 4:
                    PlaySceneController controller1 = (PlaySceneController) SceneController.getActiveController();
                    controller1.letSelectItemsOnBoard();
                    break;
                //Y = confirm the item selected from the board and then go to the insertion phase
                case 5:
                    // In the gui it should not be necessary this case
                    /*msg = new SendDataToServer(CONFIRM_ITEMS, null, 0, 0, true);
                    setChangedView();
                    notifyObserversView(msg);
                    msg = null;
                    break;*/

                case 6:
                    PlaySceneController controller2 = (PlaySceneController) SceneController.getActiveController();
                    // TODO: if it is called two times, it could reset everything
                    controller2.letOrderAndInsert();
                    //Confirm column and order
                case 7:
                    PlaySceneController controller3 = (PlaySceneController) SceneController.getActiveController();
                    controller3.letConfirm();
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

    public void update(Server o, Message arg) {
        synchronized (lock) {
            State msg = arg.getInfo();
            switch (msg) {
                case ACK_NICKNAME:
                case ACK_NUMPLAYERS:
                    SceneController.showMessage(msg.toString());
                    state = 2;
                    lock.notifyAll();
                    break;

                case SAME_NICKNAME:
                    SceneController.showMessage(msg.toString());
                    state = 0;
                    break;

                case NACK_NICKNAME:
                    SceneController.showMessage(msg.toString());
                    state = 1;
                    break;

                case NACK_NUMPLAYERS:
                    if (arg.getNickname().equals(this.nickname))
                        SceneController.showMessage(msg + " Someone has already chosen the number of players, you will be added to that game");
                    else
                        SceneController.showMessage(msg.toString());
                    state = -1;
                    break;

                case ASK_NUMPLAYERS:
                case OUT_BOUND_NUMPLAYERS:
                    if (msg == OUT_BOUND_NUMPLAYERS && arg.getNickname().equals(this.nickname))
                        System.out.println(msg + " Enter a valid number of player!!");
                    else
                        SceneController.showMessage(msg.toString());
                    state = 3;
                    break;

                case SEND_MODEL:
                    if (this.nickname.equals(arg.getNickname())) {
                        PlaySceneController controller = (PlaySceneController) SceneController.getActiveController();
                        controller.updateModel(this.nickname, arg);
                        controller.letSelectItemsOnBoard();
                        state = 4;
                    } else {
                        // TODO say to the player that it is not his turn
                        state = 20;
                    }
                    lock.notifyAll();
                    break;
                    /*arg.printMsg();
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
                    break;*/

                case SELECTED:
                    if (arg.getNickname().equals(this.nickname)) {
                        PlaySceneController controller = (PlaySceneController) SceneController.getActiveController();
                        controller.updateModel(this.nickname, arg);
                    }
                    /*arg.printMsg();
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
                        } else state = 4;
                    }
                    lock.notifyAll();
                    break;*/

                case ORDER_n_COLUMN:
                    /*arg.printMsg();
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
                    break;*/

                case ACK_ORDER_n_COLUMN:
                    /*arg.printMsg();
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
                    break;*/

                case NACK_COLUMN:
                    /*arg.printMsg();
                    if (arg.getNickname().equals(nickname)) {
                        System.out.println("\nPlease enter a valid column number!");
                        System.out.println("\nFor changing column type \"C\" -- For ordering items type \"O\": ");
                        state = 6;
                    }
                    break;*/

                case NACK_ORDER:
                    /*arg.printMsg();
                    if (arg.getNickname().equals(nickname)) {
                        System.out.println("\nPlease enter a valid item number or select a non empty list!");
                        System.out.println("\nFor changing column type \"C\" -- For ordering items type \"O\": ");
                        state = 6;
                    }
                    break;*/

                case INSERTION_DONE:
                    /*arg.printMsg();
                    if (arg.getNickname().equals(nickname)) {
                        System.out.println("\nYour shelf now is:");
                        showItems(arg.getShelf());
                        System.out.println("\n\nYour turn is now finished!!");
                    }
                    state = 20;
                    break;*/
                case FIRSTCOMMONGOAL_TAKEN:      //avviso che un giocatore ha preso un obiettivo comune
                    /*arg.printMsg();
                    if (arg.getNickname().equals(this.nickname)) {
                        System.out.print("You ");
                    } else {
                        System.out.print(arg.getNickname() + " ");
                    }
                    System.out.println("obtained: " + arg.getFirstCommon() + " points from the first common goal");
                    break;*/
                case SECONDCOMMONGOAL_TAKEN:
                    /*arg.printMsg();
                    if (arg.getNickname().equals(this.nickname)) {
                        System.out.print("You ");
                    } else {
                        System.out.print(arg.getNickname() + " ");
                    }
                    System.out.println(arg.getNickname() + "obtained: " + arg.getSecondCommon() + " points from the second common goal");
                    break;*/
                case TOKEN_END_GAME:    //avviso che il giocatore corrente ha preso il token di fine gioco
                    /*arg.printMsg();
                    if (arg.getNickname().equals(this.nickname)) {
                        System.out.print("You ");
                    } else {
                        System.out.print(arg.getNickname() + " ");
                    }
                    System.out.println(arg.getNickname() + "completed Shelf and obtained endGame's point");
                    break;*/
                case RESULTS:
                    /*arg.getInfo();
                    System.out.println(RED + arg.getOrderedRanking() + RESET);
                    state = 1;
                    lock.notifyAll();
                    break;*/
                default:
                    System.err.println("Ignoring event from " + msg + ": " + arg);
                    break;
            }
        }
    }

   /* private void showBoard(String board) {
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
                    System.out.print(WHITE + "██" + RESET);
                    break;
                case 'G':
                    System.out.print(GREEN + "██" + RESET);
                    break;
                case 'B':
                    System.out.print(BLUE + "██" + RESET);
                    break;
                case 'Y':
                    System.out.print(YELLOW + "██" + RESET);
                    break;
                case 'P':
                    System.out.print(PURPLE + "██" + RESET);
                    break;
                case 'L':
                    System.out.print(CYAN + "██" + RESET);
                    break;
                case '#':
                    System.out.print(RED + "██" + RESET);
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
    }*/

    /*public void showItems(String items) {
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
    }*/
}
