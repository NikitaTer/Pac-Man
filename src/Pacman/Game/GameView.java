package Pacman.Game;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class GameView extends Canvas implements Runnable {

    private boolean  isRunning = false;
    public static final int WIDTH = 700;
    public static final int HIGH = 500;
    public static final String TITLE = "Pac-Man";
    private GraphicsContext gc;
    private Thread thread;
    private GameModel gameModel;

    private Point2D pos;

    public GameView() {
        setWidth(GameView.WIDTH);
        setHeight(GameView.HIGH);

        gc = this.getGraphicsContext2D();
        gc.setFill(Color.YELLOW);
        gc.setStroke(Color.BROWN);
        pos = new Point2D(15, 15);
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
        System.out.println("Stoped");
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

        gc.clearRect(0,0, WIDTH, HIGH);

        for(i = 0; i < 17; i++) {
            for (j = 0; j < 58; j++) {
                switch (Map[i][j]) {
                    case 0:
                        gc.setFill(Color.LIGHTGRAY);
                        gc.fillRect(x, y, 12, 12);
                        break;
                    case 1:
                        gc.setFill(Color.BLACK);
                        gc.fillRect(x, y, 12, 12);
                        break;
                    case 2:
                        gc.setFill(Color.YELLOW);
                        Image image = new Image(GameView.class.getResourceAsStream("Sprites.png"));
                        WritableImage writableImage = new WritableImage(image.getPixelReader(), 4, 1, 12, 12);
                        gc.drawImage(writableImage, x,y,12,12);
                        break;
                    case 4:
                        gc.setFill(Color.RED);
                        gc.fillRect(x, y, 12, 12);
                        break;
                    case 5:
                        gc.setFill(Color.BLUE);
                        gc.fillRect(x, y, 12, 12);
                        break;
                    default:
                        gc.setFill(Color.WHITE);
                        gc.fillRect(x, y, 12, 12);
                        break;
                }
                x += 12;
            }
            x = 0;
            y+=12;
        }
    }

    public void setGameModel(GameModel gameModel) {
        this.gameModel = gameModel;
    }
}
