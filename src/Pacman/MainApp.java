package Pacman;

import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    private static Stage prStage;

    @Override
    public void start(Stage prStage) {
        this.prStage = prStage;
        prStage.setTitle("Pacman");
        prStage.setResizable(false);

        AnchorPane rootPane = new AnchorPane();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("Controller/View/MainMenu.fxml"));
            rootPane = (AnchorPane) loader.load();
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }

        Scene scene = new Scene(rootPane);
        scene.getStylesheets().add(getClass().getResource("Controller/View/My.css").toExternalForm());
        prStage.setScene(scene);

        prStage.show();
    }

    public static void close() {
        prStage.close();
    }

    public static void main(String[] argc) {
        launch(argc);
    }
}
