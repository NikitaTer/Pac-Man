package Pacman.Game.GameObjects;

import Pacman.Game.GameModel;
import Pacman.Game.GameView;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;


public class Player implements Runnable, EventHandler<KeyEvent> {

    private boolean isRunning = false;

    GameModel gameModel;
    GameView gameView;
    private int[][] gameMap;

    private int posX;
    private int posY;

    private Thread thread;

    public boolean isUp = false;
    public boolean isDown = false;
    public boolean isLeft = false;
    public boolean isRight = false;

    public Player(int posX, int posY, int[][] gameMap, GameModel gameModel) {
        this.posX = posX;
        this.posY = posY;
        this.gameMap = gameMap;
        this.gameModel = gameModel;
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

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    @Override
    public void run() {

    }

    @Override
    public void handle(KeyEvent event) {

        boolean flag = false;

        switch (event.getCode()) {
            case UP:
                if (gameMap[posY - 1][posX] == 0) {
                    up();
                    flag = true;
                }
                break;

            case DOWN:
                if (gameMap[posY + 1][posX] == 0) {
                    down();
                    flag = true;
                }
                break;

            case LEFT:
                if (gameMap[posY][posX - 1] == 0) {
                    left();
                    flag = true;
                }
                break;

            case RIGHT:
                if (gameMap[posY][posX + 1] == 0) {
                    right();
                    flag = true;
                }
                break;

            default:
                break;
        }

        if (flag) {
            gameView.drawing(gameMap);
            /*Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(300);
                    }
                    catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            });*/
        }
    }

    private void up() {
        if (isDown) isDown = false;
        if (isLeft) isLeft = false;
        if (isRight) isRight = false;
        if (!isUp) isUp = true;

        gameMap[posY][posX] = 0;
        gameMap[--posY][posX] = 2;
        gameModel.check(posX, posY);
    }

    private void down() {
        if (isUp) isUp = false;
        if (isLeft) isLeft = false;
        if (isRight) isRight = false;
        if (!isDown) isDown = true;

        gameMap[posY][posX] = 0;
        gameMap[++posY][posX] = 2;
        gameModel.check(posX, posY);
    }

    private void left() {
        if (isUp) isUp = false;
        if (isDown) isDown = false;
        if (isRight) isRight = false;
        if (!isLeft) isLeft = true;

        gameMap[posY][posX] = 0;
        gameMap[posY][--posX] = 2;
        gameModel.check(posX, posY);
    }

    private void right() {
        if (isUp) isUp = false;
        if (isDown) isDown = false;
        if (isLeft) isLeft = false;
        if (!isRight) isRight = true;

        gameMap[posY][posX] = 0;
        gameMap[posY][++posX] = 2;
        gameModel.check(posX, posY);
    }
}
