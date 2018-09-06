package Pacman.Game.GameObjects;

import Pacman.Game.GameModel;
import Pacman.Game.GameView;
import Pacman.Replay.PacManData;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

import java.util.Vector;

public class PacMan implements Runnable, EventHandler<KeyEvent> {
    private volatile boolean isRunning = false;
    private Thread thread;

    private int posX, posY;
    private GameMap gameMap;
    private GameModel gameModel;
    private GameView gameView;

    private volatile Vector<PacManData> vector;
    private long time;

    private volatile boolean isUp = false;
    private volatile boolean isDown = false;
    private volatile boolean isLeft = false;
    private volatile boolean isRight = false;
    private volatile boolean isTurnUp = false;
    private volatile boolean isTurnDown = false;
    private volatile boolean isTurnLeft = false;
    private volatile boolean isTurnRight = false;

    private boolean isGame;
    private boolean isOn;

    public PacMan(int posX, int posY, GameModel gameModel, GameView gameView, boolean isGame) {
        this.posX = posX;
        this.posY = posY;
        this.gameModel = gameModel;
        this.gameMap = gameModel.getGameMap();
        this.gameView = gameView;
        this.isGame = isGame;
        if (isGame)
            vector = new Vector<>();
    }

    public synchronized void start() {
        if (isRunning) return;
        thread = new Thread(this);
        thread.setName("pacMan's Thread");
        isRunning = true;
        thread.start();
    }

    public synchronized void stop() {
        if (!isRunning) return;
        if (!isGame)
            isOn = false;
        isRunning = false;
        wayFalse();
        turnFalse();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (isGame)
            vector.add(new PacManData(System.nanoTime() - time, 4));
    }

