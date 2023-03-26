package it.polimi.ingsw.model;

import it.polimi.ingsw.model.commongoal.CommonGoalAbstract;

import it.polimi.ingsw.model.custumclasses.Pair;
import it.polimi.ingsw.model.enums.StateTurn;
import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;
import java.util.List;

public class Game{              //extends Observable
    private List<Player> playerList;
    private StateTurn playerState;
    private Player currentPlayer;
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
    private List<Pair<String, Integer>> gameResult;        // da controllare come si usa pair
    /**
     * costruttore
     **/
    public Game() throws ExecutionControl.NotImplementedException{
        throw new ExecutionControl.NotImplementedException("Class not Implemented yet");
    }
    /**
     * getter
     **/
    public List<Player> getPlayerList(){ return playerList; }
    public StateTurn getPlayerState() { return playerState; }
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
    /**
     * setter
     **/
    public void setPlayerList(List<Player> playerList) { this.playerList.addAll(playerList); }
    public void setPlayerState(StateTurn playerState) {
        this.playerState = playerState;
    }
    public void setCurrentPlayer(Player currentPlayer){
        this.currentPlayer = currentPlayer;
    }
    public void setBoard(Board board) { this.board = board; }
    public void setEndGame(boolean endGame) {
        this.endGame = endGame;
    }
    public void setCanConfirmItem(boolean canConfirmItem) { this.canConfirmItem = canConfirmItem; }
    public void setOrderOK(boolean orderOK) { this.orderOK = orderOK; }
    public void setColumnOK(boolean columnOK) { this.columnOK = columnOK; }
    public void setNumPendingItems(int numPendingItems) { this.numPendingItems = numPendingItems; }
    public void setConfirmedItems(List<Item> confirmedItems) { this.confirmedItems = confirmedItems; }
    public void setTmpOrderedItems(List<Item> tmpOrderedItems) { this.tmpOrderedItems = tmpOrderedItems; }
    public void setColumnChosen (int columnChosen) { this.columnChosen = columnChosen; }
    public void setGameResult(List<Pair<String, Integer>> gameResult) { this.gameResult = gameResult; }     //=> rimuovere name e score e lasciare string e int UML
    /**
     * metodi
     **/
    public void itemClick(int x, int y) {
        Pair<Integer, Integer> cell = new Pair<>(x, y);
        if(!getBoard().getPendingCells().contains(cell)) {
            if(getCurrentPlayer().getShelf().getMaxFreeSpace() > getNumPendingItems())
                setNumPendingItems(getBoard().select(x, y));
        } else
            setNumPendingItems(getBoard().deselect(x, y));

        setCanConfirmItem(getNumPendingItems() > 0);
    }

    public void confirmItems() {
        setConfirmedItems(getBoard().removePendingItems());
        setNumPendingItems(getConfirmedItems().size());
        getCurrentPlayer().getShelf().setInsertableColumns(getNumPendingItems());
        //Aggiornamento del player state
    }

    public void orderSelectedItem(int position, int action){        // 0 - deseleziono da tmpOrderedItems, 1 - seleziono da confirmedItems => aggiungere sull'UML l'action
        Item tmpItem = new Item();

        if(action == 0){                                            // deselezione
            tmpItem = getTmpOrderedItems().remove(position);             //rimuove e ritorna l'elemento in posizione "position", fa scalare di una posizione tutti gli elementi successivi
            getConfirmedItems().add(tmpItem);
        } else if (action == 1) {                                   // selezione
            tmpItem = getConfirmedItems().remove(position);
            getTmpOrderedItems().add(tmpItem);
        }

        setOrderOK(getConfirmedItems().size() == 0 && getTmpOrderedItems().size() == getNumPendingItems());
    }

    public void selectColumn(int column) {                          // => aggiungere int column come argomento
        setColumnChosen(column);
        setColumnOK(getColumnChosen() > -1);
    }

    public void confirmInsertion() {        // la VIEW controlla i due booleani OK per mostrare il pulsante
        getCurrentPlayer().getShelf().insertItems(getTmpOrderedItems(), columnChosen);
    }

    public boolean endTurnCheck() {
        boolean closeGame = false;
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
        if(getCurrentPlayer().getFirstCommonScore() == 0){
            if(getFirstCommonGoal().specificGoal(getCurrentPlayer().getShelf()))
                getCurrentPlayer().setFirstCommonScore(getFirstCommonGoal().removePoint());        //=> cambiare takePoints con removepoint e goalObtained con specificGOal
        }

        if(getCurrentPlayer().getSecondCommonScore() == 0){
            if(getSecondCommonGoal().specificGoal(getCurrentPlayer().getShelf()))
                getCurrentPlayer().setSecondCommonScore(getSecondCommonGoal().removePoint());        //=> cambiare takePoints con removepoint e goalObtained con specificGOal
        }
    }
    private boolean endGameCheck() {                //=> sistemare su UML che ritorna boolean
        if(!getEndGame()){
            if(getCurrentPlayer().getShelf().getMaxFreeSpace() == 0)           //shelf pieno se non ho spazi liberi per le tessere
                getCurrentPlayer().setEndGameToken(1);
        }
        return getCurrentPlayer().equals(getPlayerList().get(getPlayerList().size() - 1));        //se true si va poi a endGame altrimenti se false a nextPlayer deciso dal controller
    }
    private void refillBoard() {
        if(getBoard().needToRefill()){
            getBoard().fill(getPlayerList().size());
        }
    }

    public void nextPlayer(){
        int indexCurrentPlayer = 0;
        indexCurrentPlayer = getPlayerList().indexOf(getCurrentPlayer());
        if(indexCurrentPlayer == getPlayerList().size()-1)
            setCurrentPlayer(getPlayerList().get(0));
        else
            setCurrentPlayer(getPlayerList().get(indexCurrentPlayer+1));
    }

    public void endGame(){
        Pair<String, Integer> partecipant;
        Pair<String, Integer> tmp;
        for(Player p : getPlayerList()){
            partecipant = new Pair<>(p.getNickname(), p.computeFinalScore());           //=> da cambiare su UML
            if(getGameResult().size() == 0){
                getGameResult().add(partecipant);
            } else {
                for (int i = 0; i < getGameResult().size(); i++) {
                    if (getGameResult().get(i).getY() < partecipant.getY()) {
                        tmp = getGameResult().get(i);
                        getGameResult().add(i, partecipant);
                        getGameResult().add(i + 1, tmp);
                        break;
                    }
                }
            }
        }
    }

}