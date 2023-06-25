package it.polimi.ingsw.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.enums.*;
import it.polimi.ingsw.model.commongoal.*;

import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.observer.ObservableModel;
import it.polimi.ingsw.tuples.Pair;
import it.polimi.ingsw.tuples.Triplet;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static it.polimi.ingsw.enums.State.*;
import static it.polimi.ingsw.enums.Type.*;

/**
 * Game is a class that represents a game
 * it contains every method that modifies the model
 */
public class Game extends ObservableModel<Message> {              //extends Observable
    private List<Player> playerList;
    private int numberOfPlayers;
    @JsonIgnore
    private List<PersonalGoal> personalGoals;
    private StateTurn playerState;
    private Player currentPlayer;
    private List<CommonGoalName> commonGoals;
    private String firstCommonGoalString, secondCommonGoalString;
    private CommonGoalAbstract firstCommonGoal;
    private CommonGoalAbstract secondCommonGoal;
    private Board board;
    private boolean endGame;
    @JsonIgnore
    private boolean canConfirmItem;
    private boolean orderOK;
    private boolean columnOK;
    private int numPendingItems;
    @JsonIgnore
    private List<Item> confirmedItems = new ArrayList<>();
    @JsonIgnore
    private List<Item> tmpOrderedItems = new ArrayList<>();
    private int columnChosen;
    private List<Pair<String, Integer>> gameResult;
    private Bag bag;
    private Message msg = null;
    private boolean fromScratch = true;
    private List<Player> playersToReconnect = new ArrayList<>();


    /**
     * Game constructor
     *
     * @param board            the board of the game
     * @param commonGoals      the common goals of the game
     * @param personalGoalList the personal goals of the game
     */
    public Game(Board board, List<CommonGoalName> commonGoals, List<PersonalGoal> personalGoalList) {
        this.playerList = new ArrayList<>();
        this.board = board;
        this.bag = new Bag();
        this.commonGoals = commonGoals;
        this.personalGoals = personalGoalList;
        this.endGame = false;
        this.canConfirmItem = false;
        this.orderOK = false;
        this.columnOK = false;
        this.numberOfPlayers = 0;
        this.numPendingItems = 0;
        this.confirmedItems = new ArrayList<>();
        this.tmpOrderedItems = new ArrayList<>();
        this.columnChosen = -1;
        this.gameResult = new ArrayList<>();
    }


    /**
     * Game constructor
     *
     * @param nickName       the nickname of the player that creates the game
     * @param numberOfPlayer the number of players of the game
     * @param board          the board of the game
     * @param commonGoals    the common goals of the game
     */
    public Game(String nickName, int numberOfPlayer, Board board, List<CommonGoalName> commonGoals) {
        this.playerList = new ArrayList<>();
        this.numberOfPlayers = numberOfPlayer;
        // TODO: Da sistemare il passaggio di asset
        Set<Triplet<Integer, Integer, Type>> pG = new HashSet<>();
        pG.add(new Triplet<>(4, 1, CAT));
        pG.add(new Triplet<>(4, 1, BOOK));
        pG.add(new Triplet<>(4, 1, GAME));
        pG.add(new Triplet<>(2, 0, FRAME));
        pG.add(new Triplet<>(2, 5, TROPHY));
        pG.add(new Triplet<>(0, 0, PLANTS));

        this.currentPlayer = new Player(nickName, new PersonalGoal(pG, -1));
        this.playerList.add(this.currentPlayer);
        this.board = board;
        this.bag = new Bag();
        this.board.fill(this.numberOfPlayers, this.bag);

        this.commonGoals = commonGoals;
        this.firstCommonGoal = assignCommonGoal(1);
        this.secondCommonGoal = assignCommonGoal(2);

        this.endGame = false;
        this.canConfirmItem = false;
        this.orderOK = false;
        this.columnOK = false;
        this.numPendingItems = 0;
        this.confirmedItems = new ArrayList<>();
        this.tmpOrderedItems = new ArrayList<>();
        this.columnChosen = 0;
        this.gameResult = new ArrayList<>();
    }

    //TODO - SEND_MODEL che manda il personal a tutti


    /**
     * Draws random personal goal
     *
     * @return the personal goal drawn
     */
    private PersonalGoal assignPersonalGoal() {
        Random random = new Random();
        int randomInt = random.nextInt(this.personalGoals.size());
        return personalGoals.remove(randomInt);
    }

