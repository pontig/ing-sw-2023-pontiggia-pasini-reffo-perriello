package it.polimi.ingsw.view.gui.scene;

import it.polimi.ingsw.enums.CommonGoalName;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.SendDataToServer;
import it.polimi.ingsw.observer.ObservableView;
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
import java.util.List;

import static it.polimi.ingsw.enums.State.*;

public class PlaySceneController extends ObservableView<Message> implements GenericSceneController {

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

    @FXML
    public void initialize() {
        livingroomGrid.addEventHandler(MouseEvent.MOUSE_PRESSED, this::onLivingroomGridClick);
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
        String[][] boardMatrix = Arrays.stream(boardRows).map(row -> row.split(" ")).toArray(String[][]::new);
        List<Node> livingroomChildren = livingroomGrid.getChildren();
        livingroomChildren.forEach(cell -> {
            int colIndex = GridPane.getColumnIndex(cell);
            int rowIndex = GridPane.getRowIndex(cell);
            String cellValue = boardMatrix[rowIndex][colIndex];
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
                url.append("1.").append(cellValue.charAt(1)).append(".png");
                ((ImageView) cell).setImage(new Image(url.toString()));
            } else {
                ((ImageView) cell).setImage(null);
            }
        });

        // Update shelf
        String shelf = model.getShelf();
        String[] shelfRows = shelf.split("\n ");
        String[][] shelfMatrix = Arrays.stream(shelfRows).map(row -> row.split("(?<=\\G.{" + 2 + "})")).toArray(String[][]::new);
        List<Node> shelfChildren = shelfGrid.getChildren();
        shelfChildren.forEach(cell -> {
            int colIndex = GridPane.getColumnIndex(cell);
            String cellValue = shelfMatrix[0][colIndex];
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
                url.append("1.").append(cellValue.charAt(1)).append(".png");
                ((ImageView) cell).setImage(new Image(url.toString()));
            } else {
                ((ImageView) cell).setImage(null);
            }
        });

        // Update commonGoals
        String[] goals = {model.getFirstCommon(), model.getSecondCommon()};
        Node[][] wheretoPut = new Node[2][2];
        commonBox.getChildren().stream().forEach(cell -> {
            int colIndex = GridPane.getColumnIndex(cell);
            int rowIndex = GridPane.getRowIndex(cell);
            wheretoPut[rowIndex][colIndex] = cell;
        });
        for (int i = 0; i < 2; i++) {
            String goal = goals[i];
            String name = goal.split("\n")[0];
            int which = CommonGoalName.valueOf(name).ordinal();
            String url = "file:src/main/resources/images/commonGoals/" + which + ".png";
            ((ImageView) wheretoPut[i][1]).setImage(new Image(url));
            String[] exploded = goal.split(" ");

            ((ImageView) wheretoPut[i][1]).setImage(
                    new Image("file:src/main/resources/images/commonGoals/scores/scoring_" + exploded[exploded.length - 1] + ".jpg")
            );
        }
    }

    public void assignPersonalGoal(Message model) {
        // TODO: find a way to call this method and pass it the correct arguments
        String pg = model.getPersonal();
        int number = Integer.parseInt(pg.split("~")[1]);
        String path = "file:src/main/resources/images/personalGoals/Personal_Goals" + number + ".png";
        commonGoalCard.setImage(new Image(path));
    }

}
