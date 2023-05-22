package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.enums.CommonGoalName;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.SendDataToServer;
import it.polimi.ingsw.view.GUI;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
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
    private ImageView[] unordered = {unordered1, unordered2, unordered3};
    private ImageView[] ordered = {ordered1, ordered2, ordered3};
    @FXML
    private Button confirmInsertButton;
    @FXML
    private Label title;
    @FXML
    private GridPane commonBox;
    @FXML
    private ImageView commonGoalCard;

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
    private ImageView[][] commonWrap;

    @FXML
    public void initialize() {
        livingroomGrid.addEventHandler(MouseEvent.MOUSE_PRESSED, this::onLivingroomGridClick);

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


        shelfGrid.addEventHandler(MouseEvent.MOUSE_PRESSED, this::onShelfGridClick);
        orderingBox.setVisible(false);
        unordered1.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> onOrderingClick(1, 0));
        unordered2.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> onOrderingClick(1, 1));
        unordered3.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> onOrderingClick(1, 2));
        ordered1.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> onOrderingClick(0, 0));
        ordered2.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> onOrderingClick(0, 1));
        ordered3.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> onOrderingClick(0, 2));
        confirmInsertButton.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> this.onConfirmInsertClick());
        confirmInsertButton.setVisible(false);

    }

    public void letSelectItemsOnBoard() {
        livingroomGrid.setDisable(false);
        shelfGrid.setDisable(true);
    }

    public void letOrderAndInsert() {
        livingroomGrid.setDisable(true);
        shelfGrid.setDisable(false);
        orderingBox.setVisible(true);
    }

    public void letConfirm() {
        confirmInsertButton.setVisible(true);
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
    private void onShelfGridClick(MouseEvent event) {
        System.out.println("Shelf grid clicked");
        Node clickedNode = event.getPickResult().getIntersectedNode();
        if (clickedNode != shelfGrid) {
            // click on descendant node
            Integer colIndex = GridPane.getColumnIndex(clickedNode);
            System.out.println("Mouse clicked column: " + colIndex);
            Message msg = new SendDataToServer(SELECT_COLUMN, null, 0, colIndex, false);
            setChangedView();
            notifyObserversView(msg);
        }

    }

    @FXML
    private void onOrderingClick(int action, int pos) {
        Message msg = new SendDataToServer(ORDER_ITEM, null, action, pos, false);
        setChangedView();
        notifyObserversView(msg);

        ImageView[] from = action == 0 ? ordered : unordered;
        ImageView[] to = action == 0 ? unordered : ordered;

        String fromPath = from[pos].getImage().getUrl();
        for (int i = 0; ; i++) {
            if (to[i].getImage().getUrl().equals("file:src/main/resources/images/empty.png")) {
                to[i].setImage(from[pos].getImage());
                break;
            }
        }
        for (int i = pos; i < 2; i++) {
            from[i].setImage(from[i + 1].getImage());
        }

        // Could be unnecessary, the server should check this
        boolean unorderedEmpty = true;
        for (int i = 0; i < 3; i++) {
            if (!unordered[i].getImage().getUrl().equals("file:src/main/resources/images/empty.png")) {
                unorderedEmpty = false;
                break;
            }
        }
    }

    @FXML
    private void onConfirmInsertClick() {
        Message msg = new SendDataToServer(CONFIRM_INSERTION, null, 0, 0, true);
        setChangedView();
        notifyObserversView(msg);
    }

    public void updateModel(String nickname, Message model) {
        title.setText("Dashboard di " + nickname);

        // Update board
        String board = model.getBoard();
        String[] boardRows = board.split("\n");
        String[][] itemsToPutInBoard = Arrays.stream(boardRows).map(row -> splitString(row, 3)).toArray(String[][]::new);

        for (int rowIndex = 0; rowIndex < 9; rowIndex++) {
            for (int colIndex = 0; colIndex < 9; colIndex++) {

                ImageView cell = livingroomGridCells[colIndex][rowIndex];
                String cellValue = itemsToPutInBoard[rowIndex][colIndex];
                StringBuilder url = new StringBuilder("file:src/main/resources/images/tiles/");
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
                        url.append("Selezionato");
                        break;
                    case '■':
                    default:
                        // Empty
                        url = null;
                        break;

                }
                if (url != null) {
                    url.append("1.").append(Character.getNumericValue(cellValue.charAt(2)) + 1).append(".png");
                    cell.setImage(new Image(url.toString()));
                } else {
                    cell.setImage(null);
                }
            }
        }

        // Update shelf
        String shelf = model.getShelf();
        String[] shelfRows = shelf.split("\n ");
        String[][] ItemsToPutInShelf = Arrays.stream(shelfRows).map(row -> splitString(row, 2)).toArray(String[][]::new);

        for (int rowIndex = 0; rowIndex < 6; rowIndex++) {
            for (int colIndex = 0; colIndex < 5; colIndex++) {

                ImageView cell = shelfGridCells[colIndex][rowIndex];
                String cellValue = itemsToPutInBoard[rowIndex][colIndex];
                StringBuilder url = new StringBuilder("file:src/main/resources/images/tiles/");
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
                    url.append("1.").append(Character.getNumericValue(cellValue.charAt(2)) + 1).append(".png");
                    cell.setImage(new Image(url.toString()));
                } else {
                    cell.setImage(null);
                }
            }
        }


        // Update commonGoals
        String[] goals = {model.getFirstCommon(), model.getSecondCommon()};
        Arrays.stream(commonWrap).flatMap(Arrays::stream).filter(Objects::nonNull).forEach(node -> node.setVisible(false));

        for (int i = 0; i < 2; i++) {
            String goal = goals[i];
            String name = goal.split("\n")[0];
            int which = CommonGoalName.valueOf(name).ordinal() +1 ;
            String url = "file:src/main/resources/images/commonGoals/" + which + ".jpg";
            commonWrap[i][0].setImage(new Image(url));
            commonWrap[i][0].setVisible(true);
            String[] exploded = goal.split("are: ");
            String[] tokenRemaining = exploded[1].split(" ");
            for (int j = 0; j < tokenRemaining.length -1; j++) {
                String token = tokenRemaining[j];
                String tokenUrl = "file:src/main/resources/commonGoals/scores/scoring_" + token + ".jpg";
                //commonWrap[i][j].setImage(new Image(tokenUrl));
                commonWrap[i][Integer.parseInt(token)].setVisible(true);
            }

                  //  new Image("file:src/main/resources/images/commonGoals/scores/scoring_" + exploded[exploded.length - 1] + ".jpg")

        }
    }

    public void assignPersonalGoal(Message model) {
        // TODO: find a way to call this method and pass it the correct arguments
        String pg = model.getPersonal();
        int number = Integer.parseInt(pg.split("~")[1]);
        String path = "file:src/main/resources/images/personalGoals/Personal_Goals" + number + ".png";
        commonGoalCard.setImage(new Image(path));
    }

    private String[] splitString(String input, int chunkSize) {
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

}
