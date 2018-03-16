package Pacman;

import Pacman.Controller.MainMenuController;
import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    private static Stage prStage;
    private static Scene scene;
    private static ControllersManager CManager;

    @Override
    public void start(Stage prStage) {
        this.prStage = prStage;
        prStage.setTitle("Pac-Man");
        prStage.setResizable(false);

        AnchorPane rootPane = new AnchorPane();
        CManager = new ControllersManager();

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("View/MainMenu.fxml"));
            rootPane = (AnchorPane) loader.load();
            CManager.addMainMenuController(loader.getController(), prStage);
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }

        scene = new Scene(rootPane);
        scene.getStylesheets().add(getClass().getResource("View/My.css").toExternalForm());
        prStage.setScene(scene);

        prStage.show();
    }

    public static void StartMenu(Stage st) {
        st.setScene(scene);
    }

    public static void main(String[] argc) {
        launch(argc);
    }
}