    /**
     * Draws a common goal for the game
     *
     * @param which the number of the common goal (first or second)
     * @return the common goal drawn
     */
    private CommonGoalAbstract assignCommonGoal(int which) {
        Random random = new Random();
        int randomInt = random.nextInt(this.commonGoals.size());
        CommonGoalName goal = commonGoals.remove(randomInt);
        CommonGoalAbstract c;
        switch (which) {
            case 1:
                firstCommonGoalString = goal.toString();
                break;
            case 2:
                secondCommonGoalString = goal.toString();
                break;
            default:
                break;
        }
        switch (goal) {
            case FIVEX:
                c = new FiveXGoal(this.numberOfPlayers);
                c.setDescription(goal.toString());
                return c;

            case FOURANGLES:
                c = new FourAnglesGoal(this.numberOfPlayers);
                c.setDescription(goal.toString());
                return c;

            case SIXCOUPLES:
                c = new SixCouplesGoal(this.numberOfPlayers);
                c.setDescription(goal.toString());
                return c;

            case FIVEDIAGONAL:
                c = new FiveDiagonalGoal(this.numberOfPlayers);
                c.setDescription(goal.toString());
                return c;

            case SQUARE2X2:
                c = new Square2x2Goal(this.numberOfPlayers);
                c.setDescription(goal.toString());
                return c;

            case FOURADJACENT:
                c = new FourAdjacentGoal(this.numberOfPlayers);
                c.setDescription(goal.toString());
                return c;

            case EIGHTSAMETYPE:
                c = new EightSameTypeGoal(this.numberOfPlayers);
                c.setDescription(goal.toString());
                return c;

            case FIVEDECRESING:
                c = new FiveDecreasingGoal(this.numberOfPlayers);
                c.setDescription(goal.toString());
                return c;

            case ROW4ITEMS5:
                c = new AdjacentDifferentItemsGoal(this.numberOfPlayers, 'h', 5, 4);
                c.setDescription(goal.toString());
                return c;

            case COLUMNS3ITEMS6:
                c = new AdjacentDifferentItemsGoal(this.numberOfPlayers, 'v', 6, 3);
                c.setDescription(goal.toString());
                return c;

            case ROW2ITEMS5DIFFERENT:
                c = new AdjacentDifferentItemsGoal(this.numberOfPlayers, 'h', 5, 2);
                c.setDescription(goal.toString());
                return c;

            case COLUMNS2ITEMS6DIFFERENT:
                c = new AdjacentDifferentItemsGoal(this.numberOfPlayers, 'v', 6, 2);
                c.setDescription(goal.toString());
                return c;
        }
        return new AdjacentDifferentItemsGoal(this.numberOfPlayers, 'v', 6, 2);
    }

    /*===========================================
    =            Getters and Setters            =
    ========================================== */

    /**
     * Getter for the player list
     *
     * @return the player list
     */
    public List<Player> getPlayerList() {
        return playerList;
    }

    /**
     * Getter of the state of the player
     *
     * @return the state of the player
     */
    public StateTurn getPlayerState() {
        return playerState;
    }

    /**
     * Getter of the number of players
     *
     * @return the number of players
     */
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
     * Getter of the personal goals deck
     *
     * @return the personal goals deck
     */
    public List<PersonalGoal> getPersonalGoals() {
        return personalGoals;
    }

    /**
     * Getter of the common goals deck
     *
     * @return the common goals deck
     */
    public List<CommonGoalName> getCommonGoals() {
        return commonGoals;
    }

    /**
     * Getter of the current player
     *
     * @return the current player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Getter of the first common goal
     *
     * @return the first common goal
     */
    public CommonGoalAbstract getFirstCommonGoal() {
        return firstCommonGoal;
    }

    /**
     * Getter of the second common goal
     *
     * @return the second common goal
     */
    public CommonGoalAbstract getSecondCommonGoal() {
        return secondCommonGoal;
    }

    /**
     * Getter of the board
     *
     * @return the board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Getter of the end game token
     *
     * @return the end game token
     */
    public boolean getEndGame() {
        return endGame;
    }

    /**
     * Getter of the confirmed items
     *
     * @return the confirmed items
     */
    @JsonIgnore
    public boolean getCanConfirmItems() {
        return canConfirmItem;
    }

    /**
     * Getter if the order of the items is correct
     *
     * @return true if the order is correct, false otherwise
     */
    public boolean getOrderOK() {
        return orderOK;
    }

    /**
     * Getter if the column chosen is correct
     *
     * @return true if the column is correct, false otherwise
     */
    public boolean getColumnOK() {
        return columnOK;
    }

    /**
     * Getter of the number of pending items
     *
     * @return the number of pending items
     */
    public int getNumPendingItems() {
        return numPendingItems;
    }

    /**
     * Getter of the confirmed items
     *
     * @return the confirmed items
     */
    public List<Item> getConfirmedItems() {
        return confirmedItems;
    }

    /**
     * Getter of the temporary ordered items
     *
     * @return the temporary ordered items
     */
    public List<Item> getTmpOrderedItems() {
        return tmpOrderedItems;
    }

    /**
     * Getter of the column chosen
     *
     * @return the column chosen
     */
    public int getColumnChosen() {
        return columnChosen;
    }

    /**
     * Getter of the game results
     *
     * @return the game results
     */
    public List<Pair<String, Integer>> getGameResult() {
        return gameResult;
    }

