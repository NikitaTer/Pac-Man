package Pacman.Game;

import Pacman.Game.GameObjects.Space;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class GameView extends Canvas implements Runnable {

    private boolean  isRunning = false;
    public static final int WIDTH = 900;
    public static final int HIGH = 300;
    public static final String TITLE = "Pac-Man";
    private GraphicsContext gc;
    private Thread thread;
    private GameModel gameModel;

    private Image[] pacmans;
    private Image[] spaces;

    public GameView() {
        setWidth(GameView.WIDTH);
        setHeight(GameView.HIGH);

        image_ini();

        gc = this.getGraphicsContext2D();
    }

    public synchronized void start() {
        if(isRunning) return;
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        if(!isRunning) return;
        isRunning = false;
        try {
            thread.join();
        }
        catch(InterruptedException ex) {
            ex.printStackTrace();
        }
    }

//    private void tick() {
//
//    }
//
//    private void render() {
//    }

    @Override
    public void run() {
        drawing(gameModel.getGameMap());
//        long LastTime = System.nanoTime();
//        long Now;
//        double TargetTicks = 60;
//        double Delta = 0;
//        double ns = 1000000000/TargetTicks;
//
//        while(isRunning) {
//            Now = System.nanoTime();
//            Delta+=(Now - LastTime) / ns;
//            LastTime = Now;
//            while(Delta >=1) {
//                tick();
//                render();
//                Delta--;
//            }
//        }
    }

    public void drawing(int[][] Map) {
        int i, j, x = 0, y = 0;

        gc.clearRect(0, 0, WIDTH, HIGH);

        for (i = 0; i < 17; i++) {
            for (j = 0; j < 58; j++) {
                switch (Map[i][j]) {
                    case 0: {
                        Space sp = gameModel.getSpaces()[i][j];
                        if (sp.isPoint)
                            gc.drawImage(spaces[1], x, y,14,14);
                        else if (sp.isUltimate)
                            System.out.println("Not Yet");
                        else if (sp.isCherry)
                            System.out.println("Not Yet");
                        else
                            gc.drawImage(spaces[0], x, y,14,14);
                        break;
                    }
                    case 1: {
                        gc.setFill(Color.BLACK);
                        gc.fillRect(x, y, 14, 14);
                        break;
                    }
                    case 2: {
                        gc.drawImage(pacmans[0], x, y, 14, 14);
                        break;
                    }
                    case 4: {
                        gc.drawImage(spaces[0], x, y,14,14);
                        break;
                    }
                    case 5: {
                        gc.drawImage(spaces[0], x, y,14,14);
                        break;
                    }
                    default:
                        gc.setFill(Color.WHITE);
                        gc.fillRect(x, y, 14, 14);
                        break;
                }
                x += 14;
            }
            x = 0;
            y += 14;
        }
    }

    private void image_ini() {
        pacmans = new Image[1];
        spaces = new Image[2];

        Image imageWalls = new Image(GameView.class.getResourceAsStream("Walls.bmp"));
        Image imageSprites = new Image(GameView.class.getResourceAsStream("Sprites.bmp"));

        pacmans[0] = new WritableImage(imageSprites.getPixelReader(), 18, 1, 14, 14);

        spaces[0] = new WritableImage(imageWalls.getPixelReader(), 64,16,14,14);
        spaces[1] = new WritableImage(imageWalls.getPixelReader(), 0, 32,14,14);
    }

    public void setGameModel(GameModel gameModel) {
        this.gameModel = gameModel;
    }
}
