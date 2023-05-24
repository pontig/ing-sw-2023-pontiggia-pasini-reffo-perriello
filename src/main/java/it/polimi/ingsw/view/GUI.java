package it.polimi.ingsw.view;

import it.polimi.ingsw.enums.State;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.SendDataToServer;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.view.gui.JavaFXGui;
import it.polimi.ingsw.view.gui.SceneController;
import it.polimi.ingsw.view.gui.scene.AskNumPlayersSceneController;
import it.polimi.ingsw.view.gui.scene.PlaySceneController;
import javafx.application.Application;
import javafx.application.Platform;

import java.util.Scanner;

import static it.polimi.ingsw.enums.State.GAME_READY;
import static it.polimi.ingsw.enums.State.OUT_BOUND_NUMPLAYERS;

public class GUI extends View {

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

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public void run() {

        JavaFXGui.setCustomClassInstance(this);
        Application.launch(JavaFXGui.class);

        //Platform.runLater(() -> {
        //  SceneController.changeRootPane(getObservers(), "LoginScene.fxml");
        //});

        // Could be not necessary?
        //while (isRunning) {
        //    switch (state) {
        //        case 0:
        //            //Form to enter a new nickname because someone already have the one expressed before
        //            SceneController.showMessage("Someone has a nickname as the one you just wrote.\nPlease enter a different nickname");
        //            NicknameSceneController controller = (NicknameSceneController) SceneController.getActiveController();
        //            controller.setError();
        //            break;
        //        case 1:
        //            //All the players required are in game so no more are needed
        //            SceneController.showMessage("No more players are required in this game, we are sorry, you will be disconnected");
        //            stop();
        //            break;
        //        case 2:
        //            //Waiting for player and waiting
        //            synchronized (lock) {
        //                try {
        //                    SceneController.changeRootPane(getObservers(), "WaitingScene.fxml");
        //                    lock.wait();        //possibile lock per tutti i giocatori -> da inviare una richiesta al server se i due numeri sono uguali
        //                    msg = null;
        //                } catch (InterruptedException e) {
        //                    throw new RuntimeException(e);
        //                }
        //            }
        //            break;
        //        //Insert the number of player for the game 2 / 3 / 4
        //        case 3:
        //            SceneController.askForNumPlayer();
//
        //            /*int numberPlayers = 0;
        //            boolean okNumber = false;
        //            System.out.print("Insert the number of players for the game (2 / 3 / 4): ");
        //            do {
        //                try {
        //                    numberPlayers = Integer.parseInt(terminal.next());
        //                    okNumber = true;
        //                } catch (NumberFormatException e) {
        //                    System.out.print("You entered an invalid character, please enter a number (2 / 3 / 4): ");
        //                }
        //            } while (!okNumber);
        //            msg = new SendDataToServer(SET_NUMPLAYERS, this.nickname, numberPlayers, 0, false);
        //            setChangedView();
        //            notifyObserversView(msg);
        //            msg = null;
        //            break;*/
        //        case -1:
        //            //Check if the game should begin
        //            msg = new SendDataToServer(GAME_READY, null, 0, 0, false);
        //            setChangedView();
        //            notifyObserversView(msg);
        //            msg = null;
        //            //state = 2;
        //            break;
//
        //        case 4:
        //            PlaySceneController controller1 = (PlaySceneController) SceneController.getActiveController();
        //            controller1.letSelectItemsOnBoard();
        //            break;
        //        //Y = confirm the item selected from the board and then go to the insertion phase
        //        case 5:
        //            // In the gui it should not be necessary this case
        //            /*msg = new SendDataToServer(CONFIRM_ITEMS, null, 0, 0, true);
        //            setChangedView();
        //            notifyObserversView(msg);
        //            msg = null;
        //            break;*/
//
        //        case 6:
        //            PlaySceneController controller2 = (PlaySceneController) SceneController.getActiveController();
        //            // TODO: if it is called two times, it could reset everything
        //            controller2.letOrderAndInsert();
        //            //Confirm column and order
        //        case 7:
        //            PlaySceneController controller3 = (PlaySceneController) SceneController.getActiveController();
        //            controller3.letConfirm();
        //            break;
//
        //        case 20:
        //            synchronized (lock) {
        //                try {
        //                    lock.wait();
        //                    msg = null;
        //                } catch (InterruptedException e) {
        //                    throw new RuntimeException(e);
        //                }
        //            }
        //            break;
//
        //        default:
        //            System.out.println("Error");
        //            break;
        //    }
//
        //}
    }