    /**
     * Getter of the bag
     *
     * @return the bag
     */
    public Bag getBag() {
        return this.bag;
    }

    /**
     * Getter of the first common goal in string format
     *
     * @return the first common goal in string format
     */
    public String getFirstCommonGoalString() {
        return this.firstCommonGoalString;
    }

    /**
     * Getter of the second common goal in string format
     *
     * @return the second common goal in string format
     */
    public String getSecondCommonGoalString() {
        return this.secondCommonGoalString;
    }

    /**
     * Game constructor
     */
    public Game() {
    }

    /**
     * Setter of the player list
     *
     * @param playerList the player list
     */
    @JsonProperty("playerList")
    public void setPlayerList(List<Player> playerList) {
        this.playerList = new ArrayList<>(playerList);
    }

    /**
     * Setter of the first common goal, restored from the json file
     *
     * @param ref the reference to the first common goal
     */
    @JsonProperty("firstCommonGoal")
    public void restoreFirstCommon(Map<String, Object> ref) {
        restoreCommons(ref, 1);
    }

    /**
     * Setter of the second common goal, restored from the json file
     *
     * @param ref the reference to the second common goal
     */
    @JsonProperty("secondCommonGoal")
    public void restoreSecondCommon(Map<String, Object> ref) {
        restoreCommons(ref, 2);
    }

    /**
     * Restores the common goals after the json file is loaded
     *
     * @param ref   the reference to the common goals
     * @param which the number of the common goal (first or second)
     */
    private void restoreCommons(Map<String, Object> ref, int which) {
        String goal = which == 1 ? firstCommonGoalString : secondCommonGoalString;
        //CommonGoalAbstract d = which;
        CommonGoalAbstract c;
        switch (goal) {
            case "FIVEX":
                c = new FiveXGoal(this.numberOfPlayers);
                c.setDescription(goal.toString());
                break;
            case "FOURANGLES":
                c = new FourAnglesGoal(this.numberOfPlayers);
                c.setDescription(goal.toString());
                break;
            case "SIXCOUPLES":
                c = new SixCouplesGoal(this.numberOfPlayers);
                c.setDescription(goal.toString());
                break;
            case "FIVEDIAGONAL":
                c = new FiveDiagonalGoal(this.numberOfPlayers);
                c.setDescription(goal.toString());
                break;
            case "SQUARE2X2":
                c = new Square2x2Goal(this.numberOfPlayers);
                c.setDescription(goal.toString());
                break;
            case "FOURADJACENT":
                c = new FourAdjacentGoal(this.numberOfPlayers);
                c.setDescription(goal.toString());
                break;
            case "EIGHTSAMETYPE":
                c = new EightSameTypeGoal(this.numberOfPlayers);
                c.setDescription(goal.toString());
                break;
            case "FIVEDECRESING":
                c = new FiveDecreasingGoal(this.numberOfPlayers);
                c.setDescription(goal.toString());
                break;
            case "ROW4ITEMS5":
                c = new AdjacentDifferentItemsGoal(this.numberOfPlayers, 'h', 5, 4);
                c.setDescription(goal.toString());
                break;
            case "COLUMNS3ITEMS6":
                c = new AdjacentDifferentItemsGoal(this.numberOfPlayers, 'v', 6, 3);
                c.setDescription(goal.toString());
                break;
            case "ROW2ITEMS5DIFFERENT":
                c = new AdjacentDifferentItemsGoal(this.numberOfPlayers, 'h', 5, 2);
                c.setDescription(goal.toString());
                break;
            case "COLUMNS2ITEMS6DIFFERENT":
                c = new AdjacentDifferentItemsGoal(this.numberOfPlayers, 'v', 6, 2);
                c.setDescription(goal.toString());
                break;
            default:
                return;
        }
        Stack<Integer> points = new Stack<>();
        points.addAll(((ArrayList<Integer>) ref.get("points")));
        c.setPoints(points);

        if (which == 1) firstCommonGoal = c;
        else secondCommonGoal = c;
    }

    /**
     * Setter of the state of the player
     *
     * @param playerState the state of the player
     */
    public void setPlayerState(StateTurn playerState) {
        this.playerState = playerState;
    }

    /**
     * Setter of the personal goals
     *
     * @param personalGoals the personal goals
     */
    public void setPersonalGoals(List<PersonalGoal> personalGoals) {
        this.personalGoals = personalGoals;
    }

    /**
     * Setter of the common goals
     *
     * @param commonGoals the common goals
     */
    public void setCommonGoals(List<CommonGoalName> commonGoals) {
        this.commonGoals = commonGoals;
    }

    /**
     * Setter of the current player
     *
     * @param currentPlayer the current player
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Setter of the board
     *
     * @param board the board
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Setter of the end game token
     *
     * @param endGame the end game token
     */
    public void setEndGame(boolean endGame) {
        this.endGame = endGame;
    }

