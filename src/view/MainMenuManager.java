package view;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class MainMenuManager {

    /*Please change FILE_PATH to where scores.txt is located on your machine*/

    public static final String FILE_PATH = "/home/chuong/Desktop/Study/CS151/Term project/FinalProject/team-03-05/team-03-05/src/view/scores.txt";
    private static final int PREF_HEIGHT = 640;
    private static final int PREF_WIDTH = 480;
    private final BorderPane mainPane;
    private final Scene mainScene;
    private final Stage mainStage;
    public static String selectedColorTxt;
    private String playerNameTxt;

    //Constructor
    public MainMenuManager() {

        mainPane = new BorderPane();
        mainScene = new Scene(mainPane, PREF_WIDTH, PREF_HEIGHT);
        mainScene.getStylesheets().add("style/style.css");
        mainMenuCreator();
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        mainStage.setTitle("Brick Breaker v1.0");
    }

    public Stage getMainStage() {
        return mainStage;
    }

    public void mainMenuCreator() {

        //Layout
        AnchorPane navigationArea = new AnchorPane();
        VBox menuContent = new VBox(20);
        GridPane ballOptions = new GridPane();

        //Layout properties
        mainPane.setPadding(new Insets(30));
        ballOptions.setHgap(20);
        ballOptions.setVgap(10);
        menuContent.setAlignment(Pos.CENTER);
        ballOptions.setAlignment(Pos.CENTER);

        //Nodes
        TextField playerName = new TextField();
        Label selectColor = new Label("Select ball color");
        Label playerNameLbl = new Label("Player's name");
        Circle color1 = new Circle();
        Circle color2 = new Circle();
        Circle color3 = new Circle();

        Button play_btn = new Button("Play");
        Button close_btn = new Button("Exit");

        //Nodes' css selector
        play_btn.getStyleClass().add("green");
        close_btn.getStyleClass().add("green");
        playerName.getStyleClass().add("playerNameTxt");
        color1.setId("color1");
        color2.setId("color2");
        color3.setId("color3");

        //Nodes' properties
        playerName.setPrefWidth(200);
        playerName.setMaxWidth(200);
        color1.setRadius(15);
        color2.setRadius(15);
        color3.setRadius(15);
        color1.setFill(Color.RED);
        color2.setFill(Color.GREEN);
        color3.setFill(Color.BLUE);

        //Nodes' controller
        color1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) { selectedColorTxt = "red"; }
        });

        color2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                selectedColorTxt = "green";
            }
        });

        color3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                selectedColorTxt = "blue";
            }
        });

        close_btn.setOnAction(e -> mainStage.close()); //close application

        play_btn.setOnAction(e -> {


                    if (selectedColorTxt == null) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Dialog");
                        alert.setHeaderText(null);
                        alert.setContentText("Please select color");
                        alert.showAndWait();

                    } else if (playerName.getText().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Dialog");
                        alert.setHeaderText(null);
                        alert.setContentText("Please type player's name");
                        alert.showAndWait();
                    } else {
                        GameViewManager gameView = new GameViewManager();
                        playerNameTxt = playerName.getText();
                        gameView.createNewGame(mainStage, selectedColorTxt, playerNameTxt);
                    }
                }
        );

        // Add buttons to holder
        navigationArea.getChildren().add(play_btn);
        navigationArea.getChildren().add(close_btn);

        // Delete top left
        AnchorPane.setTopAnchor(close_btn, 0.0);
        AnchorPane.setLeftAnchor(close_btn, 0.0);

        // Back bottom right
        AnchorPane.setBottomAnchor(play_btn, 0.0);
        AnchorPane.setRightAnchor(play_btn, 0.0);

        //Menu content
        menuContent.getChildren().addAll(playerNameLbl, playerName, selectColor, ballOptions);
        ballOptions.add(color1, 0, 1);
        ballOptions.add(color2, 1, 1);
        ballOptions.add(color3, 2, 1);

        mainPane.setCenter(menuContent);
        mainPane.setBottom(navigationArea);

        //Adding to stage

    }
}
