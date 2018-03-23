package Pacman.Game.GameObjects;

import Pacman.Game.GameModel;
import Pacman.Game.GameView;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.shape.Rectangle;


public class Player {

    private int posX;
    private int posY;
    /*public boolean up = false;
    public boolean down = false;
    public boolean left = false;
    public boolean right = false;
    public boolean isAlive = true;*/
    private Image image;

    public Player(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        image = new Image(GameView.class.getResourceAsStream("Sprites.png"));
        WritableImage writableImage = new WritableImage(image.getPixelReader(), 4, 1, 12, 12);
        image = writableImage;
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