    /**
     * Setter of the flag to confirm the items
     *
     * @param canConfirmItem the flag to confirm the items
     */
    public void setCanConfirmItem(boolean canConfirmItem) {
        this.canConfirmItem = canConfirmItem;
    }

    /**
     * Setters of the flag to confirm the order of the items
     *
     * @param orderOK the flag to confirm the order of the items
     */
    public void setOrderOK(boolean orderOK) {
        this.orderOK = orderOK;
    }

    /**
     * Setter of the flag to confirm the column
     *
     * @param columnOK the flag to confirm the column
     */
    public void setColumnOK(boolean columnOK) {
        this.columnOK = columnOK;
    }

    /**
     * Setter of the number of pending items
     *
     * @param numPendingItems the number of pending items
     */
    public void setNumPendingItems(int numPendingItems) {
        this.numPendingItems = numPendingItems;
    }

    /**
     * Setter of the confirmed items
     *
     * @param confirmedItems the confirmed items
     */
    public void setConfirmedItems(List<Item> confirmedItems) {
        this.confirmedItems = confirmedItems;
    }

    /**
     * Setter of the temporary ordered items
     *
     * @param tmpOrderedItems the temporary ordered items
     */
    public void setTmpOrderedItems(List<Item> tmpOrderedItems) {
        this.tmpOrderedItems = tmpOrderedItems;
    }

    /**
     * Setter of the column chosen
     *
     * @param columnChosen the column chosen
     */
    public void setColumnChosen(int columnChosen) {
        this.columnChosen = columnChosen;
    }

    /**
     * Setter of the game results
     *
     * @param gameResult the game results
     */
    public void setGameResult(List<Pair<String, Integer>> gameResult) {
        this.gameResult = gameResult;
    }

    /**
     * Setter of the bag
     *
     * @param bag the bag
     */
    public void setBag(Bag bag) {
        this.bag = bag;
    }

    /**
     * Notifies that the game is resumed after a server crash
     */
    public void notFromScratch() {
        this.fromScratch = false;
        this.playersToReconnect.addAll(playerList);

    }

    /*===========================================
    =                 Game logic                =
    ========================================== */

    /**
     * Inserts a new player if the nickname isn't already taken
     * <p>
     * If the game isn't from scratch, handles the reconnection of the players
     *
     * @param nickname the nickname of the player to add
     */
    public void insertPlayer(String nickname) {
        if (fromScratch) {
            boolean sameNickname = false;

            for (Player p : getPlayerList()) {
                if (p.getNickname().equals(nickname)) {
                    sameNickname = true;
                    break;
                }
            }

            if (sameNickname)
                msg = new SendDataToClient(SAME_NICKNAME, nickname, null, null, null, null, null, null, false, null, null);
            else {
                if (getNumberOfPlayers() == 0 || getPlayerList() == null) {
                    Player p = new Player(nickname, assignPersonalGoal());
                    getPlayerList().add(p);
                    msg = new SendDataToClient(ASK_NUMPLAYERS, null, null, null, null, null, null, null, false, null, null);
                } else {
                    if (getNumberOfPlayers() == getPlayerList().size()) {
                        msg = new SendDataToClient(NACK_NICKNAME, null, null, null, null, null, null, null, false, null, null);
                    } else if (getNumberOfPlayers() > getPlayerList().size()) {
                        Player p = new Player(nickname, assignPersonalGoal()); //da sostituire con assignPersonal()
                        getPlayerList().add(p);
                        this.startGame();
                    }
                }
            }
            setChangedAndNotifyObservers(msg);
        } else {
            boolean found = false;
            for (Player p : playersToReconnect) {
                if (p.getNickname().equals(nickname)) {
                    // A player reconnected correctly
                    msg = new SendDataToClient(RECONNECTED, null, null, null, null, null, null, null, false, null, null);
                    setChangedAndNotifyObservers(msg);
                    playersToReconnect.remove(p);
                    found = true;
                    break;
                }
            }
            if (!found) {
                msg = new SendDataToClient(NOT_IN_PREV_GAME, nickname, null, null, null, null, null, null, false, null, null);
                setChangedAndNotifyObservers(msg);
            }
            if (playersToReconnect.isEmpty()) {
                this.startGame();
                for (Player p : getPlayerList()) {
                    msg = new SendDataToClient(SEND_OTHER_SHELF, p.getNickname(), null, null, p.getShelf().toString(), null, null, null, false, null, null);
                    setChangedAndNotifyObservers(msg);
                    msg = new SendDataToClient(TOKENS_TAKEN, p.getNickname(), null, null, null, "" + p.getFirstCommonScore(), "" + p.getSecondCommonScore(), null, p.getEndGameToken() == 1, null, null);
                    setChangedAndNotifyObservers(msg);
                }
            }
        }
    }

