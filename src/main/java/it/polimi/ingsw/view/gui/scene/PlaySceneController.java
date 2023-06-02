package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.enums.CommonGoalName;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.SendDataToServer;
import it.polimi.ingsw.tuples.Pair;
import it.polimi.ingsw.view.GUI;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.*;

import static it.polimi.ingsw.enums.State.*;

public class PlaySceneController extends GUI implements GenericSceneController {

    @FXML
    private GridPane livingroomGrid;
    @FXML
    private GridPane shelfGrid;
    @FXML
    private VBox orderingBox;
    @FXML
    private ImageView unordered1, unordered2, unordered3;
    @FXML
    private ImageView ordered1, ordered2, ordered3;
    private ImageView[] unordered;
    private ImageView[] ordered;
    private ImageView[][] orderingBoxChildren;
    @FXML
    private Button confirmInsertButton, confirmSelectButton;
    @FXML
    private Label title;
    @FXML
    private GridPane commonBox;
    @FXML
    private ImageView personalGoalCard;
    private boolean isThisFirst = false;

    // Livingroom grid cells
    @FXML
    private ImageView cell00, cell01, cell02, cell03, cell04, cell05, cell06, cell07, cell08;
    @FXML
    private ImageView cell10, cell11, cell12, cell13, cell14, cell15, cell16, cell17, cell18;
    @FXML
    private ImageView cell20, cell21, cell22, cell23, cell24, cell25, cell26, cell27, cell28;
    @FXML
    private ImageView cell30, cell31, cell32, cell33, cell34, cell35, cell36, cell37, cell38;
    @FXML
    private ImageView cell40, cell41, cell42, cell43, cell44, cell45, cell46, cell47, cell48;
    @FXML
    private ImageView cell50, cell51, cell52, cell53, cell54, cell55, cell56, cell57, cell58;
    @FXML
    private ImageView cell60, cell61, cell62, cell63, cell64, cell65, cell66, cell67, cell68;
    @FXML
    private ImageView cell70, cell71, cell72, cell73, cell74, cell75, cell76, cell77, cell78;
    @FXML
    private ImageView cell80, cell81, cell82, cell83, cell84, cell85, cell86, cell87, cell88;
    private ImageView[][] livingroomGridCells;

    // Shelf grid cells
    @FXML
    private ImageView chooseColumn0, chooseColumn1, chooseColumn2, chooseColumn3, chooseColumn4;
    private ImageView[] chooseColumns;
    @FXML
    private ImageView shelfCell00, shelfCell01, shelfCell02, shelfCell03, shelfCell04, shelfCell05;
    @FXML
    private ImageView shelfCell10, shelfCell11, shelfCell12, shelfCell13, shelfCell14, shelfCell15;
    @FXML
    private ImageView shelfCell20, shelfCell21, shelfCell22, shelfCell23, shelfCell24, shelfCell25;
    @FXML
    private ImageView shelfCell30, shelfCell31, shelfCell32, shelfCell33, shelfCell34, shelfCell35;
    @FXML
    private ImageView shelfCell40, shelfCell41, shelfCell42, shelfCell43, shelfCell44, shelfCell45;
    private ImageView[][] shelfGridCells;

    // Common goals
    @FXML
    private ImageView firstCommonImg, secondCommonImg;
    @FXML
    private ImageView first2token, first4token, first6token, first8token;
    @FXML
    private ImageView second2token, second4token, second6token, second8token;
    @FXML
    private ImageView endGameTokenImg;
    private ImageView[][] commonWrap;
    @FXML
    private ImageView firstCommonTokenTaken, secondCommonTokenTaken, endGameTokenTaken;
    private ImageView[] commonTokensTaken;
    @FXML
    private HBox selectColumnBox;
    @FXML
    private ImageView customImg;
    private boolean initialized = false;

