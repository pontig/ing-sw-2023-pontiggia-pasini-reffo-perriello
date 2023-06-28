package it.polimi.ingsw.view;

import it.polimi.ingsw.enums.State;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.SendChatMessage;
import it.polimi.ingsw.network.messages.SendDataToServer;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.view.gui.JavaFXGui;
import it.polimi.ingsw.view.gui.SceneController;
import it.polimi.ingsw.view.gui.scene.AskNumPlayersSceneController;
import it.polimi.ingsw.view.gui.scene.EndGameSceneController;
import it.polimi.ingsw.view.gui.scene.NicknameSceneController;
import it.polimi.ingsw.view.gui.scene.PlaySceneController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static it.polimi.ingsw.enums.State.GAME_READY;

/**
 * This class is the GUI view of the MVC pattern
 */
public class GUI extends View {

    int state = 0;
    Scanner terminal = new Scanner(System.in);
    private final Object lock = new Object();
    private volatile boolean isRunning = true;
    private Message msg = null;
    private String nickname;
    private Boolean isGameEnded = false;
    private Boolean isThisPlayerFirst = false;
    private List<String> chatDests = null;

    /**
     * Constructor of the class
     */
    public GUI() {
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
     * Sets the nickname of the owner of this GUI
     *
     * @param nickname the nickname String of the player
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @see View#run()
     */
    @Override
    public void run() {
        JavaFXGui.setCustomClassInstance(this);
        Application.launch(JavaFXGui.class);
    }

    /**
     * @see View#update(Server, Message)
     */
    @Override
    public void update(Server o, Message arg) {
        if (!isGameEnded) synchronized (lock) {
            State msg = arg.getInfo();
            switch (msg) {
                case ACK_NUMPLAYERS:
                    if (!(this.nickname != null && this.nickname.equals(arg.getNickname()))) break;
                    isThisPlayerFirst = true;
                case RECONNECTED:
                case ACK_NICKNAME:
                    if (this.nickname == null) break;
                    Platform.runLater(() -> {
                        SceneController.changeRootPane(getObservers(), "WaitingScene.fxml");
                        System.out.println("Message: " + msg.toString() + " " + nickname);
                    });
                    lock.notifyAll();
                    break;

                case SAME_NICKNAME:
                    if (this.nickname != null &&
                            this.nickname.equals(arg.getNickname()) &&
                            !SceneController.getCurrFxml().equals("WaitingScene.fxml") &&
                            !SceneController.getCurrFxml().equals("AskNumPlayersScene.fxml")
                    ) {
                        SceneController.showMessage("Please choose another nickname, the one you choose is already taken");
                        NicknameSceneController controller = (NicknameSceneController) SceneController.getActiveController();
                        controller.letReSubmit();
                        this.nickname = null;
                        System.out.println("Message: " + msg.toString() + " " + arg.getNickname());
                    }
                    lock.notifyAll();
                    break;

                case NACK_NUMPLAYERS:
                    if (arg.getNickname().equals(this.nickname)) {
                        Platform.runLater(() -> {
                            SceneController.changeRootPane(getObservers(), "WaitingScene.fxml");
                            Message check = new SendDataToServer(GAME_READY, null, 0, 0, false);
                            setChangedView();
                            notifyObserversView(check);
                            SceneController.showMessage("Someone has already chosen the number of players, you will be added to that game");
                            System.out.println("Message: " + msg.toString() + " " + arg.getNickname());
                        });
                    }
                    lock.notifyAll();
                    break;

                case GAME_READY:
                    if (arg.getNickname().equals(this.nickname)) {
                        Platform.runLater(() -> {
                            SceneController.changeRootPane(getObservers(), "PlayScene.fxml");
                            PlaySceneController controller = (PlaySceneController) SceneController.getActiveController();
                            controller.updateModel(this.nickname, arg);
                            controller.assignPersonalGoal(arg);
                            //controller.updatePlayerList(this.chatDests);
                            if (arg.getOrderedRanking() != null && arg.getOrderedRanking().equals("1")) {
                                controller.setThisFirst();
                            }
                            System.out.println("Message: " + msg.toString() + " " + arg.getNickname() + ", first=" + arg.getConfirm());
                        });
                    }
                    lock.notifyAll();
                    break;

                case ASK_NUMPLAYERS:
                    if (this.nickname == null) break;
                    Platform.runLater(() -> {
                        SceneController.changeRootPane(getObservers(), "AskNumPlayersScene.fxml");
                        AskNumPlayersSceneController controller = (AskNumPlayersSceneController) SceneController.getActiveController();
                        controller.setNickname(this.nickname);
                        System.out.println("Message: " + msg.toString() + " " + arg.getNickname());
                    });
                    lock.notifyAll();
                    break;

                case SEND_MODEL:
                    if (this.nickname == null) break;
                    if (this.nickname.equals(arg.getNickname())) {
                        Platform.runLater(() -> {
                            PlaySceneController controller = (PlaySceneController) SceneController.getActiveController();
                            controller.updateModel(this.nickname, arg);
                            controller.letSelectItemsOnBoard();
                            controller.setInstructions("It's your turn, select 1 to 3 items and then click 'confirm'");
                            System.out.println("Message: " + msg.toString() + " " + arg.getNickname() + " then");
                        });
                    } else {
                        Platform.runLater(() -> {
                            PlaySceneController controller = (PlaySceneController) SceneController.getActiveController();
                            controller.updateNotMyTurn(this.nickname, arg);
                            controller.setInstructions("It's " + arg.getNickname() + " turn, we are waiting for him");
                            System.out.println("Message: " + msg.toString() + " " + arg.getNickname() + " else");
                        });
                    }
                    lock.notifyAll();
                    break;

                case PLAYER_LIST:
                    if (this.nickname == null || this.chatDests != null) break;
                    Platform.runLater(() -> {
                        chatDests = new ArrayList<>();
                        PlaySceneController controller = (PlaySceneController) SceneController.getActiveController();
                        if (arg.getNickname() != null && !arg.getNickname().equals(this.nickname)) {
                            chatDests.add(arg.getNickname());
                            controller.updateOtherShelf(arg.getNickname(), "");
                        }
                        if (arg.getBoard() != null && !arg.getBoard().equals(this.nickname)) {
                            chatDests.add(arg.getBoard());
                            controller.updateOtherShelf(arg.getBoard(), "");
                        }
                        if (arg.getPersonal() != null && !arg.getPersonal().equals(this.nickname)) {
                            chatDests.add(arg.getPersonal());
                            controller.updateOtherShelf(arg.getPersonal(), "");
                        }
                        if (arg.getShelf() != null && !arg.getShelf().equals(this.nickname)) {
                            chatDests.add(arg.getShelf());
                            controller.updateOtherShelf(arg.getShelf(), "");
                        }
                        System.out.println("Message: " + msg.toString() + " " + arg.getNickname());
                        controller.updatePlayerList(this.chatDests);
                    });
                    break;

                case SELECTED:
                    if (arg.getNickname().equals(this.nickname)) {
                        PlaySceneController controller = (PlaySceneController) SceneController.getActiveController();
                        controller.updateModel(this.nickname, arg);
                        if (arg.getConfirm()) {
                            controller.letConfirm();
                        } else {
                            controller.dontLetConfirm();
                        }
                        System.out.println("Message: " + msg.toString() + " " + arg.getNickname());
                    }
                    lock.notifyAll();
                    break;


                case ORDER_n_COLUMN:
                    if (arg.getNickname().equals(this.nickname)) {
                        Platform.runLater(() -> {
                            PlaySceneController controller = (PlaySceneController) SceneController.getActiveController();
                            controller.letOrderAndInsert(arg.getSelected(), arg.getOrderedRanking());
                            controller.setInstructions("Now order the items, select a column and then click 'confirm'");
                            controller.disableOthers(arg.getColumns());
                        });
                        state = 6;
                    }
                    lock.notifyAll();
                    break;

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
                                controller.enlightColumn((arg.getColumns().indexOf("#") - 1) / 2);
                            }
                        });
                    }
                    lock.notifyAll();
                    break;

