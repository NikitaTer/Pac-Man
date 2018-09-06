package Pacman.Game.GameObjects;

import Pacman.Game.GameModel;
import Pacman.Game.GameView;
import javafx.application.Platform;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class Ghost implements Runnable {
    private volatile boolean isRunning = false;
    private Thread thread;

    private volatile boolean isWeak = false;
    private volatile boolean isUp = false;
    private volatile boolean isDown = false;
    private volatile boolean isLeft = false;
    private volatile boolean isRight = true;
    private volatile boolean onBase = true;

    private boolean isGame;
    private boolean isOn;
    private volatile Vector<Integer> vector;
    private boolean isWait = false;

    private GameView gameView;
    private GameModel gameModel;
    private GameMap gameMap;
    private int delay;

    private int posX, posY, resPosX, resPosY;

    public Ghost(int posX, int posY, GameModel gameModel, GameView gameView, int delay, boolean isGame) {
        this.delay = delay;
        this.posX = posX;
        this.posY = posY;
        resPosX = posX;
        resPosY = posY;
        this.gameModel = gameModel;
        this.gameView = gameView;
        gameMap = gameModel.getGameMap();
        this.isGame = isGame;
        if (isGame)
            vector = new Vector<>();
    }

    public synchronized void start(int num) {
        if (isRunning) return;
        thread = new Thread(this);
        thread.setName("Ghost thread " + num);
        isRunning = true;
        thread.start();
    }

    public synchronized void stop() {
        if (!isRunning) return;
        if (!isGame)
            isOn = false;
        isRunning = false;
        if (isUp) isUp = false;
        if (isDown) isDown = false;
        if (isLeft) isLeft = false;
        if (isRight) isRight = false;
        thread.interrupt();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (isGame)
            vector.add(4);
    }

    @Override
    public void run() {
        if (isGame) {
            while (isRunning) {
                try {
                    if (onBase) {
                        onBase = false;
                        Thread.sleep(delay);
                        if (!isRunning)
                            break;
                        script();
                        if (!isRunning)
                            break;
                    }

                    while (isUp && isRunning) {
                        synchronized (gameMap) {
                            if (!isUp) continue;
                            if (gameMap.getPos(posY, posX - 1) != 1 && gameMap.getPos(posY, posX - 1) != 5 && gameMap.getPos(posY, posX + 1) != 1 && gameMap.getPos(posY, posX + 1) != 5 && gameMap.getPos(posY - 1, posX) != 1 && gameMap.getPos(posY - 1, posX) != 5) {
                                waySwitch(choose(1, 4));
                            } else if ((gameMap.getPos(posY, posX - 1) != 1 && gameMap.getPos(posY, posX - 1) != 5 && gameMap.getPos(posY, posX + 1) != 1 && gameMap.getPos(posY, posX + 1) != 5)
                                    || (gameMap.getPos(posY, posX - 1) != 1 && gameMap.getPos(posY, posX - 1) != 5 && gameMap.getPos(posY - 1, posX) != 1 && gameMap.getPos(posY - 1, posX) != 5)
                                    || (gameMap.getPos(posY, posX + 1) != 1 && gameMap.getPos(posY, posX + 1) != 5 && gameMap.getPos(posY - 1, posX) != 1 && gameMap.getPos(posY - 1, posX) != 5)) {
                                waySwitch(choose(1, 3));
                            } else if ((gameMap.getPos(posY, posX - 1) != 1 && gameMap.getPos(posY, posX - 1) != 5)
                                    || (gameMap.getPos(posY, posX + 1) != 1 && gameMap.getPos(posY, posX + 1) != 5)) {
                                waySwitch(choose(1, 2));
                            }
                            if (isUp) up();
                            else if (isDown) down();
                            else if (isLeft) left();
                            else if (isRight) right();
                        }
                        Thread.sleep(200);
                    }

                    while (isDown && isRunning) {
                        synchronized (gameMap) {
                            if (!isDown) continue;
                            if (gameMap.getPos(posY, posX - 1) != 1 && gameMap.getPos(posY, posX - 1) != 5 && gameMap.getPos(posY, posX + 1) != 1 && gameMap.getPos(posY, posX + 1) != 5 && gameMap.getPos(posY + 1, posX) != 1 && gameMap.getPos(posY + 1, posX) != 5) {
                                waySwitch(choose(0, 4));
                            } else if ((gameMap.getPos(posY, posX - 1) != 1 && gameMap.getPos(posY, posX - 1) != 5 && gameMap.getPos(posY, posX + 1) != 1 && gameMap.getPos(posY, posX + 1) != 5)
                                    || (gameMap.getPos(posY, posX - 1) != 1 && gameMap.getPos(posY, posX - 1) != 5 && gameMap.getPos(posY + 1, posX) != 1 && gameMap.getPos(posY + 1, posX) != 5)
                                    || (gameMap.getPos(posY, posX + 1) != 1 && gameMap.getPos(posY, posX + 1) != 5 && gameMap.getPos(posY + 1, posX) != 1 && gameMap.getPos(posY + 1, posX) != 5)) {
                                waySwitch(choose(0, 3));
                            } else if ((gameMap.getPos(posY, posX - 1) != 1 && gameMap.getPos(posY, posX - 1) != 5)
                                    || (gameMap.getPos(posY, posX + 1) != 1 && gameMap.getPos(posY, posX + 1) != 5)) {
                                waySwitch(choose(0, 2));
                            }
                            if (isUp) up();
                            else if (isDown) down();
                            else if (isLeft) left();
                            else if (isRight) right();
                        }
                        Thread.sleep(200);
                    }

                    while (isLeft && isRunning) {
                        synchronized (gameMap) {
                            if (!isLeft) continue;
                            if (gameMap.getPos(posY, posX - 1) != 1 && gameMap.getPos(posY, posX - 1) != 5 && gameMap.getPos(posY + 1, posX) != 1 && gameMap.getPos(posY + 1, posX) != 5 && gameMap.getPos(posY - 1, posX) != 1 && gameMap.getPos(posY - 1, posX) != 5) {
                                waySwitch(choose(3, 4));
                            } else if ((gameMap.getPos(posY - 1, posX) != 1 && gameMap.getPos(posY - 1, posX) != 5 && gameMap.getPos(posY + 1, posX) != 1 && gameMap.getPos(posY + 1, posX) != 5)
                                    || (gameMap.getPos(posY, posX - 1) != 1 && gameMap.getPos(posY, posX - 1) != 5 && gameMap.getPos(posY - 1, posX) != 1 && gameMap.getPos(posY - 1, posX) != 5)
                                    || (gameMap.getPos(posY, posX - 1) != 1 && gameMap.getPos(posY, posX - 1) != 5 && gameMap.getPos(posY + 1, posX) != 1 && gameMap.getPos(posY + 1, posX) != 5)) {
                                waySwitch(choose(3, 3));
                            } else if ((gameMap.getPos(posY + 1, posX) != 1 && gameMap.getPos(posY + 1, posX) != 5)
                                    || (gameMap.getPos(posY - 1, posX) != 1 && gameMap.getPos(posY - 1, posX) != 5)) {
                                waySwitch(choose(3, 2));
                            }
                            if (isUp) up();
                            else if (isDown) down();
                            else if (isLeft) left();
                            else if (isRight) right();
                        }
                        Thread.sleep(200);
                    }

                    while (isRight && isRunning) {
                        synchronized (gameMap) {
                            if (!isRight) continue;
                            if (gameMap.getPos(posY, posX + 1) != 1 && gameMap.getPos(posY, posX + 1) != 5 && gameMap.getPos(posY + 1, posX) != 1 && gameMap.getPos(posY + 1, posX) != 5 && gameMap.getPos(posY - 1, posX) != 1 && gameMap.getPos(posY - 1, posX) != 5) {
                                waySwitch(choose(2, 4));
                            } else if ((gameMap.getPos(posY, posX + 1) != 1 && gameMap.getPos(posY, posX + 1) != 5 && gameMap.getPos(posY - 1, posX) != 1 && gameMap.getPos(posY - 1, posX) != 5)
                                    || (gameMap.getPos(posY, posX + 1) != 1 && gameMap.getPos(posY, posX + 1) != 5 && gameMap.getPos(posY + 1, posX) != 1 && gameMap.getPos(posY + 1, posX) != 5)) {
                                waySwitch(choose(2, 3));
                            } else if ((gameMap.getPos(posY + 1, posX) != 1 && gameMap.getPos(posY + 1, posX) != 5)
                                    || (gameMap.getPos(posY - 1, posX) != 1 && gameMap.getPos(posY - 1, posX) != 5)) {
                                waySwitch(choose(2, 2));
                            }
                            if (isUp) up();
                            else if (isDown) down();
                            else if (isLeft) left();
                            else if (isRight) right();
                        }
                        Thread.sleep(200);
                    }
                } catch (InterruptedException ex) {

                }
            }
        }
        else {
            isOn = true;
            while (isOn) {
                try {
                    if (onBase) {
                        onBase = false;
                        Thread.sleep(delay);
                        if (!isOn)
                            break;
                        script();
                        if (!isOn)
                            break;
                    }

                    if (isWait) {
                        isWait = false;
                        Thread.sleep(200);
                    }
                    int temp;
                    if (!vector.isEmpty())
                        temp = vector.remove(0);
                    else {
                        isOn = false;
                        break;
                    }
                    switch (temp) {
                        case 0:
                            synchronized (gameMap) {
                                up();
                            }
                            Thread.sleep(200);
                            break;

                        case 1:
                            synchronized (gameMap) {
                                down();
                            }
                            Thread.sleep(200);
                            break;

                        case 2:
                            synchronized (gameMap) {
                                left();
                            }
                            Thread.sleep(200);
                            break;

                        case 3:
                            synchronized (gameMap) {
                                right();
                            }
                            Thread.sleep(200);
                            break;

                            default:
                                isOn = false;
                                break;
                    }
                } catch (InterruptedException ex) {

                }
            }
        }

    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setVector(Vector<Integer> vector) {
        this.vector = new Vector<>(vector);
    }

    public Vector<Integer> getVector() {
        return vector;
    }

    public boolean isWeak() {
        return isWeak;
    }

    private int choose(int from, int count) {

        int res = (int) (Math.random() * 101);
        boolean isUpFree = findWay(0);
        boolean isDownFree = findWay(1);
        boolean isLeftFree = findWay(2);
        boolean isRightFree = findWay(3);

        switch (count) {
            case 2:
                if (res <= 10)
                    return from;
                if (isUpFree && from != 0)
                    return 0;
                if (isDownFree && from != 1)
                    return 1;
                if (isLeftFree && from != 2)
                    return 2;
                if (isRightFree && from != 3)
                    return 3;
                break;

            case 3:
                if (res <= 10)
                    return from;
                if (res <= 55) {
                    if (isUpFree && from != 0)
                        return 0;
                    if (isDownFree && from != 1)
                        return 1;
                    if (isLeftFree && from != 2)
                        return 2;
                    if (isRightFree && from != 3)
                        return 3;
                }
                if (res <= 100) {
                    if (isRightFree && from != 3)
                        return 3;
                    if (isLeftFree && from != 2)
                        return 2;
                    if (isDownFree && from != 1)
                        return 1;
                    if (isUpFree && from != 0)
                        return 0;
                }
                break;

            case 4:
                switch (from) {
                    case 0:
                        if (res <= 10)
                            return 0;
                        if (res <= 40)
                            return 1;
                        if (res <= 70)
                            return 2;
                        if (res <= 100)
                            return 3;
                        break;

                    case 1:
                        if (res <= 10)
                            return 1;
                        if (res <= 40)
                            return 2;
                        if (res <= 70)
                            return 3;
                        if (res <= 100)
                            return 0;
                        break;

                    case 2:
                        if (res <= 10)
                            return 2;
                        if (res <= 40)
                            return 3;
                        if (res <= 70)
                            return 0;
                        if (res <= 100)
                            return 1;
                        break;

                    case 3:
                        if (res <= 10)
                            return 3;
                        if (res <= 40)
                            return 0;
                        if (res <= 70)
                            return 1;
                        if (res <= 100)
                            return 2;
                        break;

                        default:
                            return 0;
                }
                break;

                default:
                    return 0;
        }
        return 0;
    }

    private boolean findWay(int way) {
        switch (way) {
            case 0:
                if (gameMap.getPos(posY - 1, posX) != 1 && gameMap.getPos(posY - 1, posX) != 5)
                    return true;
                return false;

            case 1:
                if (gameMap.getPos(posY + 1, posX) != 1 && (posY + 1 != 5 && (posX != 35 && posX != 36)))
                    return true;
                return false;

            case 2:
                if (gameMap.getPos(posY, posX - 1) != 1 && gameMap.getPos(posY, posX - 1) != 5)
                    return true;
                return false;

            case 3:
                if (gameMap.getPos(posY, posX + 1) != 1 && gameMap.getPos(posY, posX + 1) != 5)
                    return true;
                return false;

                default:
                    return false;
        }
    }

    private void waySwitch(int way) {
        if (isUp) isUp = false;
        if (isDown) isDown = false;
        if (isLeft) isLeft = false;
        if (isRight) isRight = false;

        if (way == 0)
            isUp = true;
        else if (way == 1)
            isDown = true;
        else if (way == 2)
            isLeft = true;
        else if (way == 3)
            isRight = true;
    }

    private void script() throws InterruptedException {
        boolean first = true;
        boolean twice = false;
        boolean inCage = true;
        while (inCage) {
            synchronized (gameMap) {
                if (first) {
                    first = false;
                    if (gameMap.getPos(posY, posX - 2) == 1) {
                        gameMap.chPos(posY, posX, posY, ++posX, 4);
                    }
                    else if (gameMap.getPos(posY, posX + 2) == 1) {
                        gameMap.chPos(posY, posX, posY, --posX, 4);
                    }
                    else if (gameMap.getPos(posY, posX - 1) == 1) {
                        twice = true;
                        gameMap.chPos(posY, posX, posY, ++posX, 4);
                    }
                    else if (gameMap.getPos(posY, posX + 1) == 1) {
                        twice = true;
                        gameMap.chPos(posY, posX, posY, --posX, 4);
                    }
                }

                else if (twice) {
                    twice = false;
                    if (gameMap.getPos(posY, posX - 2) == 1) {
                        gameMap.chPos(posY, posX, posY, ++posX, 4);
                    }
                    else if (gameMap.getPos(posY, posX + 2) == 1) {
                        gameMap.chPos(posY, posX, posY, --posX, 4);
                    }
                }
                else {
                    gameMap.chPos(posY, posX, --posY, posX, 4);
                    if (gameMap.getPos(posY - 1, posX) == 1)
                        inCage = false;
                }
            }
            Thread.sleep(200);
            if (isGame && !isRunning)
                return;
            if (!isGame && !isOn)
                return;
        }

        if (isGame) {
            if ((int) (Math.random() * 11) <= 5)
                isLeft = true;
            else
                isRight = true;
        }
        else {
            if (vector.remove(0) == 2)
                left();
            else
                right();
            isWait = true;
        }
    }

    public void dead() {

        if (isUp) isUp = false;
        if (isDown) isDown = false;
        if (isLeft) isLeft = false;
        if (isRight) isRight = false;

        int temp_i = posY;
        int temp_j = posX;
        posX = resPosX;
        posY = resPosY;
        gameMap.chPos(temp_i, temp_j, posY, posX, 4);
        isWeak = false;
        onBase = true;
    }

    public void Weak() {
        if (!isWeak) isWeak = true;
        Timer timer1 = new Timer();
        timer1.schedule(new TimerTask() {
            @Override
            public void run() {
                unWeak();
            }
        }, 15000);
    }

    public void unWeak() {
        if (isWeak) isWeak = false;
    }

    private void up() {
        if (gameMap.getPos(posY - 1, posX) == 1 || gameMap.getPos(posY - 1, posX) == 5) {
            isUp = false;
            isDown = true;
        }
        else {
            if (isGame)
                vector.add(0);
            gameMap.chPos(posY, posX, --posY, posX, 4);
        }
    }

    private void down() {
        if (gameMap.getPos(posY + 1, posX) == 1 || (posY + 1 == 5 && (posX == 35 || posX == 36))) {
            isDown = false;
            isUp = true;
        }
        else {
            if (isGame)
                vector.add(1);
            gameMap.chPos(posY, posX, ++posY, posX, 4);
        }
    }

    private void left() {
        if (gameMap.getPos(posY, posX - 1) == 1 || gameMap.getPos(posY, posX - 1) == 5) {
            isLeft = false;
            isRight = true;
        }
        else {
            if (isGame)
                vector.add(2);
            gameMap.chPos(posY, posX, posY, --posX, 4);
        }
    }

    private void right() {
        if (gameMap.getPos(posY, posX + 1) == 1 || gameMap.getPos(posY, posX + 1) == 5) {
            isRight = false;
            isLeft = true;
        }
        else {
            if (isGame)
                vector.add(3);
            gameMap.chPos(posY, posX, posY, ++posX, 4);
        }
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

    public Thread getThread() {
        return thread;
    }
}