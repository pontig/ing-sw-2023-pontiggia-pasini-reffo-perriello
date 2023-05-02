package it.polimi.ingsw.model;


public class Player {
    private final String nickname;
    private Shelf shelf;
    private final PersonalGoal personalGoal;
    private int firstCommonScore;
    private int secondCommonScore;
    private int endGameToken;

    /**
     * Constructor of Player
     * @param nickname the nickname of the player
     * @param personalGoal the personal goal of the player
     */
    public Player(String nickname, PersonalGoal personalGoal) {
        this.nickname = nickname;
        this.shelf = new Shelf();
        this.personalGoal = personalGoal;
        this.firstCommonScore = 0;
        this.secondCommonScore = 0;
        this.endGameToken = 0;
    }

    /**
     * Getter of the nickname
     * @return the nickname of the player
     */
    public String getNickname() {
        return this.nickname;
    }

    /**
     * Getter of the personal goal
     * @return the personal goal of the player
     */
    public PersonalGoal getPersonalGoal() {
        return this.personalGoal;
    }

    /**
     * Getter of the shelf
     * @return the shelf of the player
     */
    public Shelf getShelf() {
        return this.shelf;
    }

    /**
     * Getter of the first common score
     * @return the first common score of the player
     */
    public int getFirstCommonScore() {
        return this.firstCommonScore;
    }

    /**
     * Setter of the first common score
     * @param firstCommonScore the first common score of the player
     */
    public void setFirstCommonScore(int firstCommonScore) {
        this.firstCommonScore = firstCommonScore;
    }

    /**
     * Getter of the second common score
     * @return the second common score of the player
     */
    public int getSecondCommonScore() {
        return this.secondCommonScore;
    }

    /**
     * Setter of the second common score
     * @param secondCommonScore the second common score of the player
     */
    public void setSecondCommonScore(int secondCommonScore) {
        this.secondCommonScore = secondCommonScore;
    }

    /**
     * Getter of the end game token
     * @return the end game token of the player
     */
    public int getEndGameToken() {
        return this.endGameToken;
    }

    /**
     * Setter of the end game token
     * @param endGameToken the end game token of the player
     */
    public void setEndGameToken(int endGameToken) {
        this.endGameToken = endGameToken;
    }

    /**
     * At the end of the match, the method computes the final score of the player
     * @return the final score of the player
     */
    public int computeFinalScore() {
        int pg = this.personalGoalCheck();
        System.out.println("Player: " + getNickname() +
                            "\nFirst: " + this.firstCommonScore +
                            "\nSecond: " + this.secondCommonScore +
                            "\nEndGame: " + this.endGameToken +
                            "\nPersonal: " + pg +
                            "\nAdiacenze: " + this.adjacentScore());
        return this.firstCommonScore
                + this.secondCommonScore
                + this.endGameToken
                + pg
                + this.adjacentScore();
    }

    /**
     * The method checks if the player has completed the personal goal
     * @return the number of points obtained by the player
     */
    private int personalGoalCheck() {
        int[] pointGrid = new int[]{0, 1, 2, 4, 6, 9, 12};
        int obtained = personalGoal.pg
                .stream()
                .mapToInt(a -> (shelf.getItem(a._x, a._y) != null && shelf.getItem(a._x, a._y).getType() == a._z) ? 1 : 0)
                .sum();
        return pointGrid[obtained];
    }

    /**
     * Method that counts the number of adjacent items of the same type and assigns points accordingly
     * @return the total number of points obtained by the player for the adjacent items
     */
    private int adjacentScore() {
        // create a copy of the shelf
        Shelf copy = shelf.clone();
        int res = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                if (copy.getItem(i, j) != null) {
                    int adj = copy.adjacent(i, j, copy.getItem(i, j).getType());
                    if (adj == 3) res += 2;
                    else if (adj == 4) res += 3;
                    else if (adj == 5) res += 5;
                    else if (adj >= 6) res += 8;
                }
            }
        }
        return res;
    }

}