    /**
     * The first player logged in chooses the number of players of the game
     *
     * @param nickname        the players that selected the number of players
     * @param numberOfPlayers the number of players
     */
    public void setNumberOfPlayers(String nickname, int numberOfPlayers) {
        if (getNumberOfPlayers() != 0)
            msg = new SendDataToClient(NACK_NUMPLAYERS, nickname, null, null, null, null, null, null, false, null, null);
        else {
            if (numberOfPlayers < 5 && numberOfPlayers > 1) {
                this.numberOfPlayers = numberOfPlayers;
                getBoard().fill(numberOfPlayers, getBag());
                this.firstCommonGoal = assignCommonGoal(1);
                this.secondCommonGoal = assignCommonGoal(2);
                setCurrentPlayer(getPlayerList().get(0));
                msg = new SendDataToClient(ACK_NUMPLAYERS, nickname, null, null, null, null, null, null, false, null, null);
            } else
                msg = new SendDataToClient(OUT_BOUND_NUMPLAYERS, null, null, null, null, null, null, null, false, null, null);
        }
        setChangedAndNotifyObservers(msg);
    }


    /**
     * Starts the game and sends the initial data to the clients
     */
    public void startGame() {

        if (getNumberOfPlayers() == getPlayerList().size()) {
            String p1 = null, p2 = null, p3 = null, p4 = null;
            for (Player user : playerList) {
                switch (getNumberOfPlayers()) {
                    case 4:
                        p4 = getPlayerList().get(3).getNickname();
                    case 3:
                        p3 = getPlayerList().get(2).getNickname();
                    case 2:
                        p2 = getPlayerList().get(1).getNickname();
                        p1 = getPlayerList().get(0).getNickname();
                        break;
                    default:
                        break;
                }
                msg = new SendDataToClient(GAME_READY, user.getNickname(), getBoard().sendToString(), user.getPersonalGoal().sendToString(), user.getShelf().toString(), firstCommonGoal.toString(), secondCommonGoal.toString(), getCurrentPlayer().getNickname(), user == getCurrentPlayer(), user.getNickname().equals(getPlayerList().get(0).getNickname()) ? "1" : null, null);
                setChangedAndNotifyObservers(msg);
            }
            msg = new SendDataToClient(PLAYER_LIST, p1, p2, p3, p4, null, null, null, false, null, null);
            setChangedAndNotifyObservers(msg);
            msg = new SendDataToClient(SEND_MODEL, getCurrentPlayer().getNickname(), getBoard().sendToString(), getCurrentPlayer().getPersonalGoal().sendToString(), getCurrentPlayer().getShelf().toString(), firstCommonGoal.toString(), secondCommonGoal.toString(), null, false, null, null);
        } else {
            msg = new SendDataToClient(ACK_NICKNAME, null, null, null, null, null, null, null, false, null, null);
        }
        setChangedAndNotifyObservers(msg);

    }

    /**
     * Handles the selection of an item
     *
     * @param x the x coordinate of the item
     * @param y the y coordinate of the item
     */
    public void itemClick(int x, int y) {
        Pair<Integer, Integer> cell = new Pair<>(x, y);
        boolean contains = false;
        for (Pair<Integer, Integer> tmp : getBoard().getPendingCells()) {
            if (tmp.getX().equals(cell.getX()) && tmp.getY().equals(cell.getY())) {
                contains = true;
                break;
            }
        }
        if (getBoard().getDisposition()[x][y].getContent() != null) {
            if (!contains) {
                if (getCurrentPlayer().getShelf().getMaxFreeSpace() > getNumPendingItems())
                    setNumPendingItems(getBoard().select(x, y));
            } else
                setNumPendingItems(getBoard().deselect(x, y));

            setCanConfirmItem(getNumPendingItems() > 0);
        }
        System.out.println(getNumPendingItems() + " pending items");
        for (Pair<Integer, Integer> p : getBoard().getPendingCells()) {
            System.out.println(getBoard().getDisposition()[p.getX()][p.getY()].getContent().getType().toString());
        }
        msg = new SendDataToClient(SELECTED, getCurrentPlayer().getNickname(), getBoard().sendToString(), null, null, null, null, getBoard().pendingToString(), getCanConfirmItems(), null, null);
        setChangedAndNotifyObservers(msg);
    }

    /**
     * Handles the confirmation of the selected items
     */
    public void confirmItems() {
        setCanConfirmItem(false);
        setConfirmedItems(getBoard().removePendingItems());
        setNumPendingItems(getConfirmedItems().size());
        getCurrentPlayer().getShelf().setInsertableColumns(getNumPendingItems());
        msg = new SendDataToClient(ORDER_n_COLUMN, getCurrentPlayer().getNickname(), getBoard().sendToString(), getCurrentPlayer().getPersonalGoal().sendToString(), getCurrentPlayer().getShelf().toString(), null, null, confirmedItemsToString(), false, orderedItemsToString(), getCurrentPlayer().getShelf().columnsToString(-1));
        setChangedAndNotifyObservers(msg);
    }

