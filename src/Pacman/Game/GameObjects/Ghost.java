package Pacman.Game.GameObjects;

import Pacman.Game.GameModel;
import Pacman.Game.GameView;

import java.io.InterruptedIOException;

public class Ghost implements Runnable {

    private boolean isRunning = false;

    GameModel gameModel;
    GameView gameView;
    private int[][] gameMap;

    private int posX;
    private int posY;
    private int resp_posX;
    private int resp_posY;

    private Thread thread;

    public volatile boolean isUp = false;
    public volatile boolean isDown = false;
    public volatile boolean isLeft = false;
    public volatile boolean isRight = false;
    public volatile boolean isWeak = false;
    private volatile boolean onResp = true;

    public Ghost(int posX, int posY, GameModel gameModel) {
        this.posX = posX;
        this.posY = posY;
        resp_posX = posX;
        resp_posY = posY;
        this.gameModel = gameModel;
        this.gameMap = gameModel.getGameMap();
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
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
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (isRunning) {
            try {

                if (onResp) {
                    Thread.sleep(7000);
                    gameMap[posY][posX] = 6;
                    Thread.sleep(200);
                    script();
                }

                while (isUp) {
                    if (gameMap[posY][posX - 1] != 1 && gameMap[posY][posX - 1] != 5 && gameMap[posY][posX + 1] != 1 && gameMap[posY][posX + 1] != 5 && gameMap[posY - 1][posX] != 1 && gameMap[posY - 1][posX] != 5) {
                        choose(1, 4);
                        break;
                    }
                    if ((gameMap[posY][posX - 1] != 1 && gameMap[posY][posX - 1] != 5 && gameMap[posY][posX + 1] != 1 && gameMap[posY][posX + 1] != 5)
                            || (gameMap[posY][posX - 1] != 1 && gameMap[posY][posX - 1] != 5 && gameMap[posY - 1][posX] != 1 && gameMap[posY - 1][posX] != 5)
                            || (gameMap[posY][posX + 1] != 1 && gameMap[posY][posX + 1] != 5 && gameMap[posY - 1][posX] != 1 && gameMap[posY - 1][posX] != 5)) {
                        choose(1, 3);
                        break;
                    }
                    if ((gameMap[posY][posX - 1] != 1 && gameMap[posY][posX - 1] != 5)
                            || (gameMap[posY][posX + 1] != 1 && gameMap[posY][posX + 1] != 5)
                            || (gameMap[posY - 1][posX] != 1 && gameMap[posY - 1][posX] != 5)) {
                        choose(1, 2);
                        break;
                    }
                    up();
                    gameView.drawing(gameMap);
                    Thread.sleep(200);
                }

                while (isDown) {
                    if (gameMap[posY][posX - 1] != 1 && gameMap[posY][posX - 1] != 5 && gameMap[posY][posX + 1] != 1 && gameMap[posY][posX + 1] != 5 && gameMap[posY + 1][posX] != 1 && gameMap[posY + 1][posX] != 5) {
                        choose(1, 4);
                        break;
                    }
                    if ((gameMap[posY][posX - 1] != 1 && gameMap[posY][posX - 1] != 5 && gameMap[posY][posX + 1] != 1 && gameMap[posY][posX + 1] != 5)
                            || (gameMap[posY][posX - 1] != 1 && gameMap[posY][posX - 1] != 5 && gameMap[posY + 1][posX] != 1 && gameMap[posY + 1][posX] != 5)
                            || (gameMap[posY][posX + 1] != 1 && gameMap[posY][posX + 1] != 5 && gameMap[posY + 1][posX] != 1 && gameMap[posY + 1][posX] != 5)) {
                        choose(1, 3);
                        break;
                    }
                    if ((gameMap[posY][posX - 1] != 1 && gameMap[posY][posX - 1] != 5)
                            || (gameMap[posY][posX + 1] != 1 && gameMap[posY][posX + 1] != 5)
                            || (gameMap[posY + 1][posX] != 1 && gameMap[posY + 1][posX] != 5)) {
                        choose(1, 2);
                        break;
                    }
                    down();
                    gameView.drawing(gameMap);
                    Thread.sleep(200);
                }

                while (isLeft) {
                    if (gameMap[posY][posX - 1] != 1 && gameMap[posY][posX - 1] != 5 && gameMap[posY + 1][posX] != 1 && gameMap[posY + 1][posX] != 5 && gameMap[posY - 1][posX] != 1 && gameMap[posY - 1][posX] != 5) {
                        choose(1, 4);
                        break;
                    }
                    if ((gameMap[posY - 1][posX] != 1 && gameMap[posY - 1][posX] != 5 && gameMap[posY + 1][posX] != 1 && gameMap[posY + 1][posX] != 5)
                            || (gameMap[posY][posX - 1] != 1 && gameMap[posY][posX - 1] != 5 && gameMap[posY - 1][posX] != 1 && gameMap[posY - 1][posX] != 5)
                            || (gameMap[posY][posX - 1] != 1 && gameMap[posY][posX - 1] != 5 && gameMap[posY + 1][posX] != 1 && gameMap[posY + 1][posX] != 5)) {
                        choose(1, 3);
                        break;
                    }
                    if ((gameMap[posY][posX - 1] != 1 && gameMap[posY][posX - 1] != 5)
                            || (gameMap[posY + 1][posX] != 1 && gameMap[posY + 1][posX] != 5)
                            || (gameMap[posY - 1][posX] != 1 && gameMap[posY - 1][posX] != 5)) {
                        choose(1, 2);
                        break;
                    }
                    left();
                    gameView.drawing(gameMap);
                    Thread.sleep(200);
                }

                while (isRight) {
                    if (gameMap[posY][posX + 1] != 1 && gameMap[posY][posX + 1] != 5 && gameMap[posY + 1][posX] != 1 && gameMap[posY + 1][posX] != 5 && gameMap[posY - 1][posX] != 1 && gameMap[posY - 1][posX] != 5) {
                        choose(1, 4);
                        break;
                    }
                    if ((gameMap[posY - 1][posX] != 1 && gameMap[posY - 1][posX] != 5 && gameMap[posY + 1][posX] != 1 && gameMap[posY + 1][posX] != 5)
                            || (gameMap[posY][posX + 1] != 1 && gameMap[posY][posX + 1] != 5 && gameMap[posY - 1][posX] != 1 && gameMap[posY - 1][posX] != 5)
                            || (gameMap[posY][posX + 1] != 1 && gameMap[posY][posX + 1] != 5 && gameMap[posY + 1][posX] != 1 && gameMap[posY + 1][posX] != 5)) {
                        choose(1, 3);
                        break;
                    }
                    if ((gameMap[posY][posX + 1] != 1 && gameMap[posY][posX + 1] != 5)
                            || (gameMap[posY + 1][posX] != 1 && gameMap[posY + 1][posX] != 5)
                            || (gameMap[posY - 1][posX] != 1 && gameMap[posY - 1][posX] != 5)) {
                        choose(1, 2);
                        break;
                    }
                    right();
                    gameView.drawing(gameMap);
                    Thread.sleep(200);
                }
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void choose(int way, int count_way) {

        int res = (int) Math.random() * 101;

        switch (count_way) {
            case 2: //Если всего два пути, по которым можно пойти
                switch (way) {
                    case 0: //Если пришел сверху
                        isDown = false;
                        if ((gameMap[posY][posX - 1] != 1 && gameMap[posY][posX - 1] != 5) && res <= 80) {
                            isLeft = true;
                            break;
                        }
                        if ((gameMap[posY][posX + 1] != 1 && gameMap[posY][posX + 1] != 5) && res <= 80) {
                            isRight = true;
                            break;
                        }
                        isUp = true;
                        break;

                    case 1: //Если пришел снизу
                        isUp = false;
                        if ((gameMap[posY][posX - 1] != 1 && gameMap[posY][posX - 1] != 5) && res <= 80) {
                            isLeft = true;
                            break;
                        }
                        if ((gameMap[posY][posX + 1] != 1 && gameMap[posY][posX + 1] != 5) && res <= 80) {
                            isRight = true;
                            break;
                        }
                        isDown = true;
                        break;

                    case 2: //Если пришел слева
                        isRight = false;
                        if ((gameMap[posY - 1][posX] != 1 && gameMap[posY - 1][posX] != 5) && res <= 80) {
                            isUp = true;
                            break;
                        }
                        if ((gameMap[posY + 1][posX] != 1 && gameMap[posY + 1][posX] != 5) && res <= 80) {
                            isDown = true;
                            break;
                        }
                        isLeft = true;
                        break;

                    case 3: //Если пришел справа
                        isLeft = false;
                        if ((gameMap[posY - 1][posX] != 1 && gameMap[posY - 1][posX] != 5) && res <= 80) {
                            isUp = true;
                            break;
                        }
                        if ((gameMap[posY + 1][posX] != 1 && gameMap[posY + 1][posX] != 5) && res <= 80) {
                            isDown = true;
                            break;
                        }
                        isRight = true;
                        break;
                    default:
                        break;
                }
                break;

            case 3: //Если всего 3 путей, по которым можно пойти
                switch (way) {
                    case 0: //Если пришел сверху
                        if ((gameMap[posY + 1][posX] != 1 && gameMap[posY + 1][posX] != 5) && (gameMap[posY][posX - 1] != 1 && gameMap[posY][posX - 1] != 5)) {
                            if (res <= 45) {
                                isDown = false;
                                isLeft = true;
                                break;
                            }
                            if (res <= 90) {
                                break;
                            }
                            isDown = false;
                            isUp = true;
                            break;
                        }

                        if ((gameMap[posY + 1][posX] != 1 && gameMap[posY + 1][posX] != 5) && (gameMap[posY][posX + 1] != 1 && gameMap[posY][posX + 1] != 5)) {
                            if (res <= 45) {
                                break;
                            }
                            if (res <= 90) {
                                isDown = false;
                                isRight = true;
                                break;
                            }
                            isDown = false;
                            isUp = true;
                            break;
                        }

                        if (res <= 45) {
                            isDown = false;
                            isLeft = true;
                            break;
                        }
                        if (res <= 90) {
                            isDown = false;
                            isRight = true;
                            break;
                        }
                        isDown = false;
                        isUp = true;
                        break;

                    case 1: //Если пришел снизу
                        if ((gameMap[posY - 1][posX] != 1 && gameMap[posY - 1][posX] != 5) && (gameMap[posY][posX - 1] != 1 && gameMap[posY][posX - 1] != 5)) {
                            if (res <= 45) {
                                isUp = false;
                                isLeft = true;
                                break;
                            }
                            if (res <= 90) {
                                break;
                            }
                            isUp = false;
                            isDown = true;
                            break;
                        }

                        if ((gameMap[posY - 1][posX] != 1 && gameMap[posY - 1][posX] != 5) && (gameMap[posY][posX + 1] != 1 && gameMap[posY][posX + 1] != 5)) {
                            if (res <= 45) {
                                break;
                            }
                            if (res <= 90) {
                                isUp = false;
                                isRight = true;
                                break;
                            }
                            isUp = false;
                            isDown = true;
                            break;
                        }

                        if (res <= 45) {
                            isUp = false;
                            isLeft = true;
                            break;
                        }
                        if (res <= 90) {
                            isUp = false;
                            isRight = true;
                            break;
                        }
                        isUp = false;
                        isDown = true;
                        break;

                    case 2: //Если пришел слева
                        if ((gameMap[posY + 1][posX] != 1 && gameMap[posY + 1][posX] != 5) && (gameMap[posY][posX + 1] != 1 && gameMap[posY][posX + 1] != 5)) {
                            if (res <= 45) {
                                break;
                            }
                            if (res <= 90) {
                                isRight = false;
                                isDown = true;
                                break;
                            }
                            isRight = false;
                            isLeft = true;
                            break;
                        }

                        if ((gameMap[posY - 1][posX] != 1 && gameMap[posY - 1][posX] != 5) && (gameMap[posY][posX + 1] != 1 && gameMap[posY][posX + 1] != 5)) {
                            if (res <= 45) {
                                break;
                            }
                            if (res <= 90) {
                                isRight = false;
                                isUp = true;
                                break;
                            }
                            isRight = false;
                            isLeft = true;
                            break;
                        }

                        if (res <= 45) {
                            isRight = false;
                            isUp = true;
                            break;
                        }
                        if (res <= 90) {
                            isRight = false;
                            isDown = true;
                            break;
                        }
                        isRight = false;
                        isLeft = true;
                        break;

                    case 3: //Если пришел справа
                        if ((gameMap[posY + 1][posX] != 1 && gameMap[posY + 1][posX] != 5) && (gameMap[posY][posX - 1] != 1 && gameMap[posY][posX - 1] != 5)) {
                            if (res <= 45) {
                                break;
                            }
                            if (res <= 90) {
                                isLeft = false;
                                isDown = true;
                                break;
                            }
                            isLeft = false;
                            isRight = true;
                            break;
                        }

                        if ((gameMap[posY - 1][posX] != 1 && gameMap[posY - 1][posX] != 5) && (gameMap[posY][posX - 1] != 1 && gameMap[posY][posX - 1] != 5)) {
                            if (res <= 45) {
                                break;
                            }
                            if (res <= 90) {
                                isLeft = false;
                                isUp = true;
                                break;
                            }
                            isLeft = false;
                            isRight = true;
                            break;
                        }

                        if (res <= 45) {
                            isLeft = false;
                            isUp = true;
                            break;
                        }
                        if (res <= 90) {
                            isLeft = false;
                            isDown = true;
                            break;
                        }
                        isLeft = false;
                        isRight = true;
                        break;
                    default:
                        break;
                }
                break;
            case 4: //Если путей 4
                switch (way) {
                    case 0: //Если пришел сверху
                        if (res <= 30) {
                            isDown = false;
                            isLeft = true;
                            break;
                        }
                        if (res <= 60) {
                            break;
                        }
                        if (res <= 90) {
                            isDown = false;
                            isRight = true;
                            break;
                        }
                        isDown = false;
                        isUp = true;
                        break;

                    case 1: //Если пришел снизу
                        if (res <= 30) {
                            isUp = false;
                            isLeft = true;
                            break;
                        }
                        if (res <= 60) {
                            break;
                        }
                        if (res <= 90) {
                            isUp = false;
                            isRight = true;
                            break;
                        }
                        isUp = false;
                        isDown = true;
                        break;

                    case 2: //Если пришел слева
                        if (res <= 30) {
                            isRight = false;
                            isUp = true;
                            break;
                        }
                        if (res <= 60) {
                            break;
                        }
                        if (res <= 90) {
                            isRight = false;
                            isDown = true;
                            break;
                        }
                        isRight = false;
                        isLeft = true;
                        break;

                    case 3: //Если пришел справа
                        if (res <= 30) {
                            isLeft = false;
                            isUp = true;
                            break;
                        }
                        if (res <= 60) {
                            break;
                        }
                        if (res <= 90) {
                            isLeft = false;
                            isDown = true;
                            break;
                        }
                        isLeft = false;
                        isRight = true;
                        break;

                    default:
                        break;
                }
                break;

            default:
                break;
        }
    }

    private boolean up() {

        if (gameMap[posY - 1][posX] != 1 && gameMap[posY - 1][posX] != 5) {
            gameMap[posY][posY] = 0;
            gameMap[--posY][posX] = 6;
            return true;
        }
        return false;
    }

    private boolean down() {

        if (gameMap[posY + 1][posX] != 1 && gameMap[posY + 1][posX] != 5) {
            gameMap[posY][posY] = 0;
            gameMap[++posY][posX] = 6;
            return true;
        }
        return false;
    }

    private boolean left() {

        if (gameMap[posY][posX - 1] != 1 && gameMap[posY][posX - 1] != 5) {
            gameMap[posY][posY] = 0;
            gameMap[posY][--posX] = 6;
            return true;
        }
        return false;
}

    private boolean right() {

        if (gameMap[posY][posX + 1] != 1 && gameMap[posY][posX + 1] != 5) {
            gameMap[posY][posY] = 0;
            gameMap[posY][++posX] = 6;
            return true;
        }
        return false;
    }

    private void script() throws InterruptedException {
        if (gameMap[posY][posX - 2] == 1) {
            gameMap[posY][posY] = 4;
            gameMap[posY][++posX] = 6;
            gameView.drawing(gameMap);
            Thread.sleep(200);
            while (gameMap[posY + 1][posX] != 1) {
                gameMap[posY][posX] = 0;
                gameMap[--posY][posX] = 6;
                gameView.drawing(gameMap);
                Thread.sleep(200);
            }
            gameMap[posY + 1][posX] = 5;
            onResp = false;
            return;
        }

        if (gameMap[posY][posX + 2] == 1) {
            gameMap[posY][posY] = 4;
            gameMap[posY][--posX] = 6;
            gameView.drawing(gameMap);
            Thread.sleep(200);
            while (gameMap[posY + 1][posX] != 1) {
                gameMap[posY][posX] = 0;
                gameMap[--posY][posX] = 6;
                gameView.drawing(gameMap);
                Thread.sleep(200);
            }
            gameMap[posY + 1][posX] = 5;
            onResp = false;
            return;
        }

        if (gameMap[posY][posX - 1] == 1) {
            gameMap[posY][posY] = 4;
            gameMap[posY][++posX] = 6;
            gameView.drawing(gameMap);
            Thread.sleep(200);
            gameMap[posY][posY] = 4;
            gameMap[posY][++posX] = 6;
            gameView.drawing(gameMap);
            Thread.sleep(200);
            while (gameMap[posY + 1][posX] != 1) {
                gameMap[posY][posX] = 0;
                gameMap[--posY][posX] = 6;
                gameView.drawing(gameMap);
                Thread.sleep(200);
            }
            gameMap[posY + 1][posX] = 5;
            onResp = false;
            return;
        }

        if (gameMap[posY][posX + 1] == 1) {
            gameMap[posY][posY] = 4;
            gameMap[posY][--posX] = 6;
            gameView.drawing(gameMap);
            Thread.sleep(200);
            gameMap[posY][posY] = 4;
            gameMap[posY][--posX] = 6;
            gameView.drawing(gameMap);
            Thread.sleep(200);
            while (gameMap[posY + 1][posX] != 1) {
                gameMap[posY][posX] = 0;
                gameMap[--posY][posX] = 6;
                gameView.drawing(gameMap);
                Thread.sleep(200);
            }
            gameMap[posY + 1][posX] = 5;
            onResp = false;
            return;
        }
    }

    public void kill() throws InterruptedException {
        wayOff();
        gameMap[posY][posX] = 2;
        posX = resp_posX;
        posY = resp_posY;
        if (isWeak) isWeak = false;
        onResp = true;
    }

    private void wayOff() {
        if (isUp) isUp =  false;
        if (isDown) isDown = false;
        if (isLeft) isLeft = false;
        if (isRight) isRight = false;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
}
