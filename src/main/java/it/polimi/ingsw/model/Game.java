package it.polimi.ingsw.model;

import it.polimi.ingsw.enums.*;
import it.polimi.ingsw.model.commongoal.*;

import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.observer.ObservableModel;
import it.polimi.ingsw.tuples.Pair;
import it.polimi.ingsw.tuples.Triplet;

import java.util.*;

import static it.polimi.ingsw.enums.State.*;
import static it.polimi.ingsw.enums.Type.*;

public class Game extends ObservableModel<Message> {              //extends Observable
    private final List<Player> playerList;
    private int numberOfPlayers;
    private List<PersonalGoal> personalGoals;
    private StateTurn playerState;
    private Player currentPlayer;
    private List<CommonGoalName> commonGoals;
    private CommonGoalAbstract firstCommonGoal;
    private CommonGoalAbstract secondCommonGoal;
    private Board board;
    private boolean endGame;
    private boolean canConfirmItem;
    private boolean orderOK;
    private boolean columnOK;
    private int numPendingItems;
    private List<Item> confirmedItems;
    private List<Item> tmpOrderedItems;
    private int columnChosen;
    private List<Pair<String, Integer>> gameResult;
    private Bag bag;
    private Message msg = null;
    /**
     * costruttore
     **/
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

    public Game(String nickName, int numberOfPlayer, Board board, List<CommonGoalName> commonGoals) {
        this.playerList = new ArrayList<>();
        this.numberOfPlayers = numberOfPlayer;
        //Da sistemare il passaggio di asset
        Set<Triplet<Integer, Integer, Type>> pG = new HashSet<>();
        pG.add(new Triplet<>(4,1,CAT));
        pG.add(new Triplet<>(4,1,BOOK));
        pG.add(new Triplet<>(4,1,GAME));
        pG.add(new Triplet<>(2,0,FRAME));
        pG.add(new Triplet<>(2,5,TROPHY));
        pG.add(new Triplet<>(0,0,PLANTS));

        this.currentPlayer = new Player(nickName, new PersonalGoal(pG));
        this.playerList.add(this.currentPlayer);
        this.board = board;
        this.bag = new Bag();
        this.board.fill(this.numberOfPlayers, this.bag);

        this.commonGoals = commonGoals;
        this.firstCommonGoal = assignCommonGoal();
        this.secondCommonGoal = assignCommonGoal();

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
    private PersonalGoal assignPersonalGoal(){
        Random random = new Random();
        int randomInt = random.nextInt(this.personalGoals.size());
        return personalGoals.remove(randomInt);
    }

    private CommonGoalAbstract assignCommonGoal() {
        Random random = new Random();
        int randomInt = random.nextInt(this.commonGoals.size());
        CommonGoalName goal = commonGoals.remove(randomInt);
        CommonGoalAbstract c;
        switch(goal){
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
                c = new AdjacentDifferentItemsGoal(this.numberOfPlayers, 'v',6, 3);
                c.setDescription(goal.toString());
                return c;

            case ROW2ITEMS5DIFFERENT:
                c = new AdjacentDifferentItemsGoal(this.numberOfPlayers, 'h',5,2);
                c.setDescription(goal.toString());
                return c;

            case COLUMNS2ITEMS6DIFFERENT:
                c = new AdjacentDifferentItemsGoal(this.numberOfPlayers, 'v',6,2);
                c.setDescription(goal.toString());
                return c;
        }
        return new AdjacentDifferentItemsGoal(this.numberOfPlayers, 'v',6,2);
    }

    /**
     * getter
     **/
    public List<Player> getPlayerList(){ return playerList; }
    public StateTurn getPlayerState() { return playerState; }
    public int getNumberOfPlayers(){ return numberOfPlayers; }
    public List<PersonalGoal> getPersonalGoals() {return personalGoals; }
    public List<CommonGoalName> getCommonGoals() { return commonGoals;}
    public Player getCurrentPlayer() { return currentPlayer; }
    public CommonGoalAbstract getFirstCommonGoal() { return firstCommonGoal; }
    public CommonGoalAbstract getSecondCommonGoal() { return secondCommonGoal; }
    public Board getBoard() { return board;}
    public boolean getEndGame() { return endGame; }
    public boolean getCanConfirmItems() { return canConfirmItem; }
    public boolean getOrderOK() { return orderOK; }
    public boolean getColumnOK() { return columnOK; }
    public int getNumPendingItems() { return numPendingItems; }
    public List<Item> getConfirmedItems() { return confirmedItems; }
    public List<Item> getTmpOrderedItems() { return tmpOrderedItems; }
    public int getColumnChosen() { return columnChosen; }
    public List<Pair<String, Integer>> getGameResult() { return gameResult;}
    public Bag getBag() { return this.bag; }
    /**
     * setter
     **/
    public void setPlayerList(List<Player> playerList) { this.playerList.addAll(playerList); }
    public void setPlayerState(StateTurn playerState) { this.playerState = playerState; }
    public void setPersonalGoals(List<PersonalGoal> personalGoals){ this.personalGoals = personalGoals; }
    public void setCommonGoals(List<CommonGoalName> commonGoals) { this.commonGoals = commonGoals; }
    public void setCurrentPlayer(Player currentPlayer){ this.currentPlayer = currentPlayer; }
    public void setBoard(Board board) { this.board = board; }
    public void setEndGame(boolean endGame) { this.endGame = endGame; }
    public void setCanConfirmItem(boolean canConfirmItem) { this.canConfirmItem = canConfirmItem; }
    public void setOrderOK(boolean orderOK) { this.orderOK = orderOK; }
    public void setColumnOK(boolean columnOK) { this.columnOK = columnOK; }
    public void setNumPendingItems(int numPendingItems) { this.numPendingItems = numPendingItems; }
    public void setConfirmedItems(List<Item> confirmedItems) { this.confirmedItems = confirmedItems; }
    public void setTmpOrderedItems(List<Item> tmpOrderedItems) { this.tmpOrderedItems = tmpOrderedItems; }
    public void setColumnChosen (int columnChosen) { this.columnChosen = columnChosen; }
    public void setGameResult(List<Pair<String, Integer>> gameResult) { this.gameResult = gameResult; }     //=> rimuovere name e score e lasciare string e int UML
    public void setBag(Bag bag) { this.bag = bag; }
    /**
     * metodi
     **/
    public void insertPlayer(String nickname) {
        boolean sameNickname = false;

        for(Player p: getPlayerList()){
            if(p.getNickname().equals(nickname)) {
                sameNickname = true;
                break;
            }
        }

        if(sameNickname)
            msg = new SendDataToClient(SAME_NICKNAME, null, null, null, null, null, null, null, false, null, null);
        else{
            if(getNumberOfPlayers() == 0 || getPlayerList() == null) {
                Player p = new Player(nickname, assignPersonalGoal());
                getPlayerList().add(p);
                msg = new SendDataToClient(ASK_NUMPLAYERS, null,null, null, null, null, null, null, false, null, null);
            } else {
                if (getNumberOfPlayers() == getPlayerList().size()) {
                    msg = new SendDataToClient(NACK_NICKNAME, null, null, null, null, null, null, null, false, null, null);
                } else if (getNumberOfPlayers() > getPlayerList().size()) {
                    Player p = new Player(nickname, assignPersonalGoal()); //da sostituire con assignPersonal()
                    getPlayerList().add(p);
                    if (getNumberOfPlayers() == getPlayerList().size())
                        msg = new SendDataToClient(SEND_MODEL, getCurrentPlayer().getNickname(), getBoard().sendToString(), getCurrentPlayer().getPersonalGoal().sendToString(), getCurrentPlayer().getShelf().toString(), firstCommonGoal.toString(), secondCommonGoal.toString(), null, false, null, null);
                    else
                        msg = new SendDataToClient(ACK_NICKNAME, null, null, null, null, null, null, null, false, null, null);
                }
            }
        }
        setChangedAndNotifyObservers(msg);
    }

    public void setNumberOfPlayers(String nickname, int numberOfPlayers) {
        if(getNumberOfPlayers() != 0)
            msg = new SendDataToClient(NACK_NUMPLAYERS, nickname, null, null, null, null, null, null, false, null, null);
        else {
            if (numberOfPlayers < 5 && numberOfPlayers > 1) {
                this.numberOfPlayers = numberOfPlayers;
                getBoard().fill(numberOfPlayers, getBag());
                this.firstCommonGoal = assignCommonGoal();
                this.secondCommonGoal = assignCommonGoal();
                setCurrentPlayer(getPlayerList().get(0));
                msg = new SendDataToClient(ACK_NUMPLAYERS, null, null, null, null, null, null, null, false, null, null);
            } else
                msg = new SendDataToClient(OUT_BOUND_NUMPLAYERS, nickname, null, null, null, null, null, null, false, null, null);
        }
        setChangedAndNotifyObservers(msg);
    }

    public void startGame(){
        if(getNumberOfPlayers() == getPlayerList().size())
            msg = new SendDataToClient(SEND_MODEL, getCurrentPlayer().getNickname(), getBoard().sendToString(), getCurrentPlayer().getPersonalGoal().sendToString(), getCurrentPlayer().getShelf().toString(), firstCommonGoal.toString(), secondCommonGoal.toString(), null, false, null, null);
        else
            msg = new SendDataToClient(ACK_NICKNAME, null, null, null, null, null, null, null, false, null, null);
        setChangedAndNotifyObservers(msg);
    }
    public void itemClick(int x, int y) {
        Pair<Integer, Integer> cell = new Pair<>(x, y);
        boolean contains = false;
        for(Pair<Integer, Integer> tmp : getBoard().getPendingCells()){
            if(tmp.getX().equals(cell.getX()) && tmp.getY().equals(cell.getY())){
                contains = true;
                break;
            }
        }
        if(getBoard().getDisposition()[x][y].getContent() != null) {
            if (!contains) {
                if (getCurrentPlayer().getShelf().getMaxFreeSpace() > getNumPendingItems())
                    setNumPendingItems(getBoard().select(x, y));
            } else
                setNumPendingItems(getBoard().deselect(x, y));

            setCanConfirmItem(getNumPendingItems() > 0);
        }
        System.out.println(getNumPendingItems());
        for(Pair<Integer, Integer> p: getBoard().getPendingCells()){
            System.out.println(getBoard().getDisposition()[p.getX()][p.getY()].getContent().getType().toString());
        }
        msg = new SendDataToClient(SELECTED, getCurrentPlayer().getNickname(), getBoard().sendToString(), null, null, null, null, getBoard().pendingToString(), getCanConfirmItems(), null, null);
        setChangedAndNotifyObservers(msg);
    }

    public void confirmItems() {
        setCanConfirmItem(false);
        setConfirmedItems(getBoard().removePendingItems());
        setNumPendingItems(getConfirmedItems().size());
        getCurrentPlayer().getShelf().setInsertableColumns(getNumPendingItems());
        msg = new SendDataToClient(ORDER_n_COLUMN, getCurrentPlayer().getNickname(), getBoard().sendToString(), getCurrentPlayer().getPersonalGoal().sendToString(), getCurrentPlayer().getShelf().toString(), null, null, confirmedItemsToString(), false, orderedItemsToString(), getCurrentPlayer().getShelf().columnsToString(-1));
        setChangedAndNotifyObservers(msg);
    }

    public void orderSelectedItem(int position, int action){        // 0 - deseleziono da tmpOrderedItems, 1 - seleziono da confirmedItems
        Item tmpItem;

        if(action == 0){                                            // deselezione dagli ordinati
            if(getTmpOrderedItems() == null || getTmpOrderedItems().size() == 0){
                msg = new SendDataToClient(NACK_ORDER, getCurrentPlayer().getNickname(), null, null, null, null, null, null, false, null, null);
                setChangedAndNotifyObservers(msg);
                return;
            } else{
                if(position >= getTmpOrderedItems().size()) {
                    msg = new SendDataToClient(NACK_ORDER, getCurrentPlayer().getNickname(), null, null, null, null, null, null, false, null, null);
                    setChangedAndNotifyObservers(msg);
                    return;
                } else {
                    tmpItem = getTmpOrderedItems().remove(position);        //rimuove e ritorna l'elemento in posizione "position", fa scalare di una posizione tutti gli elementi successivi
                    getConfirmedItems().add(tmpItem);
                }
            }
        } else if (action == 1) {                                   // selezione dai disordinati
            if(getConfirmedItems() == null || getConfirmedItems().size() == 0){
                msg = new SendDataToClient(NACK_ORDER, getCurrentPlayer().getNickname(), null, null, null, null, null, null, false, null, null);
                setChangedAndNotifyObservers(msg);
                return;
            } else {
                if(position >= getConfirmedItems().size()) {
                    msg = new SendDataToClient(NACK_ORDER, getCurrentPlayer().getNickname(), null, null, null, null, null, null, false, null, null);
                    setChangedAndNotifyObservers(msg);
                    return;
                } else {
                    tmpItem = getConfirmedItems().remove(position);
                    getTmpOrderedItems().add(tmpItem);
                }
            }
        }

        setOrderOK(getConfirmedItems().size() == 0 && getTmpOrderedItems().size() == getNumPendingItems());
        msg = new SendDataToClient(ACK_ORDER_n_COLUMN, getCurrentPlayer().getNickname(), null, getCurrentPlayer().getPersonalGoal().sendToString(), getCurrentPlayer().getShelf().toString(), null, null, confirmedItemsToString(), getColumnOK()&&getOrderOK(), orderedItemsToString(), getCurrentPlayer().getShelf().columnsToString(getColumnChosen()));
        setChangedAndNotifyObservers(msg);
    }

    public void selectColumn(int column) {
        boolean columnFind = false;
        for(int c:getCurrentPlayer().getShelf().getInsertableColumns()){
            if(c == column) {
                setColumnChosen(column);
                setColumnOK(getColumnChosen() > -1);
                msg = new SendDataToClient(ACK_ORDER_n_COLUMN, getCurrentPlayer().getNickname(), null, getCurrentPlayer().getPersonalGoal().toString(), getCurrentPlayer().getShelf().toString(), null, null, confirmedItemsToString(), getColumnOK()&&getOrderOK(), orderedItemsToString(), getCurrentPlayer().getShelf().columnsToString(column));
                columnFind = true;
                break;
            }
        }
        if(!columnFind)
            msg = new SendDataToClient(NACK_COLUMN, getCurrentPlayer().getNickname(), null, null, null, null, null, null, false, null, null);

        setChangedAndNotifyObservers(msg);
    }

    public void confirmInsertion() {        // la VIEW controlla i due booleani OK per mostrare il pulsante
        setOrderOK(false);
        setColumnOK(false);
        getCurrentPlayer().getShelf().insertItems(getTmpOrderedItems(), columnChosen);
        msg = new SendDataToClient(INSERTION_DONE, getCurrentPlayer().getNickname(), null, null, getCurrentPlayer().getShelf().toString(), null, null, null, false, null, null);
        setChangedAndNotifyObservers(msg);
    }

    public boolean endTurnCheck() {
        boolean closeGame;
        getTmpOrderedItems().clear();
        commonGoalCheck();
        closeGame = endGameCheck();
        if(closeGame)
            return true;
        else {
            refillBoard();
            return false;
        }
    }
    private void commonGoalCheck() {
        int oldC1 = getCurrentPlayer().getFirstCommonScore();
        int oldC2 = getCurrentPlayer().getSecondCommonScore();
        Integer c1 = null;
        Integer c2 = null;

        if(oldC1 == 0){
            if(getFirstCommonGoal().specificGoal(getCurrentPlayer().getShelf()))
                getCurrentPlayer().setFirstCommonScore(getFirstCommonGoal().removePoint());
        }
        if(oldC2 == 0){
            if(getSecondCommonGoal().specificGoal(getCurrentPlayer().getShelf()))
                getCurrentPlayer().setSecondCommonScore(getSecondCommonGoal().removePoint());
        }
        c1 = getCurrentPlayer().getFirstCommonScore();
        c2 = getCurrentPlayer().getSecondCommonScore();
        if(oldC1 == 0 && c1 != 0) {
            msg = new SendDataToClient(FIRSTCOMMONGOAL_TAKEN, getCurrentPlayer().getNickname(), null, null, null, c1.toString(), null, null, false, null, null);
            setChangedAndNotifyObservers(msg);
        }
        if(oldC2 == 0 && c2 != 0) {
            msg = new SendDataToClient(SECONDCOMMONGOAL_TAKEN, getCurrentPlayer().getNickname(), null, null, null, null, c2.toString(), null, false, null, null);
            setChangedAndNotifyObservers(msg);
        }
        System.out.println("Ho controllato i common goal di " + getCurrentPlayer().getNickname() + " --> primo: " + getCurrentPlayer().getFirstCommonScore() + " secondo: " + getCurrentPlayer().getSecondCommonScore());
    }
    private boolean endGameCheck() {
        if(!getEndGame()){
            if(getCurrentPlayer().getShelf().getMaxFreeSpace() == 0) {          //shelf pieno se non ho spazi liberi per le tessere
                getCurrentPlayer().setEndGameToken(1);
                setEndGame(true);
                msg = new SendDataToClient(TOKEN_END_GAME, getCurrentPlayer().getNickname(), null, null, null, null, null, null, false, null, null);       //mando il messaggio di fine gioco
                setChangedAndNotifyObservers(msg);
            }
        }
        System.out.println("Fine gioco? " + getEndGame() + "and" + getCurrentPlayer().equals(getPlayerList().get(getPlayerList().size() - 1)));
        return getEndGame() && getCurrentPlayer().equals(getPlayerList().get(getPlayerList().size() - 1));        //se true si va poi a endGame altrimenti se false a nextPlayer deciso dal controller
    }
    private void refillBoard() {
        if(getBoard().needToRefill()){
            getBoard().fill(getPlayerList().size(), getBag());
            System.out.println("board refillata");
        }
    }

    public void nextPlayer(){
        int indexCurrentPlayer = getPlayerList().indexOf(getCurrentPlayer());
        if(indexCurrentPlayer == getPlayerList().size()-1)
            setCurrentPlayer(getPlayerList().get(0));
        else
            setCurrentPlayer(getPlayerList().get(indexCurrentPlayer+1));

        setNumPendingItems(0);
        setColumnChosen(-1);
        System.out.println("Current player: " + getCurrentPlayer().getNickname());
        msg = new SendDataToClient(SEND_MODEL, getCurrentPlayer().getNickname(), getBoard().sendToString(), getCurrentPlayer().getPersonalGoal().sendToString(), getCurrentPlayer().getShelf().toString(), firstCommonGoal.toString(), secondCommonGoal.toString(), null, false, null, null);
        setChangedAndNotifyObservers(msg);
    }

    public void endGame(){
        Pair<String, Integer> partecipant;
        List<Pair<String, Integer>> tempResult = new ArrayList<>();
        int candidatePos;
        for(Player p : getPlayerList()){
            candidatePos = getGameResult().size();
            partecipant = new Pair<>(p.getNickname(), p.computeFinalScore());
            if(getGameResult().size() == 0){
                getGameResult().add(partecipant);
            } else {
                for (int i = 0; i < getGameResult().size(); i++) {
                    if (getGameResult().get(i).getY() < partecipant.getY()) {
                        candidatePos = i;
                        break;
                    }
                }
                for(int i = 0; i < candidatePos; i++)
                    tempResult.add(getGameResult().get(i));

                tempResult.add(candidatePos, partecipant);

                for(int i = candidatePos; i < getGameResult().size(); i++)
                    tempResult.add(getGameResult().get(i));

                setGameResult(tempResult);
                tempResult = new ArrayList<>();
            }
        }
        for(Pair<String, Integer> p: getGameResult()){
            System.out.println("Player " + p.getX() + " points " + p.getY());
        }
        msg = new SendDataToClient(RESULTS, null, null, null, null, null, null, null, false, gameResultToString(), null);
        setChangedAndNotifyObservers(msg);
    }

    private void setChangedAndNotifyObservers(Message arg) {
        setChangedModel();
        notifyObserversModel(arg);
    }

    private String confirmedItemsToString(){
        StringBuilder unordered = new StringBuilder(" ");
        for(int i=0; i<getConfirmedItems().size(); i++){
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

    private String orderedItemsToString(){
        StringBuilder ordered = new StringBuilder(" ");
        for(int i=0; i<getTmpOrderedItems().size(); i++){
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

    private String gameResultToString(){
        StringBuilder ranking = new StringBuilder("MATCH RANKING: \n");
        int i = 1;
        for(Pair<String, Integer> p: getGameResult()){
            ranking.append(i).append(" - ");
            ranking.append(p.getX()).append("   Total score: ").append(p.getY()).append("\n");
            i++;
        }
        return ranking.toString();
    }

}