package view;

import model.Ball;
import model.Brick;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class GameViewManager {
    private Scene gameScene;
    private Stage gameStage;
    private final GridPane gameInfo = new GridPane();

    private static final int PREF_WIDTH = 480;
    private static final int PREF_HEIGHT = 640;

    private Stage menuStage;
    public String colorID;
    public String playerName;

    private ArrayList<Ball> balls;
    private GraphicsContext gd;
    private GameTimer timer;
    private double panelX;
    private double ballX, ballY, ballXV, ballYV;
    private Brick[][]blocks;
    private boolean right, left;
    public static double panelXV;
    private int score;
    private int level;



    public GameViewManager() {
        initializeStage();
    }

    //Event handler to controller A,D key press

    private void createKeyListener() {
        gameScene.setOnKeyPressed( e->{
            switch (e.getCode()){
                case A : {
                    right = false;
                    left = true;
                    break;
                }
                case D :
                    left = false;
                    right = true;
                    break;

                default : {
                    break;
                }
            }
        });

        gameScene.setOnKeyReleased(e -> {
            switch (e.getCode()){
                case A : {
                    left = false;
                    break;
                }
                case D : {
                    right = false;
                    break;
                }
                default : {
                    break;
                }
            }
        });
    }

    private void initVariables()
    {
        level = 1;
        panelX = PREF_WIDTH/2-30;
        ballX = PREF_WIDTH/2;
        ballY = PREF_HEIGHT/2;
        ballXV = 0;
        ballYV = 5;
        right = false;
        left = false;
        loadLevel();
        score = 0;
        balls = new ArrayList<Ball>();
        Ball b = new Ball(ballX, ballY, ballXV, ballYV);
        b.setBallColor(MainMenuManager.selectedColorTxt);
        balls.add(b);


    }

    private void initializeStage() {
        HBox gameBox = new HBox();
        gameBox.setAlignment(Pos.CENTER);
        gameScene = new Scene(gameBox, PREF_WIDTH, PREF_HEIGHT);
        Canvas canvas = new Canvas(PREF_WIDTH, PREF_HEIGHT);
        gd = canvas.getGraphicsContext2D();
        gd.setFont(new Font("Time New Roman", 28));
        initVariables();
        timer = new GameTimer();
        gameBox.getChildren().add(canvas);
        createKeyListener();

        gameBox.getStylesheets().add("style/style.css");
        gameStage = new Stage();
        gameStage.setScene(gameScene);
        gameStage.setResizable(false);
        gameStage.setTitle("Brick Breaker v1.0");
        gameStage.setOnCloseRequest(e -> {
            e.consume();
            System.exit(0);
        });
        gameStage.show();
        timer.start();
    }

    private void loadLevel() {
        if (level == 2) {
            blocks = new Brick[][]{
                    {
                            new Brick(false), new Brick(false), new Brick(false), new Brick(false), new Brick(false), new Brick(1), new Brick(1), new Brick(false), new Brick(false), new Brick(false), new Brick(false), new Brick(false)
                    },
                    {
                            new Brick(1), new Brick(false), new Brick(false), new Brick(1), new Brick(1), new Brick(2), new Brick(2), new Brick(1), new Brick(1), new Brick(false), new Brick(false), new Brick(1)
                    },
                    {
                            new Brick(false), new Brick(1), new Brick(1), new Brick(false), new Brick(1), new Brick(1), new Brick(1), new Brick(1), new Brick(false), new Brick(1), new Brick(1), new Brick(false)
                    },
                    {
                            new Brick(false), new Brick(false), new Brick(false), new Brick(1), new Brick(1), new Brick(false), new Brick(false), new Brick(1), new Brick(1), new Brick(false), new Brick(false), new Brick(false)
                    },
                    {
                            new Brick(false), new Brick(1), new Brick(1), new Brick(false), new Brick(false), new Brick(false), new Brick(false), new Brick(false), new Brick(false), new Brick(1), new Brick(1), new Brick(false)
                    },
                    {
                            new Brick(1), new Brick(false), new Brick(false), new Brick(false), new Brick(false), new Brick(false), new Brick(false), new Brick(false), new Brick(false), new Brick(false), new Brick(false), new Brick(1)
                    }
            };
        } else if (level == 3) {
            blocks = new Brick[][]{
                    {
                            new Brick(1), new Brick(1), new Brick(1), new Brick(1), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2)
                    },
                    {
                            new Brick(1), new Brick(1), new Brick(1), new Brick(1), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3)
                    },
                    {
                            new Brick(1), new Brick(1), new Brick(1), new Brick(1), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2)
                    },
                    {
                            new Brick(1), new Brick(1), new Brick(1), new Brick(1), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3)
                    },
                    {
                            new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2)
                    },
                    {
                            new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3)
                    }
            };
        } else if (level == 1) {
            blocks = new Brick[][]{
                    {
                            new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3)
                    },
                    {
                            new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3), new Brick(3)
                    },
                    {
                            new Brick(1), new Brick(1), new Brick(1), new Brick(1), new Brick(1), new Brick(1), new Brick(1), new Brick(1), new Brick(1), new Brick(1), new Brick(1), new Brick(1)
                    },
                    {
                            new Brick(1), new Brick(1), new Brick(1), new Brick(1), new Brick(1), new Brick(1), new Brick(1), new Brick(1), new Brick(1), new Brick(1), new Brick(1), new Brick(1)
                    },
                    {
                            new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2)
                    },
                    {
                            new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2), new Brick(2)
                    }
            };
        }
        panelX = PREF_WIDTH/2-30;
        ballX = PREF_WIDTH/2;
        ballY = PREF_HEIGHT/2;
        ballXV = 0;
        ballYV = 5;
        balls = new ArrayList<Ball>();
        Ball b = new Ball(ballX, ballY, ballXV, ballYV);
        b.setBallColor(MainMenuManager.selectedColorTxt);
        balls.add(b);
    }

    public void createNewGame(Stage menuStage, String selectedColor, String playerName) {
        this.menuStage = menuStage;
        this.menuStage.hide();
        this.playerName = playerName;
        this.colorID = selectedColor;

        gameStage.show();
    }

    private void setText(String color, String name) {
        Label ballColor = new Label("Ball color:  " + color);
        Label playerName = new Label("Player's name: " + name);
    }

    private  void gameInfoArea(){
        Button score_btn = new Button("See Score");
        Button back_btn = new Button("Back");
        score_btn.getStyleClass().add("green");
        back_btn.getStyleClass().add("green");

        AnchorPane navigationArea = new AnchorPane();
        navigationArea.getChildren().add(score_btn);
        navigationArea.getChildren().add(back_btn);

        AnchorPane.setTopAnchor(back_btn, 0.0);
        AnchorPane.setLeftAnchor(back_btn, 0.0);

        // Back bottom right
        AnchorPane.setBottomAnchor(score_btn, 0.0);
        AnchorPane.setRightAnchor(score_btn, 0.0);
    }

    private class GameTimer extends AnimationTimer
    {
        public GameTimer()
        {
            //Empty
        }

        @Override
        public void handle(long arg0) {
            handleIt();
        }

        private synchronized void handleIt()
        {
            handlePanel();
            handleBall();
            gd.clearRect(0, 0, PREF_WIDTH, PREF_HEIGHT);
            drawBlocks();
            drawBoard();
            gd.setFill(Color.BLACK);
            gd.fillRect(panelX, 500, 100, 10);
            for (Ball e: balls)
            {
                gd.setFill(e.getColor());
                gd.fillOval(e.getX(), e.getY(), 10, 10);
            }
            checkIfAllBroken();
            notifyAll();
        }

        private void checkIfAllBroken()
        {
            boolean nextLevel = true;
            for (Brick[] e : blocks)
            {
                for (Brick f : e)
                {
                    if (f.isAlive())
                    {
                        nextLevel = false;
                    }
                }
            }
            if (nextLevel)
            {
                level++;
                loadLevel();
            }
        }

        private void drawBoard()
        {
            gd.setFill(Color.GRAY);
            gd.fillRect(0, PREF_HEIGHT-95, PREF_HEIGHT, 100);
            gd.setFill(Color.GREEN);
            gd.fillText("Score: "+score, 200, 600);

        }

        private void drawBlocks()
        {
            for (int i = 0; i < blocks.length; i++)
            {
                for (int j = 0; j < blocks[0].length; j++)
                {
                    if (blocks[i][j].isAlive()==true)
                    {
                        gd.setFill(Color.BLACK);
                        gd.fillRect(j*40, i*20, 40, 20);
                        gd.setFill(blocks[i][j].getColor());
                        gd.fillRect(j*40+5, i*20+5, 30, 10);
                    }
                }
            }
        }

        private void handleBall()
        {
            ArrayList<Integer> indexes = new ArrayList<Integer>();
            ArrayList<Integer> ballsToDupe = new ArrayList<Integer>();
            for (int i = 0; i < balls.size(); i++)
            {
                if (balls.get(i).getX() <= 5 || balls.get(i).getX() >= PREF_WIDTH-5)
                {
                    if (balls.get(i).getX() <= 5)
                    {
                        balls.get(i).setX(6);
                    }
                    else
                    {
                        balls.get(i).setX(PREF_WIDTH-6);
                    }
                    balls.get(i).reverseX();
                }
                if (balls.get(i).getY() <= 5 || balls.get(i).getY() >= PREF_HEIGHT-105)
                {
                    if (balls.get(i).getY() >= PREF_HEIGHT-105 && i != 0)
                    {
                        balls.remove(i);
                        i--;
                    }
                    else {
                        balls.get(i).reverseY();
                        if (balls.get(i).getY() >= PREF_HEIGHT-105)
                        {
                            timer.stop();
                            saveScore(playerName,score);
                            Alert gameOver = new Alert(Alert.AlertType.CONFIRMATION);
                            gameOver.setTitle("Game Over");
                            gameOver.setHeaderText(null);
                            gameOver.setContentText("Please select the options below");

                            ButtonType restart_btn = new ButtonType("Play again");
                            ButtonType seeScore_btn = new ButtonType("Score");

                            gameOver.getButtonTypes().setAll(restart_btn, seeScore_btn);

                            Platform.runLater(new Runnable(){
                                @Override
                                public void run() {
                                    Optional<ButtonType> result = gameOver.showAndWait();
                                    if(result.get() == restart_btn)
                                    {
                                        initVariables();
                                        timer.start();
                                    }
                                    else if (result.get() == seeScore_btn)
                                    {
                                        ScoreViewManager scoreView = new ScoreViewManager();
                                        try {
                                            scoreView.createScoreView(gameStage);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            });
                        }
                    }
                }
                if (balls.get(i).getX() >= panelX-balls.get(i).getXV() && balls.get(i).getX() <= panelX+100+balls.get(i).getXV() && (balls.get(i).getY() > 495 && balls.get(i).getY() < 515))
                {
                    if ((balls.get(i).getY() > 495 && balls.get(i).getYV() > 0) || (balls.get(i).getY() < 515 && balls.get(i).getYV() < 0))
                    {
                        balls.get(i).reverseY();
                        balls.get(i).changeX();
                    }
                    else
                    {
                        balls.get(i).reverseX();
                    }
                }
                boolean a = detectCollisionWithBricks(balls.get(i));
                if (a)
                {
                    ballsToDupe.add(i);
                    indexes.add(i);
                }
                balls.get(i).update();
            }

            for (Integer a : ballsToDupe)
            {
                balls.add(new Ball(balls.get(a).getX(), balls.get(a).getY()+10, balls.get(indexes.get(0)).getXV()*((int) (Math.random()*2))-1, balls.get(a).getYV(), Color.CRIMSON));
                indexes.remove(0);
            }
        }

        private boolean detectCollisionWithBricks(Ball e)
        {
            boolean done = false;
            boolean addBalls = false;
            for (int i = 0; i < blocks.length && !done; i++)
            {
                for (int j = 0; j < blocks[1].length && !done; j++)
                {
                    if ((e.getX() >= j*40-5 && e.getX() <= j*40+45) && (e.getY() >= i*20-5 && e.getY() <= i*20+25) &&
                            blocks[i][j].isAlive()==true)
                    {
                        blocks[i][j].update();
                        score+=100;
                        if ((e.getY() > i*20+e.getYV() && !(e.getY() < i*20+20 -e.getYV()) && e.getYV() < 0) || (!(e.getY() > i*20+e.getYV()) && e.getY() <= i*20+15 && e.getYV() > 0))
                        {
                            e.reverseY();
                            done = true;
                        }
                        else if (!done && (e.getX() > j*40+e.getXV() && !(e.getX() < j*40+40-e.getXV())) || (!(e.getX() > j*40+e.getXV()) && e.getX() <= j*40+40-e.getXV()))
                        {
                            e.reverseX();
                            done = true;
                        }
                        if (done)
                        {
                            if (blocks[i][j].getType() == 3)
                            {
                                addBalls = true;
                            }
                        }
                    }
                }
            }
            return addBalls;
        }


        private void handlePanel()
        {
            if (left)
            {
                if(panelXV > -10)
                {
                    panelXV -= .5;
                }
            }
            else if (right)
            {
                if (panelXV < 10)
                {
                    panelXV += .5;
                }
            }
            else if (panelXV > .5)
            {
                panelXV-= .5;
            }
            else if (panelXV <= .5 && panelXV > 0)
            {
                panelXV=0;
            }
            else if (panelXV < -.5)
            {
                panelXV+= .5;
            }
            else if (panelXV >= -.5 && panelXV < 0)
            {
                panelXV = 0;
            }

            if (panelX < 0)
            {
                panelX = 0;
            }
            if (panelX > 380)
            {
                panelX = 380;
            }
            panelX+=panelXV;
        }
    }

    private void saveScore(String playerName, int s) {
        String score = String.valueOf(s);
        File scoreTxt = new File(MainMenuManager.FILE_PATH);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(scoreTxt, true));
            writer.write(playerName + "_" + score);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