                case INSERTION_DONE:
                    if (arg.getNickname().equals(nickname)) {
                        Platform.runLater(() -> {
                            PlaySceneController controller = (PlaySceneController) SceneController.getActiveController();
                            controller.updateModel(this.nickname, arg);
                            controller.endTurn();
                        });
                    }
                    lock.notifyAll();
                    break;

                case FIRSTCOMMONGOAL_TAKEN:
                    if (arg.getNickname().equals(this.nickname)) {
                        Platform.runLater(() -> {
                            PlaySceneController controller = (PlaySceneController) SceneController.getActiveController();
                            controller.setCommon(0, Integer.parseInt(arg.getFirstCommon()));
                        });
                    }
                    lock.notifyAll();
                    break;

                case SECONDCOMMONGOAL_TAKEN:
                    if (arg.getNickname().equals(this.nickname)) {
                        Platform.runLater(() -> {
                            PlaySceneController controller = (PlaySceneController) SceneController.getActiveController();
                            controller.setCommon(1, Integer.parseInt(arg.getSecondCommon()));
                        });
                    }
                    lock.notifyAll();
                    break;

                case TOKEN_END_GAME:
                    if (arg.getNickname().equals(this.nickname)) {
                        Platform.runLater(() -> {
                            PlaySceneController controller = (PlaySceneController) SceneController.getActiveController();
                            controller.setCommon(2, 1);
                        });
                    } else Platform.runLater(() -> {
                        PlaySceneController controller = (PlaySceneController) SceneController.getActiveController();
                        controller.endGameTaken();
                    });
                    lock.notifyAll();
                    break;

