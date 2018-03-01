package Pacman.Model;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Test extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage prStage) {
        prStage.setTitle(Game.TITLE);
        prStage.setWidth(Game.WIDTH);
        prStage.setHeight(Game.HIGH);
        prStage.setResizable(false);
        AnchorPane rootPane = new AnchorPane();
        Scene scene = new Scene(rootPane);
        Game game = new Game();
        rootPane.getChildren().add(game);
        prStage.setScene(scene);
        game.start();
        prStage.show();
        prStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                game.stop();
            }
        });
    }
}
