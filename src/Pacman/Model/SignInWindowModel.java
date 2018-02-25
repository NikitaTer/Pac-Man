package Pacman.Model;


import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

public class SignInWindowModel extends Application {

    private static Stage prStage;

    @Override
    public void start(Stage prStage) {
        this.prStage = prStage;
        prStage.setTitle("Вход");

        AnchorPane rootPane = new AnchorPane();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/SignInWindow.fxml"));
            rootPane = (AnchorPane) loader.load();
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }

        Scene scene = new Scene(rootPane);
        prStage.setScene(scene);

        prStage.show();
    }

    public static void close() {
        prStage.close();
    }
}
