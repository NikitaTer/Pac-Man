package Pacman.Game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SpriteTest extends Application {

    private Stage prStage;

    @Override
    public void start(Stage prStage) {
        this.prStage = prStage;
        prStage.setTitle("SpriteTest");
        prStage.setWidth(700);
        prStage.setHeight(500);
        Pane root = new Pane();
        Canvas canvas = new Canvas();
        root.getChildren().add(canvas);
        prStage.setScene(new Scene(root));

        prStage.show();
    }

    private void drawing(GraphicsContext gc) {

    }

    public static void main(String[] args) {
        launch(args);
    }
}