    public void update(Server o, Message arg) {
        synchronized (lock) {
            State msg = arg.getInfo();
            switch (msg) {
                case ACK_NICKNAME:
                case ACK_NUMPLAYERS:
                    if (this.nickname == null || !this.nickname.equals(arg.getNickname())) break;
                    Platform.runLater(() -> {
                        SceneController.changeRootPane(getObservers(), "WaitingScene.fxml");
                    });
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
                    if (arg.getNickname().equals(this.nickname)) {
                        Platform.runLater(() -> {
                            SceneController.changeRootPane(getObservers(), "WaitingScene.fxml");
                            Message check = new SendDataToServer(GAME_READY, null, 0, 0, false);
                            setChangedView();
                            notifyObserversView(check);
                            SceneController.showMessage(msg + " Someone has already chosen the number of players, you will be added to that game");
                            state = -1;
                        });
                    }
                    break;

                case GAME_READY:

                    Platform.runLater(() -> {
                        SceneController.changeRootPane(getObservers(), "PlayScene.fxml");

                        state = 4;
                    });
                    Platform.runLater(() -> {
                        // TODO: confirm a true se è il primo giocatore
                        if (arg.getNickname().equals(this.nickname)) {
                            PlaySceneController controller = (PlaySceneController) SceneController.getActiveController();
                            controller.assignPersonalGoal(arg);
                        }
                    });
                    break;

                case ASK_NUMPLAYERS:
                    if (this.nickname == null) break;
                case OUT_BOUND_NUMPLAYERS: // should not be necessary in the gui
                    if (msg == OUT_BOUND_NUMPLAYERS && arg.getNickname().equals(this.nickname))
                        System.out.println(msg + " Enter a valid number of player!!");
                    else
                        Platform.runLater(() -> {
                            SceneController.changeRootPane(getObservers(), "AskNumPlayersScene.fxml");
                            AskNumPlayersSceneController controller = (AskNumPlayersSceneController) SceneController.getActiveController();
                            controller.setNickname(this.nickname);

                        });
                    state = 3;
                    break;

                case SEND_MODEL:
                    if (this.nickname.equals(arg.getNickname())) {
                        Platform.runLater(() -> {

                            PlaySceneController controller = (PlaySceneController) SceneController.getActiveController();
                            controller.updateModel(this.nickname, arg);
                            controller.letSelectItemsOnBoard();
                            if (arg.getPersonal() != null) {
                                controller.assignPersonalGoal(arg);
                            }
                        });
                        state = 4;
                    } else {
                        // TODO say to the player that it is not his turn
                        Platform.runLater(() -> {
                            PlaySceneController controller = (PlaySceneController) SceneController.getActiveController();
                            controller.gameJustStarted(this.nickname, arg);
                        });
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
                        // La tessera sulla board è stata selezionata correttamente
                        PlaySceneController controller = (PlaySceneController) SceneController.getActiveController();
                        controller.updateModel(this.nickname, arg);
                        if (arg.getConfirm()) {
                            controller.letConfirm();
                        } else {
                            controller.dontLetConfirm();
                        }
                    }
                    break;
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
                    // Manda il personal goal, la shelf con le colonne disponibili
                    if (arg.getNickname().equals(this.nickname)) {
                        Platform.runLater(() -> {
                            PlaySceneController controller = (PlaySceneController) SceneController.getActiveController();
                            controller.letOrderAndInsert(arg.getSelected(), arg.getOrderedRanking());
                            controller.disableOthers(arg.getColumns());
                        });
                        state = 6;
                    }
                    break;

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
                    if (arg.getNickname().equals(nickname)) {
                        Platform.runLater(() -> {
                                PlaySceneController controller = (PlaySceneController) SceneController.getActiveController();
                            if (arg.getConfirm()) {
                                controller.letInsert();
                            } else {
                                controller.dontLetInsert();
                            }
                            if (arg.getColumns().contains("#")) {
                                controller.enlightColumn((arg.getColumns().indexOf("#")-1)/2);
                            }
                        });
                    }
                    break;

                // Immesso correttamente il valore di colonna / ordinato correttamente una delle tiles
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
                    break;
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
                    if (arg.getNickname().equals(nickname)) {
                        Platform.runLater(() -> {
                            PlaySceneController controller = (PlaySceneController) SceneController.getActiveController();
                            controller.updateModel(nickname, arg);
                            controller.endTurn();
                        });
                    }
                    break;

                    /*arg.printMsg();
                    if (arg.getNickname().equals(nickname)) {
                        System.out.println("\nYour shelf now is:");
                        showItems(arg.getShelf());
                        System.out.println("\n\nYour turn is now finished!!");
                    }
                    state = 20;
                    break;*/
                case FIRSTCOMMONGOAL_TAKEN:      //avviso che un giocatore ha preso un obiettivo comune
                    if (arg.getNickname().equals(this.nickname)) {
                        Platform.runLater(() -> {
                            PlaySceneController controller = (PlaySceneController) SceneController.getActiveController();
                            controller.setCommon(0, Integer.parseInt(arg.getFirstCommon()));
                        });
                    }
                    break;

                    /*arg.printMsg();
                    if (arg.getNickname().equals(this.nickname)) {
                        System.out.print("You ");
                    } else {
                        System.out.print(arg.getNickname() + " ");
                    }
                    System.out.println("obtained: " + arg.getFirstCommon() + " points from the first common goal");
                    break;*/
                case SECONDCOMMONGOAL_TAKEN:
                    if (arg.getNickname().equals(this.nickname)) {
                        Platform.runLater(() -> {
                            PlaySceneController controller = (PlaySceneController) SceneController.getActiveController();
                            controller.setCommon(1, Integer.parseInt(arg.getSecondCommon()));
                        });
                    }
                    break;
                    /*arg.printMsg();
                    if (arg.getNickname().equals(this.nickname)) {
                        System.out.print("You ");
                    } else {
                        System.out.print(arg.getNickname() + " ");
                    }
                    System.out.println(arg.getNickname() + "obtained: " + arg.getSecondCommon() + " points from the second common goal");
                    break;*/
                case TOKEN_END_GAME:    //avviso che il giocatore corrente ha preso il token di fine gioco
                    if (arg.getNickname().equals(this.nickname)) {
                        Platform.runLater(() -> {
                            PlaySceneController controller = (PlaySceneController) SceneController.getActiveController();
                            controller.setCommon(2, 1);
                        });
                    }
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
}