    /**
     * Handles the re-ordering of the selected items
     *
     * @param position the position of the item in the list
     * @param action   0 from ordered to pending, 1 from pending to ordered
     */
    public void orderSelectedItem(int position, int action) {
        Item tmpItem;

        if (action == 0) {
            // from ordered to pending
            if (getTmpOrderedItems() == null || getTmpOrderedItems().size() == 0) {
                msg = new SendDataToClient(NACK_ORDER, getCurrentPlayer().getNickname(), null, null, null, null, null, null, false, null, null);
                setChangedAndNotifyObservers(msg);
                return;
            } else {
                if (position >= getTmpOrderedItems().size()) {
                    msg = new SendDataToClient(NACK_ORDER, getCurrentPlayer().getNickname(), null, null, null, null, null, null, false, null, null);
                    setChangedAndNotifyObservers(msg);
                    return;
                } else {
                    // removes the item from the ordered list, filling the gap with the following items and adds it to the pending list
                    tmpItem = getTmpOrderedItems().remove(position);
                    getConfirmedItems().add(tmpItem);
                }
            }
        } else if (action == 1) {
            // from pending to ordered
            if (getConfirmedItems() == null || getConfirmedItems().size() == 0) {
                msg = new SendDataToClient(NACK_ORDER, getCurrentPlayer().getNickname(), null, null, null, null, null, null, false, null, null);
                setChangedAndNotifyObservers(msg);
                return;
            } else {
                if (position >= getConfirmedItems().size()) {
                    msg = new SendDataToClient(NACK_ORDER, getCurrentPlayer().getNickname(), null, null, null, null, null, null, false, null, null);
                    setChangedAndNotifyObservers(msg);
                    return;
                } else {
                    // removes the item from the pending list, filling the gap with the following items and adds it to the ordered list
                    tmpItem = getConfirmedItems().remove(position);
                    getTmpOrderedItems().add(tmpItem);
                }
            }
        }

        setOrderOK(getConfirmedItems().size() == 0 && getTmpOrderedItems().size() == getNumPendingItems());
        msg = new SendDataToClient(ACK_ORDER_n_COLUMN, getCurrentPlayer().getNickname(), null, getCurrentPlayer().getPersonalGoal().sendToString(), getCurrentPlayer().getShelf().toString(), null, null, confirmedItemsToString(), getColumnOK() && getOrderOK(), orderedItemsToString(), getCurrentPlayer().getShelf().columnsToString(getColumnChosen()));
        setChangedAndNotifyObservers(msg);
    }

    /**
     * Handles the selection of a column
     *
     * @param column the column to be selected
     */
    public void selectColumn(int column) {
        boolean columnFind = false;
        for (int c : getCurrentPlayer().getShelf().getInsertableColumns()) {
            if (c == column) {
                setColumnChosen(column);
                setColumnOK(getColumnChosen() > -1);
                msg = new SendDataToClient(ACK_ORDER_n_COLUMN, getCurrentPlayer().getNickname(), null, getCurrentPlayer().getPersonalGoal().sendToString(), getCurrentPlayer().getShelf().toString(), null, null, confirmedItemsToString(), getColumnOK() && getOrderOK(), orderedItemsToString(), getCurrentPlayer().getShelf().columnsToString(column));
                columnFind = true;
                break;
            }
        }
        if (!columnFind)
            msg = new SendDataToClient(NACK_COLUMN, getCurrentPlayer().getNickname(), null, null, null, null, null, null, false, null, null);

        setChangedAndNotifyObservers(msg);
    }

    /**
     * Inserts the selected items in the selected column in the correct order and notifies the clients
     */
    public void confirmInsertion() {
        setOrderOK(false);
        setColumnOK(false);
        getPlayerByNickname(getCurrentPlayer().getNickname()).getShelf().insertItems(getTmpOrderedItems(), columnChosen);
        msg = new SendDataToClient(INSERTION_DONE, getCurrentPlayer().getNickname(), null, null, getPlayerByNickname(getCurrentPlayer().getNickname()).getShelf().toString(), null, null, null, false, null, null);
        setChangedAndNotifyObservers(msg);
        for (Player p : getPlayerList()) {
            msg = new SendDataToClient(SEND_OTHER_SHELF, p.getNickname(), null, null, p.getShelf().toString(), null, null, null, false, null, null);
            setChangedAndNotifyObservers(msg);
            System.out.println("Send shelf of " + p.getNickname());
        }

    }

    /**
     * Finds the player with the given nickname
     * @param nickname the nickname of the player
     * @return the player with the given nickname
     */
    private Player getPlayerByNickname(String nickname) {
        return getPlayerList().stream().filter(p -> p.getNickname().equals(nickname)).findFirst().orElse(null);
    }

