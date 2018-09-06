package Pacman.Game;

import Pacman.Game.GameObjects.*;
import Pacman.MainApp;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.Timer;
import java.util.TimerTask;

public class GameModel implements EventHandler<KeyEvent>{
    private GameMap gameMap;
    private Ghost[] ghosts;
    private PacMan pacMan;
    private Space[][] spaces;
    private Wall[][] walls;

    private GameView gameView;
    private MainApp mainApp;
    private boolean isGame;


    private volatile long score = 0;
    private long points = 0;

    public GameModel(MainApp mainApp, boolean isGame) {
        this.mainApp = mainApp;
        this.isGame = isGame;
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public Space getSpace(int i, int j) {
        return spaces[i][j];
    }

    public Wall getWall(int i, int j) {
        return walls[i][j];
    }

    public Ghost getGhost(int i, int j) {
        int k;
        for (k = 0; ghosts[k].getPosX() != j || ghosts[k].getPosY() != i; k++);
        return ghosts[k];
    }

    public PacMan getPacMan() {
        return pacMan;
    }

    public long getScore() {
        return score;
    }

    public void start() {
        gameMap = new GameMap(this, gameView);
        spaces = new Space[17][58];
        ghosts = new Ghost[8];
        walls = new Wall[17][58];
        int k = 0;

        synchronized (gameMap) {
            for (int i = 0; i < 17; i++)
                for (int j = 0; j < 58; j++) {
                    switch(gameMap.getPos(i, j)) {
                        case 0: {
                            if (i == 8 && j == 12) {
                                spaces[i][j] = new Space(false, false, true);
                                points++;
                            }
                            else if (gameMap.getPos(i, j + 1) == 4 || gameMap.getPos(i, j - 1) == 4)
                                spaces[i][j] = new Space(false,false,false);
                            else {
                                spaces[i][j] = new Space();
                                points++;
                            }
                            break;
                        }

                        case 1: {
                            walls[i][j] = new Wall(wallSwitch(i, j));
                            break;
                        }

                        case 2: {
                            pacMan = new PacMan(j, i, this, gameView, isGame);
                            spaces[i][j] = new Space(false, false, false);
                            break;
                        }

                        case 4: {
                            ghosts[k] = new Ghost(j, i, this, gameView, (k * 1000) + 1000, isGame);
                            k++;
                            spaces[i][j] = new Space(false, false, false);
                            break;
                        }
                    }
        }
        if (!isGame) {
                pacMan.setVector(mainApp.getPacManData());
                for (int i = 0; i < 8; i++)
                    ghosts[i].setVector(mainApp.getGhostsData()[i]);
        }
            for (int i = 0; i < 8; i++)
                ghosts[i].start(i);
            pacMan.start();
        }
    }

    /*
     * 00 - стена горизонтальная верхняя
     * 01 - стена вертикальная правая
     * 02 - стена горизонтальная нижняя
     * 03 - стена вертикальная леваяя
     * 04 - правый-нижний угол стены (открытый)
     * 05 - левый-нижний угол стены (открытый)
     * 06 - левый-верхний угол стены (открытый)
     * 07 - правый-верхний угол стены (открытый)
     * 08 - левый-верхний угол стены (закрытый)
     * 09 - правый-верхний угол стены (закрытый)
     * 10 - правый-нижний угол стены (закрытый)
     * 11 - левый-нижний угол стены (закрытый)
     * 12 - внутреннее заполненное пространство стен*/
    private int wallSwitch(int i, int j) {
        if (i == 0 && j == 0)
            return 8;
        if (i == 0 && j == 57)
            return 9;
        if (i == 16 && j == 0)
            return 11;
        if (i == 16 && j == 57)
            return 10;

        if (i == 0)
            return 0;
        if (j == 0)
            return 3;
        if (i == 16)
            return 2;
        if (j == 57)
            return 1;

        if (gameMap.getPos(i + 1, j) != 1 && gameMap.getPos(i, j - 1) == 1 && gameMap.getPos(i, j + 1) == 1)
            return 0;
        if (gameMap.getPos(i, j - 1) != 1 && gameMap.getPos(i + 1, j) == 1 && gameMap.getPos(i - 1, j) == 1)
            return 1;
        if (gameMap.getPos(i - 1, j) != 1 && gameMap.getPos(i, j - 1) == 1 && gameMap.getPos(i, j + 1) == 1)
            return 2;
        if (gameMap.getPos(i, j + 1) != 1 && gameMap.getPos(i + 1, j) == 1 && gameMap.getPos(i - 1, j) == 1)
            return 3;

        if (gameMap.getPos(i - 1, j) != 1 && gameMap.getPos(i, j - 1) != 1)
            return 4;
        if (gameMap.getPos(i - 1, j) != 1 && gameMap.getPos(i, j + 1) != 1)
            return 5;
        if (gameMap.getPos(i + 1, j) != 1 && gameMap.getPos(i, j + 1) != 1)
            return 6;
        if (gameMap.getPos(i + 1, j) != 1 && gameMap.getPos(i, j - 1) != 1)
            return 7;

        if (gameMap.getPos(i + 1, j + 1) != 1)
            return 8;
        if (gameMap.getPos(i + 1, j - 1) != 1)
            return 9;
        if (gameMap.getPos(i - 1, j - 1) != 1)
            return 10;
        if (gameMap.getPos(i - 1, j + 1) != 1)
            return 11;
        return 12;
    }

    public void check(int i, int j) {
        if (spaces[i][j].isEmpty())
            return;
        if (spaces[i][j].isPoint())
            score += 100;
        else if (spaces[i][j].isUltimate()) {
            score += 200;
            for (int k = 0; k < 8; k++)
                ghosts[k].Weak();
        }
        spaces[i][j].eaten();
        points--;
        if (points <= 0) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    win();
                }
            });
        }
    }

    public int chose(int i, int j) {
        for (int k = 0; k < 8; k++)
            if (ghosts[k].getPosY() == i && ghosts[k].getPosX() == j)
                return 4;
        if (pacMan.getPosY() == i && pacMan.getPosX() == j)
            return 2;
        if (i == 5 && (j == 35 || j == 36))
            return 5;
        return 0;
    }

    public void battle() {
        if (getGhost(pacMan.getPosY(), pacMan.getPosX()).isWeak())
            pacManWin();
        else {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    pacManLose();
                }
            });
        }
    }

    private void pacManLose() {
        gameView.lose();
        stop();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (isGame)
                            mainApp.startMenu(score);
                        else
                            mainApp.startMenu(-1);
                        timer.cancel();
                        timer.purge();
                    }
                });
            }
        }, 1000);
    }

    private void pacManWin() {
        getGhost(pacMan.getPosY(), pacMan.getPosX()).dead();
        score += 500;
    }

    private void win() {
        gameView.win();
        stop();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (isGame)
                            mainApp.startMenu(score);
                        else
                            mainApp.startMenu(-1);
                        timer.cancel();
                        timer.purge();
                    }
                });
            }
        }, 1000);
    }

    public void stop() {
        pacMan.stop();
        for (int i = 0; i < 8; i++)
            ghosts[i].stop();
        if (isGame) {
            mainApp.setPacManData(pacMan.getVector());
            for (int i = 0; i < 8; i++)
                mainApp.setGhostsData(i, ghosts[i].getVector());
        }
    }

    @Override
    public void handle(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            stop();
            if (isGame)
                mainApp.startMenu(score);
            else
                mainApp.startMenu(-1);
        }
    }
}