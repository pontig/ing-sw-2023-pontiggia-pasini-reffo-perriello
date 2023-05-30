package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.enums.CommonGoalName;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.SendDataToServer;
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

import java.util.Arrays;
import java.util.Objects;

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

    public synchronized void updateModel(String nickname, Message model, Boolean f) {
        if (initialized) {
        title.setText("Dashboard di " + nickname);

        // Update board
        if (model.getBoard() != null) fillBoard(model);

        // Update shelf
        if (model.getShelf() != null) fillShelf(model);

        // Update commonGoals
        if (model.getFirstCommon() != null) fillCommonGoals(model);

        if (model.getPersonal() != null) assignPersonalGoal(model);
    }}

    public synchronized void assignPersonalGoal(Message model) {
        String pg = model.getPersonal();
        int number = Integer.parseInt(pg.split("~")[1]);
        String path = "/images/personalGoals/Personal_Goals" + number + ".png";
        personalGoalCard.setImage(new Image(PlaySceneController.class.getResourceAsStream(path)));
        if (model.getConfirm()) {
            isThisFirst = true;
            customImg.setVisible(true);
        }
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

    public void gameJustStarted(String nickname, Message model) {
        if (initialized) {
        title.setText("Dashboard di " + nickname);
        fillBoard(model);
        fillCommonGoals(model);
    }}

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

    private void fillShelf(Message model) {
        String shelf = model.getShelf();
        shelf = shelf.substring(1);
        String[] shelfRows = shelf.split("\n ");
        String[][] itemsToPutInShelf = Arrays.stream(shelfRows).map(row -> splitString(row, 2)).toArray(String[][]::new);

        for (int rowIndex = 0; rowIndex < 6; rowIndex++) {
            for (int colIndex = 0; colIndex < 5; colIndex++) {

                ImageView cell = shelfGridCells[colIndex][rowIndex];
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
        toShow.setImage(new Image(PlaySceneController.class.getResourceAsStream("/images/commonGoals/scores/scoring_" + points + ".jpg")));
        if (which == 2) endGameTokenImg.setVisible(false);
    }

    public void endGameTaken() {
        endGameTokenImg.setVisible(false);
    }


}
