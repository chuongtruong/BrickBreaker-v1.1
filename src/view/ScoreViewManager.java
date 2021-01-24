package view;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javafx.scene.control.TableView;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.stream.Collectors;


public class ScoreViewManager {
    private Stage scoreStage;
    private Scene scoreScene;
    private BorderPane scorePane;

    private static final int PREF_WIDTH = 480;
    private static final int PREF_HEIGHT = 600;

    private Stage gameStage;
    //PLEASE CHANGE THIS PATH TO WHERE THE score.txt file IS LOCATED IN YOUR MACHINE


    public ScoreViewManager(){
        initializeStage();
    };

    private void initializeStage() {
        scorePane = new BorderPane();
        scoreScene = new Scene(scorePane, PREF_WIDTH, PREF_HEIGHT);
        scorePane.getStylesheets().add("style/style.css");
        navigationArea();
        scoreStage = new Stage();
        scoreStage.setScene(scoreScene);
    }

    public void createScoreView(Stage gameStage) throws IOException {
        this.gameStage = gameStage;
        this.gameStage.hide();
        scorePane.setPadding(new Insets(30));
        readFileAndDisplay();
        scoreStage.show();
    }

    public void navigationArea(){
        AnchorPane navigationArea = new AnchorPane();

        Button close_btn = new Button("Close");
        close_btn.getStyleClass().add("green");
        navigationArea.getChildren().add(close_btn);

        AnchorPane.setBottomAnchor(close_btn, 0.0);
        AnchorPane.setRightAnchor(close_btn, 0.0);

        close_btn.setOnAction(e -> {
            scoreStage.close();
        });
        scorePane.setBottom(navigationArea);
        scorePane.setMargin(navigationArea, new Insets(30));
    }

    public void readFileAndDisplay() throws IOException {

        Collection<Score> list = Files.readAllLines(new File(MainMenuManager.FILE_PATH).toPath()).stream().map(line -> {
            String[] details = line.split( "_");
            Score s = new Score();
            s.setPlayerName(details[0]);
            s.setScore(Integer.parseInt(details[1]));
            return s;
        }).collect(Collectors.toList());

        ObservableList<Score> details = FXCollections.observableArrayList(list);

        TableView<Score> tableView = new TableView<>();

        TableColumn<Score, String> col1 = new TableColumn<>("Player's name");
        TableColumn<Score, Integer> col2 = new TableColumn<>("Player's score");


        tableView.getColumns().addAll(col1,col2);

        col1.prefWidthProperty().bind(tableView.widthProperty().multiply(0.5));
        col2.prefWidthProperty().bind(tableView.widthProperty().multiply(0.5));

        col1.setResizable(false);
        col2.setResizable(false);

        col1.setCellValueFactory(data -> data.getValue().playerNameProperty());
        col2.setCellValueFactory(data -> data.getValue().scoreProperty().asObject());
        tableView.setItems(details);

        scorePane.setCenter(tableView);
    }

    private class Score{
        StringProperty playerName = new SimpleStringProperty();
        IntegerProperty score = new SimpleIntegerProperty();

        private final StringProperty playerNameProperty(){
            return this.playerName;
        }

        private final String getPlayerName(){
            return this.playerNameProperty().get();
        }

        public final void setPlayerName(final String name) {
            this.playerNameProperty().set(name);
        }

        private final IntegerProperty scoreProperty(){
            return this.score;
        }

        private final Integer getScore(){
            return this.scoreProperty().get();
        }

        public final void setScore(final Integer s) {
            this.scoreProperty().set(s);
        }
    }
}