    @FXML
    private ImageView shelfCell0014, shelfCell0013, shelfCell0012, shelfCell1014, shelfCell1013, shelfCell1012, shelfCell2014, shelfCell2013, shelfCell2012, shelfCell3014, shelfCell3013, shelfCell3012, shelfCell4014, shelfCell4013, shelfCell4012, shelfCell0114, shelfCell0113, shelfCell0112, shelfCell1114, shelfCell1113, shelfCell1112, shelfCell2114, shelfCell2113, shelfCell2112, shelfCell3114, shelfCell3113, shelfCell3112, shelfCell4114, shelfCell4113, shelfCell4112, shelfCell0214, shelfCell0213, shelfCell0212, shelfCell1214, shelfCell1213, shelfCell1212, shelfCell2214, shelfCell2213, shelfCell2212, shelfCell3214, shelfCell3213, shelfCell3212, shelfCell4214, shelfCell4213, shelfCell4212, shelfCell0314, shelfCell0313, shelfCell0312, shelfCell1314, shelfCell1313, shelfCell1312, shelfCell2314, shelfCell2313, shelfCell2312, shelfCell3314, shelfCell3313, shelfCell3312, shelfCell4314, shelfCell4313, shelfCell4312, shelfCell0414, shelfCell0413, shelfCell0412, shelfCell1414, shelfCell1413, shelfCell1412, shelfCell2414, shelfCell2413, shelfCell2412, shelfCell3414, shelfCell3413, shelfCell3412, shelfCell4414, shelfCell4413, shelfCell4412, shelfCell0514, shelfCell0513, shelfCell0512, shelfCell1514, shelfCell1513, shelfCell1512, shelfCell2514, shelfCell2513, shelfCell2512, shelfCell3514, shelfCell3513, shelfCell3512, shelfCell4514, shelfCell4513, shelfCell4512;
    private ImageView[][] shelfGridCells2, shelfGridCells3, shelfGridCells4;
    private Set<ImageView[][]> otherShelfUsed = new HashSet<>();
    @FXML
    private GridPane otherPlayerShelf1, otherPlayerShelf2, otherPlayerShelf3;
    @FXML
    private Label otherPlayerName1, otherPlayerName2, otherPlayerName3;
    private Map<String, Pair<ImageView[][], Label>> otherPlayerShelves;