    @Override
    public void run() {
        synchronized (gameMap) {
            gameView.firstDraw();
        }

        if (isGame) {
            time = System.nanoTime();
            while (isRunning) {
                try {
                    while (isUp) {
                        synchronized (gameMap) {
                            if (up())
                                break;
                        }
                        Thread.sleep(150);
                    }

                    while (isDown) {
                        synchronized (gameMap) {
                            if (down())
                                break;
                        }
                        Thread.sleep(150);
                    }

                    while (isLeft) {
                        synchronized (gameMap) {
                            if (left())
                                break;
                        }
                        Thread.sleep(150);
                    }

                    while (isRight) {
                        synchronized (gameMap) {
                            if (right())
                                break;
                        }
                        Thread.sleep(150);
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }

        else {
            isOn = true;
            while (isOn) {
                PacManData temp;
                if (!vector.isEmpty())
                    temp = vector.remove(0);
                else {
                    isOn = false;
                    break;
                }
                switch (temp.getWay()) {
                    case 0:
                        try {
                            Thread.sleep(temp.getDelay() / 1000000);
                            synchronized (gameMap) {
                                wayFalse();
                                isUp = true;
                                up();
                            }
                         //   Thread.sleep(150);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        break;

                    case 1:
                        try {
                            Thread.sleep(temp.getDelay() / 1000000);
                            synchronized (gameMap) {
                                wayFalse();
                                isDown = true;
                                down();
                            }
                          //  Thread.sleep(150);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        break;

                    case 2:
                        try {
                            Thread.sleep(temp.getDelay() / 1000000);
                            synchronized (gameMap) {
                                wayFalse();
                                isLeft = true;
                                left();
                            }
                         //   Thread.sleep(150);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        break;

                    case 3:
                        try {
                            Thread.sleep(temp.getDelay() / 1000000);
                            synchronized (gameMap) {
                                wayFalse();
                                isRight = true;
                                right();
                            }
                          //  Thread.sleep(150);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        break;

                        default:
                            try {
                                Thread.sleep(temp.getDelay() / 1000000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            isOn = false;
                            break;

                }
            }
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    gameModel.stop();
                }
            });
        }
    }

    @Override
    public void handle(KeyEvent event) {
        switch (event.getCode()) {
            case UP:
                turnFalse();
                if (gameMap.getPos(posY - 1, posX) != 1 && gameMap.getPos(posY - 1, posX) != 5) {
                    wayFalse();
                    isUp = true;
                }
                else
                    isTurnUp = true;
                break;

            case DOWN:
                turnFalse();
                if (gameMap.getPos(posY + 1, posX) != 1 && gameMap.getPos(posY + 1, posX) != 5) {
                    wayFalse();
                    isDown = true;
                }
                else
                    isTurnDown = true;
                break;

            case LEFT:
                turnFalse();
                if (gameMap.getPos(posY, posX - 1) != 1 && gameMap.getPos(posY, posX - 1) != 5) {
                    wayFalse();
                    isLeft = true;
                }
                else
                    isTurnLeft = true;
                break;

            case RIGHT:
                turnFalse();
                if (gameMap.getPos(posY, posX + 1) != 1 && gameMap.getPos(posY, posX + 1) != 5) {
                    wayFalse();
                    isRight = true;
                }
                else
                    isTurnRight = true;
                break;

            default:
                break;
        }
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
        if (isRight) isRight= false;
    }

    public void setVector(Vector<PacManData> vector) {
        this.vector = new Vector<>(vector);
    }

    public Vector<PacManData> getVector() {
        return vector;
    }

    private boolean up() {
            if (isTurnLeft && gameMap.getPos(posY, posX - 1) !=1 && gameMap.getPos(posY,posX - 1) !=5) {
                turnFalse();
                isUp = false;
                isLeft = true;
                return true;
            }

            if (isTurnRight && gameMap.getPos(posY, posX + 1) !=1 && gameMap.getPos(posY,posX + 1) !=5) {
                turnFalse();
                isUp = false;
                isRight = true;
                return true;
            }

            if (gameMap.getPos(posY - 1, posX) == 1 || gameMap.getPos(posY - 1, posX) == 5) {
                isUp = false;
                return false;
            }

            if (isGame)
                vector.add(new PacManData(System.nanoTime() - time, 0));
            gameMap.chPos(posY, posX, --posY, posX, 2);
            gameModel.check(posY, posX);
            time = System.nanoTime();
            return false;
    }

    private boolean down() {
            if (isTurnLeft && gameMap.getPos(posY, posX - 1) != 1 && gameMap.getPos(posY,posX - 1) != 5) {
                turnFalse();
                isDown = false;
                isLeft = true;
                return true;
            }

            if (isTurnRight && gameMap.getPos(posY, posX + 1) != 1 && gameMap.getPos(posY,posX + 1) != 5) {
                turnFalse();
                isDown = false;
                isRight = true;
                return true;
            }

            if (gameMap.getPos(posY + 1, posX) == 1 || gameMap.getPos(posY + 1, posX) == 5) {
                isDown = false;
                return false;
            }

            if (isGame)
                vector.add(new PacManData(System.nanoTime() - time, 1));
            gameMap.chPos(posY, posX, ++posY, posX, 2);
            gameModel.check(posY, posX);
            time = System.nanoTime();
            return false;
    }

    private boolean left() {
            if (isTurnUp && gameMap.getPos(posY - 1, posX) != 1 && gameMap.getPos(posY - 1,posX) != 5) {
                turnFalse();
                isLeft = false;
                isUp = true;
                return true;
            }

            if (isTurnDown && gameMap.getPos(posY + 1, posX) != 1 && gameMap.getPos(posY + 1,posX ) != 5) {
                turnFalse();
                isLeft = false;
                isDown = true;
                return true;
            }

            if (gameMap.getPos(posY, posX - 1) == 1 || gameMap.getPos(posY, posX - 1) == 5) {
                isLeft = false;
                return false;
            }

            if (isGame)
                vector.add(new PacManData(System.nanoTime() - time, 2));
            gameMap.chPos(posY, posX, posY, --posX, 2);
            gameModel.check(posY, posX);
            time = System.nanoTime();
            return false;
    }

    private boolean right() {
            if (isTurnUp && gameMap.getPos(posY - 1, posX) != 1 && gameMap.getPos(posY - 1,posX) != 5) {
                turnFalse();
                isRight = false;
                isUp = true;
                return true;
            }

            if (isTurnDown && gameMap.getPos(posY + 1, posX) != 1 && gameMap.getPos(posY + 1,posX ) != 5) {
                turnFalse();
                isRight = false;
                isDown = true;
                return true;
            }

            if (gameMap.getPos(posY, posX + 1) == 1 || gameMap.getPos(posY, posX + 1) == 5) {
                isRight = false;
                return false;
            }

            if (isGame)
                vector.add(new PacManData(System.nanoTime() - time, 3));
            gameMap.chPos(posY, posX, posY, ++posX, 2);
            gameModel.check(posY, posX);
            time = System.nanoTime();
            return false;
    }

    public boolean isUp() {
        return isUp;
    }

    public boolean isDown() {
        return isDown;
    }

    public boolean isLeft() {
        return isLeft;
    }

    public boolean isRight() {
        return isRight;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public Thread getThread() {
        return thread;
    }
}
