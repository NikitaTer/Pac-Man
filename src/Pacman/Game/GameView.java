package Pacman.Game;

import Pacman.Game.GameObjects.Space;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class GameView extends Canvas implements Runnable {

    private boolean  isRunning = false;
    public static final int WIDTH = 900;
    public static final int HIGH = 300;
    private GraphicsContext gc;
    private Thread thread;
    private GameModel gameModel;
    private int[][] gameMap;

    private Image[] pacmans;
    private Image[][] ghosts;
    private Image[] ghosts_weak;
    private Image[] spaces;
    private Image[] walls;

    public GameView() {
        setWidth(GameView.WIDTH);
        setHeight(GameView.HIGH);

        image_init();

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

    @Override
    public void run() {
        drawing(gameModel.getGameMap());
        /*long LastTime = System.nanoTime();
        long Now;
        double TargetTicks = 60;
        double Delta = 0;
        double ns = 1000000000/TargetTicks;

        while(isRunning) {
            Now = System.nanoTime();
            Delta+=(Now - LastTime) / ns;
            LastTime = Now;
            while(Delta >=1) {
                drawing(gameModel.getGameMap());
                Delta--;
            }
        }*/
    }

    /***
     * Метод отрисовки на экране происходящего в игре.
     * @param gameMap -- Ссылка на двумерный массив, который представляет из себя карту игры.
     *
     * @author NikiTer
     */
    public synchronized void drawing(int[][] gameMap) {
        int i, j, x = 0, y = 0;


        for (i = 0; i < 17; i++) {
            for (j = 0; j < 58; j++) {

                /*
                 * 0 - пространство, по которому можно ходить
                 * 1 - стена
                 * 2 - игрок (Пакмен)
                 * 4 - респавн призраков
                 * 5 - выход из комнаты призраков
                 * 6 - прзрак*/

                switch (gameMap[i][j]) {
                    case 0: {
                        Space sp = gameModel.getSpaces()[i][j];
                        if (sp.isPoint)
                            gc.drawImage(spaces[1], x, y,14,14);
                        else if (sp.isUltimate)
                            gc.drawImage(spaces[3], x,y,14,14);
                        else if (sp.isCherry)
                            System.out.println("Not Yet");
                        else
                            gc.drawImage(spaces[0], x, y,14,14);
                        break;
                    }

                    case 1: {
                        gc.drawImage(wallSwitch(i, j), x,y,14,14);
                        break;
                    }

                    case 2: {
                        if (gameModel.getPac().isUp)
                            gc.drawImage(pacmans[2], x,y,14,14);
                        else if (gameModel.getPac().isDown)
                            gc.drawImage(pacmans[3], x,y,14,14);
                        else if (gameModel.getPac().isLeft)
                            gc.drawImage(pacmans[1], x,y,14,14);
                        else
                            gc.drawImage(pacmans[0], x,y,14,14);
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

                    case 6: {
                        for (i = 0; gameModel.getGhosts()[i].getPosX() != j && gameModel.getGhosts()[i].getPosY() != i; i++);
                        if (gameModel.getGhosts()[i].isWeak) {
                            gc.drawImage(ghosts_weak[0],x,y,14,14);
                        }
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

    private Image wallSwitch(int i, int j) {
        if (i == 0 && j == 0)
            return walls[8];
        if (i == 0 && j == 57)
            return walls[9];
        if (i == 16 && j == 0)
            return walls[11];
        if (i == 16 && j == 57)
            return walls[10];

        if (i == 0)
            return walls[0];
        if (j == 0)
            return walls[3];
        if (i == 16)
            return walls[2];
        if (j == 57)
            return walls[1];

        if ((gameMap[i + 1][j] == 0 || gameMap[i + 1][j] == 2) && gameMap[i][j - 1] == 1 && gameMap[i][j + 1] == 1)
            return walls[0];
        if ((gameMap[i][j - 1] == 0 || gameMap[i][j - 1] == 2) && gameMap[i + 1][j] == 1 && gameMap[i - 1][j] == 1)
            return walls[1];
        if ((gameMap[i - 1][j] == 0 || gameMap[i - 1][j] == 2) && gameMap[i][j - 1] == 1 && gameMap[i][j + 1] == 1)
            return walls[2];
        if ((gameMap[i][j + 1] == 0 || gameMap[i][j + 1] == 2) && gameMap[i + 1][j] == 1 && gameMap[i - 1][j] == 1)
            return walls[3];

        if ((gameMap[i - 1][j] == 0 || gameMap[i - 1][j] == 2) && (gameMap[i][j - 1] == 0 || gameMap[i][j - 1] == 2))
            return walls[4];
        if ((gameMap[i - 1][j] == 0 || gameMap[i - 1][j] == 2) && (gameMap[i][j + 1] == 0 || gameMap[i][j + 1] == 2))
            return walls[5];
        if ((gameMap[i + 1][j] == 0 || gameMap[i + 1][j] == 2) && (gameMap[i][j + 1] == 0 || gameMap[i][j + 1] == 2))
            return walls[6];
        if ((gameMap[i + 1][j] == 0 || gameMap[i + 1][j] == 2) && (gameMap[i][j - 1] == 0 || gameMap[i][j - 1] == 2))
            return walls[7];

        if (gameMap[i + 1][j + 1] == 0 || gameMap[i + 1][j + 1] == 2)
            return walls[8];
        if (gameMap[i + 1][j - 1] == 0 || gameMap[i + 1][j - 1] == 2)
            return walls[9];
        if (gameMap[i - 1][j - 1] == 0 || gameMap[i - 1][j - 1] == 2)
            return walls[10];
        if (gameMap[i - 1][j + 1] == 0 || gameMap[i - 1][j + 1] == 2)
            return walls[11];
        return walls[12];
    }

    /**
     * Метод, в результате которого создаются массивы картинок,
     * которые будут использоваться для отрисовки происходящего в игре на экране.
     *
     *@author NikiTer */
    private void image_init() {
        pacmans = new Image[4];
        ghosts = new Image[4][8];
        ghosts_weak = new Image[4];
        spaces = new Image[4];
        walls = new Image[13];
        int x = 0, y = 0, i = 0, j = 0;

        Image imageWalls = new Image(GameView.class.getResourceAsStream("Textures/Walls.bmp"));
        Image imageSprites = new Image(GameView.class.getResourceAsStream("Textures/Sprites.bmp"));

        for (x = 18, i = 0, y = 1; y <= 46; y += 15, i++)
            pacmans[i] = new WritableImage(imageSprites.getPixelReader(), x,y, 14,14);

        for (x = 3, y = 65, i = 0; i < 4; i++, x = 3, y += 16)
            for (j = 0; j < 8; j++, x +=16)
                ghosts[i][j] = new WritableImage(imageSprites.getPixelReader(), x, y,14,14);

        for (i = 0, x = 131, y = 65; i < 4; i++, x += 16)
            ghosts_weak[i] = new WritableImage(imageSprites.getPixelReader(), x, y, 14,14);

        /*
         * 0 - Пустое пространство
         * 1 - Пространство с поинтом
         * 2 - Пространство с вишенкой
         * 3 - Пространство с ультой*/
        spaces[0] = new WritableImage(imageWalls.getPixelReader(), 0,64,14,14);
        spaces[1] = new WritableImage(imageWalls.getPixelReader(), 16, 64,14,14);
        spaces[2] = new WritableImage(imageWalls.getPixelReader(), 32, 64,14,14);
        spaces[3] = new WritableImage(imageWalls.getPixelReader(), 48, 64,14,14);

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
        x = y = i = 0;
        for( ; x <= 48; x+=16, i++)
            walls[i] = new WritableImage(imageWalls.getPixelReader(), x,y,14,14);
        for(x = 0, y = 16; x <= 48; x+=16, i++)
            walls[i] = new WritableImage(imageWalls.getPixelReader(), x,y,14,14);
        for(x = 0, y = 32; x <= 48; x+=16, i++)
            walls[i] = new WritableImage(imageWalls.getPixelReader(), x,y,14,14);
        x = 0;
        y = 48;
        walls[i] = new WritableImage(imageWalls.getPixelReader(), x,y,14,14);
    }

    /**
     * Метод использкется для приема ссылки на объект класса GameModel.
     * @param gameModel -- Наш объект, отвечающий за логику игры
     * @see GameModel
     *
     * @author NikiTer*/
    public void setGameModel(GameModel gameModel) {
        this.gameModel = gameModel;
        gameMap = gameModel.getGameMap();
    }
}