    @FXML
    public synchronized void initialize() {
        livingroomGrid.addEventHandler(MouseEvent.MOUSE_PRESSED, this::onLivingroomGridClick);
        livingroomGrid.setDisable(true);

        livingroomGridCells = new ImageView[][]{{cell00, cell01, cell02, cell03, cell04, cell05, cell06, cell07, cell08},
                {cell10, cell11, cell12, cell13, cell14, cell15, cell16, cell17, cell18},
                {cell20, cell21, cell22, cell23, cell24, cell25, cell26, cell27, cell28},
                {cell30, cell31, cell32, cell33, cell34, cell35, cell36, cell37, cell38},
                {cell40, cell41, cell42, cell43, cell44, cell45, cell46, cell47, cell48},
                {cell50, cell51, cell52, cell53, cell54, cell55, cell56, cell57, cell58},
                {cell60, cell61, cell62, cell63, cell64, cell65, cell66, cell67, cell68},
                {cell70, cell71, cell72, cell73, cell74, cell75, cell76, cell77, cell78},
                {cell80, cell81, cell82, cell83, cell84, cell85, cell86, cell87, cell88}};
        shelfGridCells = new ImageView[][]{{shelfCell00, shelfCell01, shelfCell02, shelfCell03, shelfCell04, shelfCell05},
                {shelfCell10, shelfCell11, shelfCell12, shelfCell13, shelfCell14, shelfCell15},
                {shelfCell20, shelfCell21, shelfCell22, shelfCell23, shelfCell24, shelfCell25},
                {shelfCell30, shelfCell31, shelfCell32, shelfCell33, shelfCell34, shelfCell35},
                {shelfCell40, shelfCell41, shelfCell42, shelfCell43, shelfCell44, shelfCell45}};
        shelfGridCells2 = new ImageView[][]{{shelfCell0012, shelfCell0112, shelfCell0212, shelfCell0312, shelfCell0412, shelfCell0512},
                {shelfCell1012, shelfCell1112, shelfCell1212, shelfCell1312, shelfCell1412, shelfCell1512},
                {shelfCell2012, shelfCell2112, shelfCell2212, shelfCell2312, shelfCell2412, shelfCell2512},
                {shelfCell3012, shelfCell3112, shelfCell3212, shelfCell3312, shelfCell3412, shelfCell3512},
                {shelfCell4012, shelfCell4112, shelfCell4212, shelfCell4312, shelfCell4412, shelfCell4512}};
        shelfGridCells3 = new ImageView[][]{{shelfCell0013, shelfCell0113, shelfCell0213, shelfCell0313, shelfCell0413, shelfCell0513},
                {shelfCell1013, shelfCell1113, shelfCell1213, shelfCell1313, shelfCell1413, shelfCell1513},
                {shelfCell2013, shelfCell2113, shelfCell2213, shelfCell2313, shelfCell2413, shelfCell2513},
                {shelfCell3013, shelfCell3113, shelfCell3213, shelfCell3313, shelfCell3413, shelfCell3513},
                {shelfCell4013, shelfCell4113, shelfCell4213, shelfCell4313, shelfCell4413, shelfCell4513}};
        shelfGridCells4 = new ImageView[][]{{shelfCell0014, shelfCell0114, shelfCell0214, shelfCell0314, shelfCell0414, shelfCell0514},
                {shelfCell1014, shelfCell1114, shelfCell1214, shelfCell1314, shelfCell1414, shelfCell1514},
                {shelfCell2014, shelfCell2114, shelfCell2214, shelfCell2314, shelfCell2414, shelfCell2514},
                {shelfCell3014, shelfCell3114, shelfCell3214, shelfCell3314, shelfCell3414, shelfCell3514},
                {shelfCell4014, shelfCell4114, shelfCell4214, shelfCell4314, shelfCell4414, shelfCell4514}};

        commonWrap = new ImageView[][]{{firstCommonImg, null, first2token, null, first4token, null, first6token, null, first8token},
                {secondCommonImg, null, second2token, null, second4token, null, second6token, null, second8token}};
        commonTokensTaken = new ImageView[]{firstCommonTokenTaken, secondCommonTokenTaken, endGameTokenTaken};
        chooseColumns = new ImageView[]{chooseColumn0, chooseColumn1, chooseColumn2, chooseColumn3, chooseColumn4};

        unordered = new ImageView[]{unordered1, unordered2, unordered3};
        ordered = new ImageView[]{ordered1, ordered2, ordered3};
        orderingBoxChildren = new ImageView[][]{unordered, ordered};


        //shelfGrid.addEventHandler(MouseEvent.MOUSE_PRESSED, this::onShelfGridClick);
        for (int i = 0; i < chooseColumns.length; i++) {
            int inalI = i;
            chooseColumns[i].addEventHandler(MouseEvent.MOUSE_PRESSED, event -> onShelfGridClick(inalI));
        }
        orderingBox.setVisible(false);
        unordered1.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> onOrderingClick(1, 0));
        unordered2.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> onOrderingClick(1, 1));
        unordered3.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> onOrderingClick(1, 2));
        ordered1.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> onOrderingClick(0, 0));
        ordered2.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> onOrderingClick(0, 1));
        ordered3.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> onOrderingClick(0, 2));
        confirmSelectButton.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> this.onConfirmSelectClick());
        confirmSelectButton.setVisible(false);
        confirmInsertButton.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> this.onConfirmInsertClick());
        confirmInsertButton.setVisible(false);
        selectColumnBox.setVisible(false);
        customImg.setVisible(false);
        initialized = true;

        otherPlayerShelves = new HashMap<>();

    }

    public void letSelectItemsOnBoard() {
        livingroomGrid.setDisable(false);
        shelfGrid.setDisable(true);
    }

    public void letOrderAndInsert(String unorderedString, String orderedString) {
        livingroomGrid.setDisable(true);
        shelfGrid.setDisable(false);
        orderingBox.setVisible(true);
        confirmSelectButton.setVisible(false);
        confirmInsertButton.setVisible(false);
        selectColumnBox.setVisible(true);
        String[] lists = {unorderedString, orderedString};
        for (int i = 0; i < 2; i++) {
            String actual = lists[i];
            ImageView[] child = orderingBoxChildren[i];
            if (actual.equals(" ")) continue;

            actual = actual.substring(1);
            String[] items = splitString(actual, 2);
            for (int j = 0; j < items.length; j++) {

                ImageView cell = child[j];
                String cellValue = items[j];
                StringBuilder url = new StringBuilder("/images/tiles/");
                switch (cellValue.charAt(0)) {
                    case 'W':
                        // Book
                        url.append("Libri");
                        break;
                    case 'G':
                        // Cat
                        url.append("Gatti");
                        break;
                    case 'B':
                        // Frame
                        url.append("Cornici");
                        break;
                    case 'Y':
                        // Game
                        url.append("Giochi");
                        break;
                    case 'P':
                        // Plant
                        url.append("Piante");
                        break;
                    case 'L':
                        // Trophy
                        url.append("Trofei");
                        break;
                    default:
                        // Empty
                        url = null;
                        break;
                }
                if (url != null) {
                    url.append("1.").append(Character.getNumericValue(cellValue.charAt(1)) + 1).append(".png");
                    cell.setImage(new Image(PlaySceneController.class.getResourceAsStream(url.toString())));
                } else {
                    cell.setImage(null);
                }
            }
        }

    }

    public void disableOthers(String cols) {
        Arrays.stream(chooseColumns).forEach(c -> {
            c.setImage(new Image(PlaySceneController.class.getResourceAsStream("/images/thisColumn.png")));
            c.setOpacity(1);
            c.setDisable(false);
        });
        for (int i = 0; i < cols.length() / 2; i++) {
            if (cols.charAt(i * 2 + 1) == ' ') {
                chooseColumns[i].setOpacity(0.5);
                chooseColumns[i].setDisable(true);
            }
        }
    }

    public void enlightColumn(int n) {
        Arrays.stream(chooseColumns).filter(c -> c.getOpacity() == 1).forEach(c -> {
            c.setImage(new Image(PlaySceneController.class.getResourceAsStream("/images/thisColumn.png")));
            c.setOpacity(1);
            c.setDisable(false);
        });
        chooseColumns[n].setImage(new Image(PlaySceneController.class.getResourceAsStream("/images/thisColumnSelected.png")));
    }

    public void letConfirm() {
        confirmSelectButton.setVisible(true);
    }

    public void dontLetConfirm() {
        confirmSelectButton.setVisible(false);
    }

    public void letInsert() {
        confirmInsertButton.setVisible(true);
    }

    public void dontLetInsert() {
        confirmInsertButton.setVisible(false);
    }

    @FXML
    private void onLivingroomGridClick(MouseEvent event) {
        System.out.println("Livingroom grid clicked");
        Node clickedNode = event.getPickResult().getIntersectedNode();
        if (clickedNode != livingroomGrid) {
            // click on descendant node
            Integer colIndex = GridPane.getColumnIndex(clickedNode);
            Integer rowIndex = GridPane.getRowIndex(clickedNode);
            System.out.println("Mouse clicked cell: " + colIndex + " " + rowIndex);
            Message msg = new SendDataToServer(SELECT_ITEM, null, rowIndex, colIndex, false);
            setChangedView();
            notifyObserversView(msg);
        }

    }

    @FXML
    private void onShelfGridClick(int colIndex) {
        // click on descendant node

        System.out.println("Mouse clicked column: " + colIndex);
        Message msg = new SendDataToServer(SELECT_COLUMN, null, 0, colIndex, false);
        setChangedView();
        notifyObserversView(msg);
        // Arrays.stream(chooseColumns).forEach(c -> c.setImage(new Image("file:src/main/resources/images/thisColumn.png")));
    }

    @FXML
    private void onOrderingClick(int action, int pos) {
        Message msg = new SendDataToServer(ORDER_ITEM, null, action, pos, false);
        setChangedView();
        notifyObserversView(msg);

        ImageView[] from = action == 0 ? ordered : unordered;
        ImageView[] to = action == 0 ? unordered : ordered;

        for (int i = 0; ; i++) {
            if (to[i].getImage() == null) {
                to[i].setImage(from[pos].getImage());
                break;
            }
        }
        for (int i = pos; i < 2; i++) {
            from[i].setImage(from[i + 1].getImage());
        }
        from[2].setImage(null);

    }

    @FXML
    private void onConfirmInsertClick() {
        Message msg = new SendDataToServer(CONFIRM_INSERTION, null, 0, 0, true);
        setChangedView();
        notifyObserversView(msg);
        confirmInsertButton.setVisible(false);
        selectColumnBox.setVisible(false);
    }

    @FXML
    private void onConfirmSelectClick() {
        Message msg = new SendDataToServer(CONFIRM_ITEMS, null, 0, 0, true);
        setChangedView();
        notifyObserversView(msg);
    }

    public synchronized void updateModel(String nickname, Message model) {
        if (initialized) {
            title.setText("Dashboard di " + nickname);

            // Update board
            if (model.getBoard() != null) fillBoard(model);

            // Update shelf
            if (model.getShelf() != null) fillShelf(model.getShelf(), shelfGridCells);

            // Update commonGoals
            if (model.getFirstCommon() != null) fillCommonGoals(model);

            if (model.getPersonal() != null) assignPersonalGoal(model);
        }
    }

    public synchronized void assignPersonalGoal(Message model) {
        String pg = model.getPersonal();
        int number = Integer.parseInt(pg.split("~")[1]);
        String path = "/images/personalGoals/Personal_Goals" + number + ".png";
        personalGoalCard.setImage(new Image(PlaySceneController.class.getResourceAsStream(path)));
        if (model.getConfirm()) {
           setThisFirst();
        }
    }

    public void setThisFirst() {
        isThisFirst = true;
        customImg.setVisible(true);
    }

    private String[] splitString(String input, int chunkSize) {
        if (input == null) return null;
        int length = input.length();
        int numOfChunks = (int) Math.ceil((double) length / chunkSize);
        String[] chunks = new String[numOfChunks];

        for (int i = 0; i < numOfChunks; i++) {
            int start = i * chunkSize;
            int end = Math.min(start + chunkSize, length);
            chunks[i] = input.substring(start, end);
        }

        return chunks;
    }

    public void updateNotMyTurn(String nickname, Message model) {
        if (initialized) {
            title.setText("Dashboard di " + nickname);
            fillBoard(model);
            fillCommonGoals(model);
        }
    }

    private void fillBoard(Message model) {
        String board = model.getBoard();
        String[] boardRows = board.split("\n");
        String[][] itemsToPutInBoard = Arrays.stream(boardRows).map(row -> splitString(row, 3)).toArray(String[][]::new);

        for (int rowIndex = 0; rowIndex < 9; rowIndex++) {
            for (int colIndex = 0; colIndex < 9; colIndex++) {

                ImageView cell = livingroomGridCells[colIndex][rowIndex];
                String cellValue = itemsToPutInBoard[rowIndex][colIndex];
                StringBuilder url = new StringBuilder("/images/tiles/");
                switch (cellValue.charAt(1)) {
                    case 'W':
                        // Book
                        url.append("Libri");
                        break;
                    case 'G':
                        // Cat
                        url.append("Gatti");
                        break;
                    case 'B':
                        // Frame
                        url.append("Cornici");
                        break;
                    case 'Y':
                        // Game
                        url.append("Giochi");
                        break;
                    case 'P':
                        // Plant
                        url.append("Piante");
                        break;
                    case 'L':
                        // Trophy
                        url.append("Trofei");
                        break;
                    case '#':
                        // Selected
                        url = null;
                        break;
                    case '■':
                    default:
                        // Empty
                        url = null;
                        break;

                }
                ColorAdjust selectedEffect = new ColorAdjust();
                if (url != null) {
                    url.append("1.").append(Character.getNumericValue(cellValue.charAt(2)) + 1).append(".png");
                    //cell.setImage(new Image(url.toString()));
                    cell.setImage(new Image(PlaySceneController.class.getResourceAsStream(url.toString())));
                    cell.setEffect(null);
                } else {
                    if (cellValue.charAt(1) == '#') {
                        selectedEffect.setBrightness(-0.5);
                        cell.setEffect(selectedEffect);
                    } else
                        cell.setImage(null);
                }
            }
        }

    }

    private void fillShelf(String shelf, ImageView[][] target) {

        shelf = shelf.substring(1);
        String[] shelfRows = shelf.split("\n ");
        String[][] itemsToPutInShelf = Arrays.stream(shelfRows).map(row -> splitString(row, 2)).toArray(String[][]::new);

        for (int rowIndex = 0; rowIndex < 6; rowIndex++) {
            for (int colIndex = 0; colIndex < 5; colIndex++) {

                ImageView cell = target[colIndex][rowIndex];
                String cellValue = itemsToPutInShelf[rowIndex][colIndex];
                StringBuilder url = new StringBuilder("/images/tiles/");
                switch (cellValue.charAt(0)) {
                    case 'W':
                        // Book
                        url.append("Libri");
                        break;
                    case 'G':
                        // Cat
                        url.append("Gatti");
                        break;
                    case 'B':
                        // Frame
                        url.append("Cornici");
                        break;
                    case 'Y':
                        // Game
                        url.append("Giochi");
                        break;
                    case 'P':
                        // Plant
                        url.append("Piante");
                        break;
                    case 'L':
                        // Trophy
                        url.append("Trofei");
                        break;
                    case '■':
                    default:
                        // Empty
                        url = null;
                        break;
                }
                if (url != null) {
                    url.append("1.").append(Character.getNumericValue(cellValue.charAt(1)) + 1).append(".png");
                    cell.setImage(new Image(PlaySceneController.class.getResourceAsStream(url.toString())));
                } else {
                    cell.setImage(null);
                }
            }
        }
    }

    private void fillCommonGoals(Message model) {
        String[] goals = {model.getFirstCommon(), model.getSecondCommon()};
        Arrays.stream(commonWrap).flatMap(Arrays::stream).filter(Objects::nonNull).forEach(node -> node.setVisible(false));

        for (int i = 0; i < 2; i++) {
            String goal = goals[i];
            String name = goal.split("\n")[0];
            int which = CommonGoalName.valueOf(name).ordinal() + 1;
            String url = "/images/commonGoals/" + which + ".jpg";
            commonWrap[i][0].setImage(new Image(PlaySceneController.class.getResourceAsStream(url)));
            commonWrap[i][0].setVisible(true);
            String[] exploded = goal.split("are: ");
            String[] tokenRemaining = exploded[1].split(" ");
            for (int j = 0; j < tokenRemaining.length - 1; j++) {
                String token = tokenRemaining[j];
                commonWrap[i][Integer.parseInt(token)].setVisible(true);
            }

        }
    }

    public void endTurn() {
        orderingBox.setVisible(false);
        Arrays.stream(orderingBoxChildren).flatMap(Arrays::stream).forEach(node -> node.setImage(null));
    }

    public void setCommon(int which, int points) {
        ImageView toShow = commonTokensTaken[which];
        System.out.println("Common goal " + points + " taken");
        if (points == 0) {
            toShow.setImage(null);
            return;
        }
        toShow.setImage(new Image(PlaySceneController.class.getResourceAsStream("/images/commonGoals/scores/scoring_" + points + ".jpg")));
        if (which == 2) endGameTokenImg.setVisible(false);
    }

    public void endGameTaken() {
        endGameTokenImg.setVisible(false);
    }

    public void updateOtherShelf(String nickname, String shelf) {
        ImageView[][] shelfToUpdate;
        if (otherPlayerShelves.containsKey(nickname)) {
            shelfToUpdate = otherPlayerShelves.get(nickname).getX();
        } else {
            Label lbl;
            if (!otherShelfUsed.contains(shelfGridCells2)) {
                shelfToUpdate = shelfGridCells2;
                lbl = otherPlayerName1;
            }
            else if (!otherShelfUsed.contains(shelfGridCells3)) {
                shelfToUpdate = shelfGridCells3;
                lbl = otherPlayerName2;
            }
            else {
                shelfToUpdate = shelfGridCells4;
                lbl = otherPlayerName3;
            }
            otherShelfUsed.add(shelfToUpdate);
            lbl.setText(nickname);
            otherPlayerShelves.put(nickname, new Pair<>(shelfToUpdate, lbl));
        }

        fillShelf(shelf, shelfToUpdate);
    }

}