    // TODO: is this what it actually does?

    /**
     * Handles the end of the turn, eventually re filling the board and checking if the game is over
     *
     * @return true if the game is over, false otherwise
     */
    public boolean endTurnCheck() {
        boolean closeGame;
        getTmpOrderedItems().clear();
        commonGoalCheck();
        closeGame = endGameCheck();
        if (closeGame)
            return true;
        else {
            refillBoard();
            return false;
        }
    }

    /**
     * Checks the common goals for the current player and update the client if one is taken
     */
    private void commonGoalCheck() {
        int oldC1 = getCurrentPlayer().getFirstCommonScore();
        int oldC2 = getCurrentPlayer().getSecondCommonScore();
        Integer c1 = null;
        Integer c2 = null;

        if (oldC1 == 0) {
            if (getFirstCommonGoal().specificGoal(getCurrentPlayer().getShelf()))
                getCurrentPlayer().setFirstCommonScore(getFirstCommonGoal().removePoint());
        }
        if (oldC2 == 0) {
            if (getSecondCommonGoal().specificGoal(getCurrentPlayer().getShelf()))
                getCurrentPlayer().setSecondCommonScore(getSecondCommonGoal().removePoint());
        }
        c1 = getCurrentPlayer().getFirstCommonScore();
        c2 = getCurrentPlayer().getSecondCommonScore();
        if (oldC1 == 0 && c1 != 0) {
            msg = new SendDataToClient(FIRSTCOMMONGOAL_TAKEN, getCurrentPlayer().getNickname(), null, null, null, c1.toString(), null, null, false, null, null);
            setChangedAndNotifyObservers(msg);
        }
        if (oldC2 == 0 && c2 != 0) {
            msg = new SendDataToClient(SECONDCOMMONGOAL_TAKEN, getCurrentPlayer().getNickname(), null, null, null, null, c2.toString(), null, false, null, null);
            setChangedAndNotifyObservers(msg);
        }
        System.out.println("Ho controllato i common goal di " + getCurrentPlayer().getNickname() + " --> primo: " + getCurrentPlayer().getFirstCommonScore() + " secondo: " + getCurrentPlayer().getSecondCommonScore());
    }

    /**
     * Checks if the game is over
     *
     * @return true if the game is over, false otherwise
     */
    private boolean endGameCheck() {
        if (!getEndGame()) {
            if (getCurrentPlayer().getShelf().getMaxFreeSpace() == 0) {
                // The player has no more free space in his shelf
                getCurrentPlayer().setEndGameToken(1);
                setEndGame(true);
                msg = new SendDataToClient(TOKEN_END_GAME, getCurrentPlayer().getNickname(), null, null, null, null, null, null, false, null, null);       //mando il messaggio di fine gioco
                setChangedAndNotifyObservers(msg);
            }
        }
        System.out.println("Fine gioco? " + getEndGame() + "and" + getCurrentPlayer().equals(getPlayerList().get(getPlayerList().size() - 1)));
        return getEndGame() && getCurrentPlayer().equals(getPlayerList().get(getPlayerList().size() - 1));        //se true si va poi a endGame altrimenti se false a nextPlayer deciso dal controller
    }

    /**
     * Refills the board
     */
    private void refillBoard() {
        if (getBoard().needToRefill()) {
            getBoard().fill(getPlayerList().size(), getBag());
            System.out.println("board refillata");
        }
    }

