package Pacman.Game;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Test extends Application {

    private Stage prStage;
    private static GameModel gameModel;
    private static GameView gameView;

    @Override
    public void start(Stage prStage) {
        this.prStage = prStage;
        this.prStage.setTitle("DEMO");
        this.prStage.setResizable(false);
        prStage.setWidth(710);
        prStage.setHeight(500);

        Pane root = new Pane();
        Scene scene = new Scene(root);
        prStage.setScene(scene);

        gameView = new GameView();
        gameModel = new GameModel(58, 17, root);
        gameModel.setGameView(gameView);
        gameView.setGameModel(gameModel);

        root.getChildren().add(gameView);

        scene.addEventHandler(KeyEvent.KEY_RELEASED, gameModel);

        gameView.start();

        prStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        gameView.stop();
    }
}
