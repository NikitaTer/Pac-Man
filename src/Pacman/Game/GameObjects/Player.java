package Pacman.Game.GameObjects;

import Pacman.Game.GameView;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;


public class Player implements Runnable {

    private boolean isRunning = false;

    private int posX;
    private int posY;
    private Image image;
    private Thread thread;

    public boolean isUp = false;
    public boolean isDown = false;
    public boolean isLeft = false;
    public boolean isRight = false;

    public Player(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        image = new Image(GameView.class.getResourceAsStream("Sprites.bmp"));
        WritableImage writableImage = new WritableImage(image.getPixelReader(), 4, 1, 12, 12);
        image = writableImage;
    }

    public synchronized void start() {
        if (isRunning) return;
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        if (!isRunning) return;
        isRunning = false;
        try {
            thread.join();
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public void sleep() {
        try {
            Thread.sleep(100);
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {

    }

    public Image getImage() {
        return image;
    }

    public int up() {
        posY--;
        return posY + 1;
    }

    public int down() {
        posY++;
        return posY - 1;
    }

    public int left() {
        posX--;
        return posX + 1;
    }

    public int right() {
        posX++;
        return posX - 1;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
}
