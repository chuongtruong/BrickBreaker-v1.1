package application;

import javafx.application.Application;
import javafx.stage.Stage;
import view.MainMenuManager;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        try{
            MainMenuManager mainMenu = new MainMenuManager();
            primaryStage = mainMenu.getMainStage();
            primaryStage.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}