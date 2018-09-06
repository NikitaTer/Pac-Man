package Pacman;

import Pacman.Files.RecordsFile;
import Pacman.Game.*;
import Pacman.Replay.MyVector;
import Pacman.Replay.PacManData;
import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.*;
import java.util.Vector;

public class MainApp extends Application {

    private static Stage prStage;
    private static Scene menuScene;
    public static final int HEIGHT = 539;
    public static final int WIDTH = 716;
    private static ControllersManager CManager;

    private Vector<PacManData> pacManData;
    private Vector<Integer> ghostsData[];

    private boolean isNickname = false;
    private String nickname;
    private RecordsFile recordsFile;

    @Override
    public void start(Stage prStage) {
        this.prStage = prStage;
        prStage.setTitle("Pac-Man");
        prStage.setResizable(false);
        prStage.getIcons().add(new Image(MainApp.class.getResourceAsStream("View/Icon_mini.png")));

        AnchorPane rootMenu = new AnchorPane();
        CManager = new ControllersManager(this);

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("View/MainMenu.fxml"));
            rootMenu = (AnchorPane) loader.load();
            CManager.addMainMenuController(loader.getController(), prStage);
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }

        downloadVectors();

        menuScene = new Scene(rootMenu);
        menuScene.getStylesheets().add(getClass().getResource("View/My.css").toExternalForm());
        prStage.setScene(menuScene);

        recordsFile = new RecordsFile();
        updateRecords();

        prStage.show();
    }

    public void setNickname(boolean nickname, String nick) {
        isNickname = nickname;
        if (isNickname)
            this.nickname = nick;
    }

    private void updateRecords() {
        CManager.getMMController().getData().clear();
        CManager.getMMController().getData().addAll(recordsFile.getData());
    }

    public void startMenu(long score) {
        if (isNickname && score >= 0) {
            recordsFile.write(new Data(nickname, score));
            updateRecords();
        }

        if (score >= 0)
            saveVectors();

        prStage.setHeight(HEIGHT);
        prStage.setWidth(WIDTH);
        prStage.setScene(menuScene);
    }

    public void startGame() {
        Pane rootGame = new Pane();
        Scene gameScene = new Scene(rootGame);

        GameView gameView = new GameView();
        GameModel gameModel = new GameModel(this, true);
        gameModel.setGameView(gameView);

        prStage.setHeight(GameView.HIGH);
        prStage.setWidth(GameView.WIDTH);
        prStage.setScene(gameScene);

        rootGame.getChildren().add(gameView);

        gameScene.addEventHandler(KeyEvent.KEY_PRESSED, gameModel);

        gameModel.start();
        gameView.setGameModel(gameModel);

        gameScene.addEventHandler(KeyEvent.KEY_PRESSED, gameModel.getPacMan());
    }

    public void startReplay() {
        if (pacManData.size() != 0) {
            Pane rootGame = new Pane();
            Scene gameScene = new Scene(rootGame);

            GameView gameView = new GameView();
            GameModel gameModel = new GameModel(this, false);
            gameModel.setGameView(gameView);

            prStage.setHeight(GameView.HIGH);
            prStage.setWidth(GameView.WIDTH);
            prStage.setScene(gameScene);

            rootGame.getChildren().add(gameView);

            gameScene.addEventHandler(KeyEvent.KEY_PRESSED, gameModel);


            gameModel.start();
            gameView.setGameModel(gameModel);
        }
    }

    public void setPacManData(Vector<PacManData> pacManData) {
        if (this.pacManData.size() != 0)
            this.pacManData.clear();
        this.pacManData.addAll(pacManData);
    }

    public Vector<PacManData> getPacManData() {
        return pacManData;
    }

    public void setGhostsData(int i, Vector<Integer> vector) {
        if (ghostsData[i].size() != 0)
            ghostsData[i].clear();
        ghostsData[i].addAll(vector);
    }

    public Vector<Integer>[] getGhostsData() {
        return ghostsData;
    }

    private void saveVectors() {
        try {
            FileOutputStream fos = new FileOutputStream("PacManData.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            MyVector<PacManData> temp = new MyVector<>();
            temp.copy(pacManData);
            oos.writeObject(temp);
            oos.close();
        } catch (FileNotFoundException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            for (int i = 0; i < 8; i++) {
                FileOutputStream fos = new FileOutputStream("GhostData" + i + ".ser");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                MyVector<Integer> temp = new MyVector<>();
                temp.copy(ghostsData[i]);
                oos.writeObject(temp);
                oos.close();
            }
        } catch (FileNotFoundException ex) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void downloadVectors() {
        pacManData = new Vector<>();
        ghostsData = new Vector[8];
        for (int i = 0; i < 8; i++)
            ghostsData[i] = new Vector<>();
        try {
            FileInputStream fis = new FileInputStream("PacManData.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            MyVector<PacManData> temp = (MyVector<PacManData>) ois.readObject();
            pacManData.addAll(temp);
            ois.close();
        } catch (FileNotFoundException e) {

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            for (int i = 0; i < 8; i++) {
                FileInputStream fis = new FileInputStream("GhostData" + i + ".ser");
                ObjectInputStream ois = new ObjectInputStream(fis);
                MyVector<Integer> temp = (MyVector<Integer>) ois.readObject();
                ghostsData[i].addAll(temp);
                ois.close();
            }
        } catch (FileNotFoundException e) {

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] argc) {
        launch(argc);
    }
}
