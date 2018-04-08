package Pacman;

import Pacman.Game.GameModel;
import Pacman.Game.GameView;
import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    private static Stage prStage;
    private static Scene menuScene;
    public static final int HEIGHT = 539;
    public static final int WIDTH = 716;
    private static ControllersManager CManager;

    @Override
    public void start(Stage prStage) {
        this.prStage = prStage;
        prStage.setTitle("Pac-Man");
        prStage.setResizable(false);
        prStage.getIcons().add(new Image(MainApp.class.getResourceAsStream("View/Icon_mini.png")));

        AnchorPane rootMenu = new AnchorPane();
        CManager = new ControllersManager(this);

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("View/MainMenu.fxml"));
            rootMenu = (AnchorPane) loader.load();
            CManager.addMainMenuController(loader.getController(), prStage);
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }

        menuScene = new Scene(rootMenu);
        menuScene.getStylesheets().add(getClass().getResource("View/My.css").toExternalForm());
        prStage.setScene(menuScene);

        prStage.show();
    }

    public void StartMenu() {
        prStage.setHeight(HEIGHT);
        prStage.setWidth(WIDTH);
        prStage.setScene(menuScene);
    }

    public void StartGame() {
        Pane rootGame = new Pane();
        Scene gameScene = new Scene(rootGame);

        GameView gameView = new GameView();
        GameModel gameModel = new GameModel(58, 17, rootGame, this);
        gameModel.setGameView(gameView);
        gameView.setGameModel(gameModel);

        prStage.setHeight(GameView.HIGH);
        prStage.setWidth(GameView.WIDTH);
        prStage.setScene(gameScene);

        rootGame.getChildren().add(gameView);

        gameScene.addEventHandler(KeyEvent.KEY_PRESSED, gameModel);
        gameScene.addEventHandler(KeyEvent.KEY_PRESSED, gameModel.getPac());

        gameView.start();
        gameModel.start();
    }

    public static void main(String[] argc) {
        launch(argc);
    }
}