    /**
     * Give the turn to the next player
     */
    public void nextPlayer() {
        int indexCurrentPlayer = -1;

        for (Player p : getPlayerList())
            if (p.getNickname().equals(getCurrentPlayer().getNickname()))
                indexCurrentPlayer = getPlayerList().indexOf(p);

        if (indexCurrentPlayer == getPlayerList().size() - 1)
            setCurrentPlayer(getPlayerList().get(0));
        else
            setCurrentPlayer(getPlayerList().get(indexCurrentPlayer + 1));

        setNumPendingItems(0);
        setColumnChosen(-1);
        for (Player user : playerList) {
            msg = new SendDataToClient(IN_GAME, user.getNickname(), getBoard().sendToString(), user.getPersonalGoal().sendToString(), user.getShelf().toString(), firstCommonGoal.toString(), secondCommonGoal.toString(), getCurrentPlayer().getNickname(), user == getCurrentPlayer(), null, null);
            setChangedAndNotifyObservers(msg);
        }
        System.out.println("Current player: " + getCurrentPlayer().getNickname());

        try {
            (new ObjectMapper()).writeValue(new File("status.json"), this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        msg = new SendDataToClient(SEND_MODEL, getCurrentPlayer().getNickname(), getBoard().sendToString(), getCurrentPlayer().getPersonalGoal().sendToString(), getCurrentPlayer().getShelf().toString(), firstCommonGoal.toString(), secondCommonGoal.toString(), null, false, null, null);
        setChangedAndNotifyObservers(msg);
    }

    /**
     * Ends the game and computes the final score
     */
    public void endGame() {
        (new File("status.json")).delete();
        Pair<String, Integer> partecipant;
        List<Pair<String, Integer>> tempResult = new ArrayList<>();
        int candidatePos;
        for (Player p : getPlayerList()) {
            candidatePos = getGameResult().size();
            partecipant = new Pair<>(p.getNickname(), p.computeFinalScore());
            if (getGameResult().size() == 0) {
                getGameResult().add(partecipant);
            } else {
                for (int i = 0; i < getGameResult().size(); i++) {
                    if (getGameResult().get(i).getY() < partecipant.getY()) {
                        candidatePos = i;
                        break;
                    }
                }
                for (int i = 0; i < candidatePos; i++)
                    tempResult.add(getGameResult().get(i));

                tempResult.add(candidatePos, partecipant);

                for (int i = candidatePos; i < getGameResult().size(); i++)
                    tempResult.add(getGameResult().get(i));

                setGameResult(tempResult);
                tempResult = new ArrayList<>();
            }
        }
        System.out.println("Final shelves:");
        for (Player p : getPlayerList()) {
            System.out.println(p.getNickname() + ":\n" + p.getShelf().toString());
        }
        for (Pair<String, Integer> p : getGameResult()) {
            System.out.println("Player " + p.getX() + " points " + p.getY());
        }
        msg = new SendDataToClient(RESULTS, null, null, null, null, null, null, null, false, gameResultToString(), null);
        setChangedAndNotifyObservers(msg);
    }

    /**
     * Notifies the observers that the model has changed
     *
     * @param arg the message to send to the observers
     */
    private void setChangedAndNotifyObservers(Message arg) {
        setChangedModel();
        notifyObserversModel(arg);
    }

    /**
     * Sends the confirmed items to the client in a format printable by the cli
     *
     * @return the string to send to the client
     */
    private String confirmedItemsToString() {
        StringBuilder unordered = new StringBuilder(" ");
        for (int i = 0; i < getConfirmedItems().size(); i++) {
            switch (getConfirmedItems().get(i).getType()) {
                case BOOK:
                    unordered.append("W").append(getConfirmedItems().get(i).getVariant());
                    break;
                case CAT:
                    unordered.append("G").append(getConfirmedItems().get(i).getVariant());
                    break;
                case FRAME:
                    unordered.append("B").append(getConfirmedItems().get(i).getVariant());
                    break;
                case GAME:
                    unordered.append("Y").append(getConfirmedItems().get(i).getVariant());
                    break;
                case PLANTS:
                    unordered.append("P").append(getConfirmedItems().get(i).getVariant());
                    break;
                case TROPHY:
                    unordered.append("L").append(getConfirmedItems().get(i).getVariant());
                    break;
                default:
                    System.out.println("Error");
                    break;
            }
        }
        return unordered.toString();
    }

    /**
     * Sends the ordered items to the client in a format printable by the cli
     *
     * @return the string to send to the client
     */
    private String orderedItemsToString() {
        StringBuilder ordered = new StringBuilder(" ");
        for (int i = 0; i < getTmpOrderedItems().size(); i++) {
            switch (getTmpOrderedItems().get(i).getType()) {
                case BOOK:
                    ordered.append("W").append(getTmpOrderedItems().get(i).getVariant());
                    break;
                case CAT:
                    ordered.append("G").append(getTmpOrderedItems().get(i).getVariant());
                    break;
                case FRAME:
                    ordered.append("B").append(getTmpOrderedItems().get(i).getVariant());
                    break;
                case GAME:
                    ordered.append("Y").append(getTmpOrderedItems().get(i).getVariant());
                    break;
                case PLANTS:
                    ordered.append("P").append(getTmpOrderedItems().get(i).getVariant());
                    break;
                case TROPHY:
                    ordered.append("L").append(getTmpOrderedItems().get(i).getVariant());
                    break;
                default:
                    System.out.println("Error");
                    break;
            }
        }
        return ordered.toString();
    }

    /**
     * Sends the game result to the client in a format printable by the cli
     *
     * @return the string to send to the client
     */
    private String gameResultToString() {
        StringBuilder ranking = new StringBuilder("MATCH RANKING: \n");
        int i = 1;
        for (Pair<String, Integer> p : getGameResult()) {
            ranking.append(i).append(" - ");
            ranking.append(p.getX()).append("   Total score: ").append(p.getY()).append("\n");
            i++;
        }
        return ranking.toString();
    }

    /**
     * Hendles the chat message
     *
     * @param from the sender
     * @param to   the receiver
     * @param text the text of the message
     */
    public void sendChat(String from, String to, String text) {
        Message msg = new SendChatMessage(CHAT_MESSAGE, from, to, text);
        setChangedAndNotifyObservers(msg);
    }

}