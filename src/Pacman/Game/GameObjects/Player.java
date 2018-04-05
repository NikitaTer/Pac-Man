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

    public volatile boolean isUp = false;
    public volatile boolean isDown = false;
    public volatile boolean isLeft = false;
    public volatile boolean isRight = false;
    public volatile boolean isTurnUp = false;
    public volatile boolean isTurnDown = false;
    public volatile boolean isTurnLeft = false;
    public volatile boolean isTurnRight = false;

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

        wayFalse();
        turnFalse();
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

        while (isRunning) {
            try {
                while (isUp) {
                    if (up())
                        break;
                    gameView.drawing(gameMap);
                    Thread.sleep(150);
                }

                while (isDown) {
                    if (down())
                        break;
                    gameView.drawing(gameMap);
                    Thread.sleep(150);
                }

                while (isLeft) {
                    if (left())
                        break;
                    gameView.drawing(gameMap);
                    Thread.sleep(150);
                }

                while (isRight) {
                    if (right())
                        break;
                    gameView.drawing(gameMap);
                    Thread.sleep(150);
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void handle(KeyEvent event) {

        switch (event.getCode()) {
            case UP:
                turnFalse();
                if (gameMap[posY - 1][posX] == 0) {
                    wayFalse();
                    if (!isUp) isUp = true;
                }
                else
                    if (!isTurnUp) isTurnUp = true;
                break;

            case DOWN:
                turnFalse();
                if (gameMap[posY + 1][posX] == 0) {
                    wayFalse();
                    if (!isDown) isDown = true;
                }
                else
                    if (!isTurnDown) isTurnDown = true;
                break;

            case LEFT:
                turnFalse();
                if (gameMap[posY][posX - 1] == 0) {
                    wayFalse();
                    if (!isLeft) isLeft = true;
                }
                else
                    if (!isTurnLeft) isTurnLeft = true;
                break;

            case RIGHT:
                turnFalse();
                if (gameMap[posY][posX + 1] == 0) {
                    wayFalse();
                    if (!isRight) isRight = true;
                }
                else
                    if (!isTurnRight) isTurnRight = true;
                break;

            default:
                break;
        }
    }

    private boolean up() {
        if (isTurnLeft && gameMap[posY][posX - 1] == 0) {
            isUp = false;
            turnFalse();
            isLeft = true;
            return true;
        }
        if (isTurnRight && gameMap[posY][posX + 1] == 0) {
            isUp = false;
            turnFalse();
            isRight = true;
            return true;
        }

        if (gameMap[posY - 1][posX] != 0) {
            isUp = false;
            return false;
        }

        gameMap[posY][posX] = 0;
        gameMap[--posY][posX] = 2;
        gameModel.check(posX, posY);
        return false;
    }

    private boolean down() {
        if (isTurnLeft && gameMap[posY][posX - 1] == 0) {
            isDown = false;
            turnFalse();
            isLeft = true;
            return true;
        }
        if (isTurnRight && gameMap[posY][posX + 1] == 0) {
            isDown = false;
            turnFalse();
            isRight = true;
            return true;
        }

        if (gameMap[posY + 1][posX] != 0) {
            isDown = false;
            return false;
        }
        gameMap[posY][posX] = 0;
        gameMap[++posY][posX] = 2;
        gameModel.check(posX, posY);
        return false;
    }

    private boolean left() {
        if (isTurnUp && gameMap[posY - 1][posX] == 0) {
            isLeft = false;
            turnFalse();
            isUp = true;
            return true;
        }
        if (isTurnDown && gameMap[posY + 1][posX] == 0) {
            isLeft = false;
            turnFalse();
            isDown = true;
            return true;
        }

        if (gameMap[posY][posX - 1] != 0) {
            isLeft = false;
            return false;
        }
        gameMap[posY][posX] = 0;
        gameMap[posY][--posX] = 2;
        gameModel.check(posX, posY);
        return  false;
    }

    private boolean right() {
        if (isTurnUp && gameMap[posY - 1][posX] == 0) {
            isRight = false;
            turnFalse();
            isUp = true;
            return true;
        }
        if (isTurnDown && gameMap[posY + 1][posX] == 0) {
            isRight = false;
            turnFalse();
            isDown = true;
            return true;
        }

        if (gameMap[posY][posX + 1] != 0) {
            isRight = false;
            return false;
        }
        gameMap[posY][posX] = 0;
        gameMap[posY][++posX] = 2;
        gameModel.check(posX, posY);
        return false;
    }

    private void turnFalse() {
        if (isTurnUp) isTurnUp = false;
        if (isTurnDown) isTurnDown = false;
        if (isTurnLeft) isTurnLeft = false;
        if (isTurnRight) isTurnRight = false;
    }

    private void wayFalse() {
        if (isUp) isUp = false;
        if (isDown) isDown = false;
        if (isLeft) isLeft = false;
        if (isRight) isRight = false;
    }
}
