package Pacman.Game;
import Pacman.Game.GameObjects.*;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class GameView extends Canvas {
    public static final int WIDTH = 818;
    public static final int HIGH = 539;

    GraphicsContext graphicsContext;
    GameModel gameModel;
    GameMap gameMap;

    private Image[] pacmans;
    private Image lastPacMan;
    private Image[][] ghosts;
    private Image[] ghosts_weak;
    private Image[] spaces;
    private Image[] walls;

    public GameView() {
        setWidth(WIDTH);
        setHeight(HIGH);
        graphicsContext = this.getGraphicsContext2D();
        imageInit();
    }

    public void setGameModel(GameModel gameModel) {
        this.gameModel = gameModel;
        gameMap = gameModel.getGameMap();
    }

    public void imageInit() {
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

        for (x = 3, y = 65, i = 0; i < 4; i++, x = 3,  y += 16)
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

        lastPacMan = pacmans[0];
    }

    public void firstDraw() {
        int i, j, x = 0, y = 151;

        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0, WIDTH, HIGH);

        graphicsContext.setFont(new Font("Consolas", 20));
        graphicsContext.setTextAlign(TextAlignment.CENTER);
        graphicsContext.setTextBaseline(VPos.TOP);
        graphicsContext.setFill(Color.YELLOW);
        graphicsContext.fillText("" + gameModel.getScore(),409, 70);

        while (gameModel == null);

        for (i = 0; i < 17; i++) {
            for (j = 0; j < 58; j++) {
                /*
                 * 0 - пространство, по которому можно ходить
                 * 1 - стена
                 * 2 - игрок (Пакмен)
                 * 4 - призрак
                 * 5 - выход из комнаты призраков*/
                switch (gameMap.getPos(i, j)) {
                    case 0: {
                        Space space = gameModel.getSpace(i, j);
                         if (space.isPoint())
                            graphicsContext.drawImage(spaces[1], x, y,14,14);
                        else if (space.isUltimate())
                            graphicsContext.drawImage(spaces[3], x,y,14,14);
                        else if (space.isCherry())
                            System.out.println("Not Yet");
                        else
                            graphicsContext.drawImage(spaces[0], x, y,14,14);
                        break;
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
                    case 1: {
                        switch (gameModel.getWall(i, j).getType()) {
                            case 0:
                                graphicsContext.drawImage(walls[0], x, y, 14, 14);
                                break;
                            case 1:
                                graphicsContext.drawImage(walls[1], x, y, 14, 14);
                                break;
                            case 2:
                                graphicsContext.drawImage(walls[2], x, y, 14, 14);
                                break;
                            case 3:
                                graphicsContext.drawImage(walls[3], x, y, 14, 14);
                                break;
                            case 4:
                                graphicsContext.drawImage(walls[4], x, y, 14, 14);
                                break;
                            case 5:
                                graphicsContext.drawImage(walls[5], x, y, 14, 14);
                                break;
                            case 6:
                                graphicsContext.drawImage(walls[6], x, y, 14, 14);
                                break;
                            case 7:
                                graphicsContext.drawImage(walls[7], x, y, 14, 14);
                                break;
                            case 8:
                                graphicsContext.drawImage(walls[8], x, y, 14, 14);
                                break;
                            case 9:
                                graphicsContext.drawImage(walls[9], x, y, 14, 14);
                                break;
                            case 10:
                                graphicsContext.drawImage(walls[10], x, y, 14, 14);
                                break;
                            case 11:
                                graphicsContext.drawImage(walls[11], x, y, 14, 14);
                                break;
                            case 12:
                                graphicsContext.drawImage(walls[12], x, y, 14, 14);
                                break;
                        }
                        break;
                    }
                    /*
                     * 00 - пакмен повернут вправо
                     * 01 - пакмен повернут влево
                     * 02 - пакмен повернут вверх
                     * 03 - пакмен повернут вниз*/
                    case 2: {
                        if (gameModel.getPacMan().isUp()) {
                            graphicsContext.drawImage(pacmans[2], x, y, 14, 14);
                            lastPacMan = pacmans[2];
                        }
                        else if (gameModel.getPacMan().isDown()) {
                            graphicsContext.drawImage(pacmans[3], x, y, 14, 14);
                            lastPacMan = pacmans[3];
                        }
                        else if (gameModel.getPacMan().isLeft()) {
                            graphicsContext.drawImage(pacmans[1], x, y, 14, 14);
                            lastPacMan = pacmans[1];
                        }
                        else if (gameModel.getPacMan().isRight()) {
                            graphicsContext.drawImage(pacmans[0], x, y, 14, 14);
                            lastPacMan = pacmans[0];
                        }
                        else
                            graphicsContext.drawImage(lastPacMan, x, y, 14, 14);
                        break;
                    }

                    case 4: {
                        if (gameModel.getGhost(i, j).isWeak())
                            graphicsContext.drawImage(ghosts_weak[0], x, y, 14,14);
                        else if (gameModel.getGhost(i, j).isUp())
                            graphicsContext.drawImage(ghosts[0][0], x, y, 14, 14);
                        else if (gameModel.getGhost(i, j).isDown())
                            graphicsContext.drawImage(ghosts[0][2], x, y, 14, 14);
                        else if (gameModel.getGhost(i, j).isLeft())
                            graphicsContext.drawImage(ghosts[0][4], x, y, 14, 14);
                        else
                            graphicsContext.drawImage(ghosts[0][6], x, y, 14, 14);
                        break;
                    }

                    case 5: {
                        graphicsContext.drawImage(spaces[0], x, y,14,14);
                        break;
                    }
                    default:
                        graphicsContext.setFill(Color.WHITE);
                        graphicsContext.fillRect(x, y, 14, 14);
                        break;
                }
                x += 14;
            }
            x = 0;
            y += 14;
        }
    }

    public void drawing(int i, int j, int val) {

        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 70, WIDTH, 20);
        graphicsContext.setFill(Color.YELLOW);
        graphicsContext.fillText("" + gameModel.getScore(),409, 70);

        switch (val) {
            case 0:
                if (gameModel.getSpace(i, j).isPoint())
                    graphicsContext.drawImage(spaces[1], j*14, (i*14 + 151), 14, 14);
                else if (gameModel.getSpace(i, j).isUltimate())
                    graphicsContext.drawImage(spaces[3], j*14, (i*14 + 151), 14, 14);
                else
                    graphicsContext.drawImage(spaces[0], j*14, (i*14 + 151), 14, 14);
                break;

            case 1:
                break;

            case 2:
                if (gameModel.getPacMan().isUp()) {
                    graphicsContext.drawImage(pacmans[2], j*14, (i*14 + 151), 14, 14);
                    lastPacMan = pacmans[2];
                }
                else if (gameModel.getPacMan().isDown()) {
                    graphicsContext.drawImage(pacmans[3], j*14, (i*14 + 151), 14, 14);
                    lastPacMan = pacmans[3];
                }
                else if (gameModel.getPacMan().isLeft()) {
                    graphicsContext.drawImage(pacmans[1], j*14, (i*14 + 151), 14, 14);
                    lastPacMan = pacmans[1];
                }
                else if (gameModel.getPacMan().isRight()) {
                    graphicsContext.drawImage(pacmans[0], j*14, (i*14 + 151), 14, 14);
                    lastPacMan = pacmans[0];
                }
                else
                    graphicsContext.drawImage(lastPacMan, j*14, (i*14 + 151), 14, 14);
                break;

            case 4:
                if (gameModel.getGhost(i, j).isWeak())
                    graphicsContext.drawImage(ghosts_weak[0], j*14, (i*14 + 151), 14,14);
                else if (gameModel.getGhost(i, j).isUp())
                    graphicsContext.drawImage(ghosts[0][0], j*14, (i*14 + 151), 14, 14);
                else if (gameModel.getGhost(i, j).isDown())
                    graphicsContext.drawImage(ghosts[0][2], j*14, (i*14 + 151), 14, 14);
                else if (gameModel.getGhost(i, j).isLeft())
                    graphicsContext.drawImage(ghosts[0][4], j*14, (i*14 + 151), 14, 14);
                else
                    graphicsContext.drawImage(ghosts[0][6], j*14, (i*14 + 151), 14, 14);
                break;

            case 5:
                graphicsContext.drawImage(spaces[0], j*14, (i*14 + 151), 14, 14);

                default:
                    break;
        }
    }

    public void win() {
        graphicsContext.setFill(Color.YELLOW);
        graphicsContext.setFont(new Font(40));
        graphicsContext.setTextAlign(TextAlignment.CENTER);
        graphicsContext.setTextBaseline(VPos.CENTER);
        graphicsContext.fillText("Winner Winner Chicken Dinner", Math.round(getWidth() / 2), Math.round(getHeight() / 2));
    }

    public void lose() {
        graphicsContext.setFill(Color.YELLOW);
        graphicsContext.setFont(new Font(40));
        graphicsContext.setTextAlign(TextAlignment.CENTER);
        graphicsContext.setTextBaseline(VPos.CENTER);
        graphicsContext.fillText("Loooooser", Math.round(getWidth() / 2), Math.round(getHeight() / 2));
    }
}
