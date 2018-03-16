package Pacman.Game;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Test extends Application {

    private Stage prStage;
    private Canvas canvas;

    @Override
    public void start(Stage prStage) {
        this.prStage = prStage;
        this.prStage.setTitle("DEMO");
        this.prStage.setResizable(false);
        prStage.setWidth(710);
        prStage.setHeight(500);
        Group root = new Group();
        canvas = new Canvas(700, 500);
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        prStage.setScene(scene);

        Image Map = new Image(Test.class.getResourceAsStream("Level_1_demo.png"));

        GameModel gameModel = new GameModel(58, 17, Map);

        drawing(gameModel.getGameMap());

        prStage.show();
    }

    private void drawing(int[][] Map) {
        int i, j, x = 0, y = 0;
        GraphicsContext gc = canvas.getGraphicsContext2D();

        for(i = 0; i < 17; i++) {
            for (j = 0; j < 58; j++) {
                switch (Map[i][j]) {
                    case 0:
                        gc.setFill(Color.LIGHTGRAY);
                        break;
                    case 1:
                        gc.setFill(Color.BLACK);
                        break;
                    case 2:
                        gc.setFill(Color.YELLOW);
                        break;
                    case 4:
                        gc.setFill(Color.RED);
                        break;
                    case 5:
                        gc.setFill(Color.BLUE);
                        break;
                    default:
                        gc.setFill(Color.WHITE);
                        break;
                }
                gc.fillRect(x, y, 12, 12);
                x += 12;
            }
            x = 0;
            y+=12;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
