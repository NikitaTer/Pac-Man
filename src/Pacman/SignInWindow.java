package Pacman;

import Pacman.Controller.MainMenuController;
import Pacman.Controller.SignInWindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SignInWindow {

    private MainMenuController ForNick;

    public SignInWindow(MainMenuController lab) throws Exception {
        Stage prStage = new Stage();
        prStage.setTitle("Вход");

        ForNick = lab;

        AnchorPane rootP = new AnchorPane();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Controller/View/SignInWindow.fxml"));
            rootP = (AnchorPane) loader.load();
            SignInWindowController controller = loader.getController();
            controller.setStage(prStage);
            controller.setForNick(ForNick);
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }

        Scene scene = new Scene(rootP);
        prStage.setScene(scene);

        prStage.show();
    }
}
