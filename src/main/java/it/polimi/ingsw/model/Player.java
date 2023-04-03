package it.polimi.ingsw.model;

public class Player {
    // qui molte cose vanno final: occhio
    private final String nickname;
    private Shelf shelf;
    private final PersonalGoal personalGoal;
    private int firstCommonScore;
    private int secondCommonScore;
    private int endGameToken;

    public Player(String nickname, PersonalGoal personalGoal) {
        this.nickname = nickname;
        this.shelf = new Shelf();
        this.personalGoal = personalGoal;
        this.firstCommonScore = 0;
        this.secondCommonScore = 0;
        this.endGameToken = 0;
    }

    public String getNickname() {
        return this.nickname;
    }

    public PersonalGoal getPersonalGoal() {
        return this.personalGoal;
    }

    public Shelf getShelf() {
        return this.shelf;
    }

    public int getFirstCommonScore() {
        return this.firstCommonScore;
    }

    public void setFirstCommonScore(int firstCommonScore) {
        this.firstCommonScore = firstCommonScore;
    }

    public int getSecondCommonScore() {
        return this.secondCommonScore;
    }

    public void setSecondCommonScore(int secondCommonScore) {
        this.secondCommonScore = secondCommonScore;
    }

    public int getEndGameToken() {
        return this.endGameToken;
    }

    public void setEndGameToken(int endGameToken) {
        this.endGameToken = endGameToken;
    }

    public int computeFinalScore() {
        int pg = this.personalGoalCheck();
        return this.firstCommonScore
                + this.secondCommonScore
                + this.endGameToken
                + pg
                + this.adjacentScore();
    }

    private int personalGoalCheck() {
        int[] pointGrid = new int[]{0, 1, 2, 4, 6, 9, 12};
        int obtained = personalGoal.pg
                .stream()
                .mapToInt(a -> (shelf.getItem(a._x, a._y) != null && shelf.getItem(a._x, a._y).getType() == a._z) ? 1 : 0)
                .sum();
        return pointGrid[obtained];
    }

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