                case RESULTS:
                    String info = arg.getOrderedRanking();
                    Platform.runLater(() -> {
                        SceneController.changeRootPane(getObservers(), "EndGameScene.fxml");
                        EndGameSceneController controller = (EndGameSceneController) SceneController.getActiveController();
                        controller.assignRanks(info);
                    });
                    isGameEnded = true;
                    lock.notifyAll();
                    break;

                case SEND_OTHER_SHELF:
                    if (!arg.getNickname().equals(this.nickname)) {
                        Platform.runLater(() -> {
                            PlaySceneController controller = (PlaySceneController) SceneController.getActiveController();
                            controller.updateOtherShelf(arg.getNickname(), arg.getShelf());
                        });
                        lock.notifyAll();
                    }
                    break;

                case TOKENS_TAKEN:
                    if (arg.getNickname().equals(this.nickname)) {
                        Platform.runLater(() -> {
                            PlaySceneController controller = (PlaySceneController) SceneController.getActiveController();
                            controller.setCommon(0, Integer.parseInt(arg.getFirstCommon()));
                            controller.setCommon(1, Integer.parseInt(arg.getSecondCommon()));
                            if (arg.getConfirm()) controller.setCommon(2, 1);
                        });
                    } else {
                        if (arg.getConfirm()) {
                            Platform.runLater(() -> {
                                PlaySceneController controller = (PlaySceneController) SceneController.getActiveController();
                                controller.endGameTaken();
                            });
                        }
                    }
                    lock.notifyAll();
                    break;

                case NOT_IN_PREV_GAME:
                    if (this.nickname != null &&
                            this.nickname.equals(arg.getNickname()) &&
                            !SceneController.getCurrFxml().equals("WaitingScene.fxml") &&
                            !SceneController.getCurrFxml().equals("AskNumPlayersScene.fxml")
                    ) {
                        Platform.runLater(() -> {
                            SceneController.showMessage("You were not in the previous game, please re-submit your nickname");
                            NicknameSceneController controller = (NicknameSceneController) SceneController.getActiveController();
                            controller.letReSubmit();
                            this.nickname = null;
                            System.out.println("Message: " + msg.toString() + " " + arg.getNickname());
                        });
                    }
                    lock.notifyAll();
                    break;

                case CHAT_MESSAGE:
                    Platform.runLater(() -> {
                        SendChatMessage m = (SendChatMessage) arg;
                        PlaySceneController controller = (PlaySceneController) SceneController.getActiveController();
                        if (!m.getFrom().equals(this.nickname)) {
                            if (m.getTo() == null)
                                controller.newMessage(m.getFrom(), "everyone", m.getText());
                            else if (m.getTo().equals(this.nickname))
                                controller.newMessage(m.getFrom(), "you", m.getText());
                        }
                    });
                    lock.notifyAll();
                    break;

                case NACK_NICKNAME:
                    if (SceneController.getCurrFxml().equals("")) {
                        new JFXPanel();
                        Platform.runLater(() -> {
                            System.err.println("We are sorry, but there already is a game in progress, try again later");
                            if (SceneController.getMainStage() != null)
                                SceneController.getMainStage().close();
                            System.exit(0);
                        });
                    }
                    break;

                case TOO_MANY:
                    Platform.runLater(() -> {
                        SceneController.showMessage("We are sorry, but there are already too many players in the game, try again later");
                        Stage stage = SceneController.getMainStage();
                        stage.close();
                    });
                    break;

                case PING:
                    break;

                default:
                    System.err.println("Ignoring event from " + msg + ": " + arg);
                    break;
            }
        }
    }
}
