package it.polimi.ingsw.model;

import it.polimi.ingsw.model.commongoal.*;

import it.polimi.ingsw.model.enums.CommonGoalName;
import it.polimi.ingsw.model.enums.StateTurn;
import it.polimi.ingsw.model.enums.Type;
import it.polimi.ingsw.tuples.Pair;
import it.polimi.ingsw.tuples.Triplet;
import jdk.jshell.spi.ExecutionControl;

import java.util.*;

import static it.polimi.ingsw.model.enums.Type.*;

public class Game{              //extends Observable
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
    /**
     * costruttore
     **/
    public Game(String nickName, int numberOfPlayer, Board board, List<PersonalGoal> personalGoals, List<CommonGoalName> commonGoals) {
        this.playerList = new ArrayList<>();
        this.numberOfPlayers = numberOfPlayer;
        this.personalGoals = personalGoals;
        this.currentPlayer = new Player(nickName, assignPersonalGoal());
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

    public Game(String nickName, int numberOfPlayer, Board board, List<CommonGoalName> commonGoals) {
        this.playerList = new ArrayList<>();
        this.numberOfPlayers = numberOfPlayer;
        //Da sistemare il passaggio di asset
        Set<Triplet<Integer, Integer, Type>> pG = new HashSet<Triplet<Integer, Integer, Type>>();
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

    private PersonalGoal assignPersonalGoal(){
        Random random = new Random();
        int randomInt = random.nextInt(this.personalGoals.size());
        return personalGoals.remove(randomInt);
    }

    private CommonGoalAbstract assignCommonGoal() {
        Random random = new Random();
        int randomInt = random.nextInt(this.commonGoals.size());
        CommonGoalName goal = commonGoals.remove(randomInt);
        switch(goal){
            case FIVEX:
                return new FiveXGoal(this.numberOfPlayers);

            case FOURANGLES:
                return new FourAnglesGoal(this.numberOfPlayers);

            case SIXCOUPLES:
                return new SixCouplesGoal(this.numberOfPlayers);

            case FIVEDIAGONAL:
                return new FiveDiagonalGoal(this.numberOfPlayers);

            case SQUARE2X2:
                return new Square2x2Goal(this.numberOfPlayers);

            case FOURADJACENT:
                return new FourAdjacentGoal(this.numberOfPlayers);

            case EIGHTSAMETYPE:
                return new EightSameTypeGoal(this.numberOfPlayers);

            case FIVEDECRESING:
                return new FiveDecreasingGoal(this.numberOfPlayers);

            case ROW4ITEMS5:
                return new AdjacentDifferentItemsGoal(this.numberOfPlayers, 'h', 5, 4);

            case COLUMNS3ITEMS6:
                return new AdjacentDifferentItemsGoal(this.numberOfPlayers, 'v',6, 3);

            case ROW2ITEMS5DIFFERENT:
                return new AdjacentDifferentItemsGoal(this.numberOfPlayers, 'h',5,2);

            /*case COLUMNS2ITEMS6DIFFERENT:
                return new AdjacentDifferentItemsGoal(this.numberOfPlayers, 'v',6,2);
                break;*/
        }
        return new AdjacentDifferentItemsGoal(this.numberOfPlayers, 'v',6,2);
    }

    /**
     * getter
     **/
    public List<Player> getPlayerList(){ return playerList; }
    public StateTurn getPlayerState() { return playerState; }
    public int getNumberOfPlayers(){ return numberOfPlayers; };
    public List<PersonalGoal> getPersonalGoals() {return personalGoals; };
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
    public void setNumberOfPlayers(int numberOfPlayers) { this.numberOfPlayers = numberOfPlayers; }
    public void setPersonalGoals(List<PersonalGoal> personalGoals){ this.personalGoals = personalGoals; };
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
    }

    public void confirmItems() {
        setCanConfirmItem(false);
        setConfirmedItems(getBoard().removePendingItems());
        setNumPendingItems(getConfirmedItems().size());
        getCurrentPlayer().getShelf().setInsertableColumns(getNumPendingItems());
        //Aggiornamento del player state
    }

    public void orderSelectedItem(int position, int action){        // 0 - deseleziono da tmpOrderedItems, 1 - seleziono da confirmedItems
        Item tmpItem;

        if(action == 0){                                            // deselezione
            tmpItem = getTmpOrderedItems().remove(position);        //rimuove e ritorna l'elemento in posizione "position", fa scalare di una posizione tutti gli elementi successivi
            getConfirmedItems().add(tmpItem);
        } else if (action == 1) {                                   // selezione
            tmpItem = getConfirmedItems().remove(position);
            getTmpOrderedItems().add(tmpItem);
        }

        setOrderOK(getConfirmedItems().size() == 0 && getTmpOrderedItems().size() == getNumPendingItems());
    }

    public void selectColumn(int column) {
        setColumnChosen(column);
        setColumnOK(getColumnChosen() > -1);
    }

    public void confirmInsertion() {        // la VIEW controlla i due booleani OK per mostrare il pulsante
        getCurrentPlayer().getShelf().insertItems(getTmpOrderedItems(), columnChosen);
    }

    public boolean endTurnCheck() {
        boolean closeGame = false;
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
        if(getCurrentPlayer().getFirstCommonScore() == 0){
            if(getFirstCommonGoal().specificGoal(getCurrentPlayer().getShelf()))
                getCurrentPlayer().setFirstCommonScore(getFirstCommonGoal().removePoint());
        }

        if(getCurrentPlayer().getSecondCommonScore() == 0){
            if(getSecondCommonGoal().specificGoal(getCurrentPlayer().getShelf()))
                getCurrentPlayer().setSecondCommonScore(getSecondCommonGoal().removePoint());
        }
    }
    private boolean endGameCheck() {
        if(!getEndGame()){
            if(getCurrentPlayer().getShelf().getMaxFreeSpace() == 0)           //shelf pieno se non ho spazi liberi per le tessere
                getCurrentPlayer().setEndGameToken(1);
        }
        return getCurrentPlayer().equals(getPlayerList().get(getPlayerList().size() - 1));        //se true si va poi a endGame altrimenti se false a nextPlayer deciso dal controller
    }
    private void refillBoard() {
        if(getBoard().needToRefill()){
            getBoard().fill(getPlayerList().size(), getBag());
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
    }

}